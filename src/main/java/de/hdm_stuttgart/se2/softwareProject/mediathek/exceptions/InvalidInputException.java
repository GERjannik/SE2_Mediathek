package de.hdm_stuttgart.se2.softwareProject.mediathek.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InvalidInputException extends RuntimeException {

	private static final long serialVersionUID = 1;

	public InvalidInputException() {
		Logger log = LogManager.getLogger(InvalidInputException.class);
		log.error("Falsche Eingabe in " + this.getClass());
	}
	
	@Override
	public String toString() {
		return "Ivalid user-input";
	}
}
