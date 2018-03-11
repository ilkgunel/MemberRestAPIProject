package com.ilkaygunel.util;

import java.io.IOException;
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

	public Logger getLoggerForMemberUpdating(Class<?> clazz) {
		return getLogger("./memberUpdating.log", clazz);
	}

	public Logger getLoggerForMemberDeleting(Class<?> clazz) {
		return getLogger("./memberDeleting.log", clazz);
	}

	private Logger getLogger(String logFileName, Class<?> clazz) {
		LogManager.getLogManager().reset();
		Logger logger = Logger.getLogger(clazz.getName());
		try {
			Handler fileHandler = new FileHandler(logFileName, true);
			Formatter simpleFormatter = new SimpleFormatter();
			fileHandler.setFormatter(simpleFormatter);
			logger.addHandler(fileHandler);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "An error occured while getting logger object. Error is:" + e.getMessage());
		}

		return logger;
	}
}
