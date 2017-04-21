package de.hdm_stuttgart.se2.softwareProject.mediathek.exceptions;

public class InvalidTypeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1;

	@Override
	public String toString() {
		return "Invalid Type. Object must be of type 'video', 'audio' or 'book'";
	}
}
