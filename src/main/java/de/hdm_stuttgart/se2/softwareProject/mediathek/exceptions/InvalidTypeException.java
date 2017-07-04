package de.hdm_stuttgart.se2.softwareProject.mediathek.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InvalidTypeException extends Exception {
		
	private static final long serialVersionUID = 1;

	public InvalidTypeException() {
		Logger log = LogManager.getLogger(InvalidTypeException.class);
		log.error("Falscher Typ in " + this.getClass());
	}
	
	@Override
	public String toString() {
		return "Invalid Type. Object must be of type 'video', 'audio' or 'book'";
	}
}
