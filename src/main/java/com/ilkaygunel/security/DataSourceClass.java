package com.ilkaygunel.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@PropertySource(ignoreResourceNotFound = true,value = "classpath:application.properties")
public class DataSourceClass {
	@Autowired
	private Environment environment;

	@Bean
	public DriverManagerDataSource dataSource() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();

		driverManagerDataSource.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"));
		driverManagerDataSource.setUrl(environment.getProperty("spring.datasource.url"));
		driverManagerDataSource.setUsername(environment.getProperty("spring.datasource.username"));
		driverManagerDataSource.setPassword(environment.getProperty("spring.datasource.password"));
		return driverManagerDataSource;
	}
}
