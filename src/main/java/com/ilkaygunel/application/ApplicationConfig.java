package com.ilkaygunel.application;

import java.util.Locale;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConfig {

	public static ResourceBundleMessageSource messageSourceInstance = null;

	public String getValueOfProperty(String propertyName, String localeValue) {

		if (messageSourceInstance == null) {
			messageSourceInstance = new ResourceBundleMessageSource();
			messageSourceInstance.setBasenames("messageTexts");
			messageSourceInstance.setDefaultEncoding("UTF-8");
		}

		return messageSourceInstance.getMessage(propertyName, null, new Locale(localeValue));

	}
}