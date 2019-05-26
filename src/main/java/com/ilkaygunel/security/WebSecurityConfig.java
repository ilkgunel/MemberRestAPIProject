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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

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
        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()).and()
                .csrf().disable()
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

    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
        configuration.setExposedHeaders(Arrays.asList("Authorization", "x-auth-token", "x-requested-with", "x-xsrf-token"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "X-Auth-Token", "x-auth-token", "x-requested-with", "x-xsrf-token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
