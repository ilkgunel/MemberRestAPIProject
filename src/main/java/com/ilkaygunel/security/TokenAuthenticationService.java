package com.ilkaygunel.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TokenAuthenticationService {

    static final long EXPIRATIONTIME = 864_000_000; // 10 days
    static final String SECRET = "ThisIsASecret";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";

    public static void addAuthentication(HttpServletResponse res, Authentication auth) {

        String concattedRoles = "";
        for (GrantedAuthority ga : auth.getAuthorities()) {
            if (!"".equals(concattedRoles)) {
                concattedRoles += "," + ga.getAuthority();
            } else {
                concattedRoles += ga.getAuthority();
            }

        }

        String JWT = Jwts.builder().setSubject(auth.getName()).claim("roles", concattedRoles)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
        System.out.println("OUR JWT Token:" + JWT);
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
    }

    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();
            String user = claims.getSubject();
            String roles = (String) claims.get("roles");
            List<String> roleList = Arrays.asList(roles.split("\\s*,\\s*"));
            List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
            for (int i = 0; i < roleList.size(); i++) {
                SimpleGrantedAuthority abv = new SimpleGrantedAuthority(roleList.get(i));
                grantedAuths.add(abv);
            }
            return user != null ? new UsernamePasswordAuthenticationToken(user, null, grantedAuths) : null;
        }
        return null;
    }
}
