package com.ilkaygunel.logging;

import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggingUtil {
	public static Logger getLoggerForMemberSaving(Class<?> clazz) {
		LogManager.getLogManager().reset();
		Logger logger = Logger.getLogger(clazz.getName());
		try {
			Handler fileHandler = new FileHandler("./memberSaving.log");
			Formatter simpleFormatter = new SimpleFormatter();
			fileHandler.setFormatter(simpleFormatter);
			logger.addHandler(fileHandler);
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "An error occured while getting logger object. Error is:" + ex.getMessage());
		}
		return logger;
	}
}
