package de.hdm_stuttgart.se2.softwareProject.mediathek.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.xml.XmlConfigurationFactory;

public class Logging {
	
	public Logging() throws IOException {
		
		// Configuration Objekt erstellen
		
		// Configuration Datei in Input Stream laden und so zur Konfiguration vorbereiten
		File log_config = new File((getClass().getClassLoader().getResource("log_config.xml")).getFile());
		FileInputStream log_input = new FileInputStream(log_config);
		
		// Configuration Objekt erstellen
		ConfigurationSource source = new ConfigurationSource(log_input);
		ConfigurationFactory configFactory = XmlConfigurationFactory.getInstance();
		Configuration configuration = configFactory.getConfiguration(source);
		
		// TODO Appender Objekt erstellen
		// FileAppender appender = FileAppender.createAppender("/log/logfile.log", true, locking, name, immediateFlush, ignore, false, false, org.apache.log4j.PatternLayout, false, advertise, advertiseUri, config)();

	}
}