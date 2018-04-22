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
				.authorizeRequests().antMatchers("/memberGetWebServiceEndPoint/**").permitAll()
				.and()
				.authorizeRequests().antMatchers("/activateMemberWebServiceEndpoint/**").permitAll()
				.and()
				.authorizeRequests().antMatchers("/memberPostWebServiceEndPoint/saveOneUserMember")
				.access("hasAnyRole('ROLE_ADMIN,ROLE_USER')")
				.and()
				.authorizeRequests().antMatchers("/memberPostWebServiceEndPoint/saveOneAdminMember").access("hasRole('ROLE_ADMIN')")
				.and()
				.authorizeRequests().antMatchers(("/memberUpdateWebServiceEndpoint/updateOneUserMember"))
				.access("hasAnyRole('ROLE_ADMIN,ROLE_USER')")
				.and()
				.authorizeRequests().antMatchers(("/memberUpdateWebServiceEndpoint/updateOneAdminMember"))
				.access("hasRole('ROLE_ADMIN')")
				.and()
				.authorizeRequests().antMatchers(("/memberUpdateWebServiceEndpoint/updateBulkUserMember"))
				.access("hasAnyRole('ROLE_ADMIN,ROLE_USER')")
				.and()
				.authorizeRequests().antMatchers(("/memberUpdateWebServiceEndpoint/updateBulkAdminMember"))
				.access("hasRole('ROLE_ADMIN')")
				.and()
				.authorizeRequests().antMatchers(("/memberDeleteWebServiceEndPoint/deleteOneUserMember/**"))
				.access("hasAnyRole('ROLE_ADMIN,ROLE_USER')")
				.and()
				.authorizeRequests().antMatchers(("/memberDeleteWebServiceEndPoint/deleteOneAdminMember/**"))
				.access("hasRole('ROLE_ADMIN')")
				.and()
				.authorizeRequests().antMatchers(("/memberDeleteWebServiceEndPoint/deleteBulkUserMember"))
				.access("hasAnyRole('ROLE_ADMIN,ROLE_USER')")
				.and()
				.authorizeRequests().antMatchers(("/memberDeleteWebServiceEndPoint/deleteBulkAdminMember"))
				.access("hasRole('ROLE_ADMIN')")
				.and()
				.authorizeRequests().anyRequest().authenticated().and().httpBasic();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

}
