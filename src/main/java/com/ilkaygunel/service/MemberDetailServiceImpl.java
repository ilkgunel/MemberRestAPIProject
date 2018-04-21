package com.ilkaygunel.service;

import com.ilkaygunel.entities.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class MemberDetailServiceImpl extends BaseService implements UserDetailsService{

    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException{
        Member member = memberRepository.findByEmail(emailAddress);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(member.getRoleOfMember().getRole()));
        return new User(member.getEmail(),member.getPassword(),grantedAuthorities);
    }
}
