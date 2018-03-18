package com.ilkaygunel.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:sqlQueries.properties")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private Environment env;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/memberGetWebServiceEndPoint/**").permitAll().and()
				.authorizeRequests().antMatchers("/memberPostWebServiceEndPoint/saveOneUserMember")
				.access("hasAnyRole('ROLE_ADMIN,ROLE_USER')").and().authorizeRequests()
				.antMatchers("/memberPostWebServiceEndPoint/saveOneAdminMember").access("hasRole('ROLE_ADMIN')").and()
				.authorizeRequests().anyRequest().authenticated().and().httpBasic();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(env.getProperty("membersByEmailQuery"))
				.authoritiesByUsernameQuery(env.getProperty("authoritiesByEmailQuery"))
				.passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.debug(true);
	}

}
