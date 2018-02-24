package com.ilkaygunel.logging;

import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggingUtil {
	public Logger getLoggerForMemberSaving(Class<?> clazz) {
		return getLogger("./memberSaving.log", clazz);
	}

	public Logger getLoggerForMemberGetting(Class<?> clazz) {
		return getLogger("./memberGetting.log", clazz);
	}

	private Logger getLogger(String logFileName, Class<?> clazz) {
		LogManager.getLogManager().reset();
		Logger logger = Logger.getLogger(clazz.getName());
		try {
			Handler fileHandler = new FileHandler(logFileName);
			Formatter simpleFormatter = new SimpleFormatter();
			fileHandler.setFormatter(simpleFormatter);
			logger.addHandler(fileHandler);
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "An error occured while getting logger object. Error is:" + ex.getMessage());
		}
		return logger;
	}
}
