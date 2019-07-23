package com.ilkaygunel.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilkaygunel.exception.CustomException;
import com.ilkaygunel.exception.RestExceptionHandler;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JWTAuthenticationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            Authentication authentication = TokenAuthenticationService.getAuthentication((HttpServletRequest) request);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception exception) {

            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;

            RestExceptionHandler restExceptionHandler = new RestExceptionHandler();
            ResponseEntity<Object> responseEntity = restExceptionHandler.handleGenericException(exception);

            if (exception instanceof ExpiredJwtException) {
                res.setStatus(HttpStatus.UNAUTHORIZED.value());
            } else if (exception instanceof CustomException) {
                res.setStatus(HttpStatus.UNAUTHORIZED.value());
            } else {
                res.setStatus(responseEntity.getStatusCode().value());
            }

            res.setContentType(MediaType.APPLICATION_JSON.toString());

            ObjectMapper mapper = new ObjectMapper();
            PrintWriter out = res.getWriter();
            out.print(mapper.writeValueAsString(responseEntity.getBody()));
            out.flush();

            return;
        }

        filterChain.doFilter(request, response);
    }
}
