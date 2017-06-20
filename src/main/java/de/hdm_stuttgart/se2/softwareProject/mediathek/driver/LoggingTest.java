package de.hdm_stuttgart.se2.softwareProject.mediathek.driver;

import static de.hdm_stuttgart.se2.softwareProject.mediathek.controller.Logging.getContext;

import java.io.IOException;

import org.apache.logging.log4j.Logger;; 

public class LoggingTest {

	public static void main(String[] args) {
		try {
			// Logger modelsLogger = getContext().getLogger("de.hdm_stuttgart.se2.softwareProject.mediathek.models");
			Logger movieLogger = getContext().getLogger("de.hdm_stuttgart.se2.softwareProject.mediathek.models.Movie");
			
			movieLogger.trace("Test");
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
