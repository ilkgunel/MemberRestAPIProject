package com.ilkaygunel.application;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConfig {

	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("messageTexts");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
}
