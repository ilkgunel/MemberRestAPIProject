package com.ilkaygunel.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers("/memberGetWebServiceEndPoint/**")
                //.permitAll()
                .access("hasAnyRole('ROLE_ADMIN,ROLE_USER')")
                .and()
                .authorizeRequests().antMatchers("/activateMemberWebServiceEndpoint/**").permitAll()
                .and()
                .authorizeRequests().antMatchers("/memberPostWebServiceEndPoint/saveUserMember")
                .access("hasAnyRole('ROLE_ADMIN,ROLE_USER')")
                .and()
                .authorizeRequests().antMatchers("/memberPostWebServiceEndPoint/saveAdminMember")
                .access("hasRole('ROLE_ADMIN')")
                .and()
                .authorizeRequests().antMatchers(("/memberUpdateWebServiceEndpoint/updateUserMember"))
                .access("hasAnyRole('ROLE_ADMIN,ROLE_USER')")
                .and()
                .authorizeRequests().antMatchers(("/memberUpdateWebServiceEndpoint/updateAdminMember"))
                .access("hasRole('ROLE_ADMIN')")
                .and()
                .authorizeRequests().antMatchers(("/memberDeleteWebServiceEndPoint/deleteUserMember/**"))
                .access("hasAnyRole('ROLE_ADMIN,ROLE_USER')")
                .and()
                .authorizeRequests().antMatchers(("/memberDeleteWebServiceEndPoint/deleteAdminMember/**"))
                .access("hasRole('ROLE_ADMIN')")
                .and()
                .authorizeRequests().anyRequest().authenticated().and()
                .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

}
