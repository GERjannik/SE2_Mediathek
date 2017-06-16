package de.hdm_stuttgart.se2.softwareProject.mediathek.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.xml.XmlConfigurationFactory;

public class Logging {

	public static LoggerContext getContext() throws IOException {
		// Configuration Objekt erstellen
		
		// Configuration Datei in Input Stream laden und so zur Konfiguration vorbereiten
		
		// File log_config = new File((Logging.class.getClassLoader().getResource("log_config.xml")).getFile());
		File log_config = new File("C:\\Users\\Leo\\SE2_Projekt_Mediathek\\src\\main\\resources\\log_config.xml");
		FileInputStream log_input = new FileInputStream(log_config);
		
		// Configuration Objekt erstellen
		ConfigurationSource source = new ConfigurationSource(log_input);
		ConfigurationFactory configFactory = XmlConfigurationFactory.getInstance();
		Configuration configuration = configFactory.getConfiguration(source);
		
		// LoggerContext
		LoggerContext context = new LoggerContext("Mediathek DJLB Logger");
		
		// Starten des Logging Systems
		context.start(configuration);
		return context;
		
		// TODO Constructor in main einfügen und Logger in Klassen anpassen 
		// TODO --> context.getLogger(de.hdm_stuttgart.se2.softwareProject.mediathek.controller);

	}
}