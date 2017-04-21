package de.hdm_stuttgart.se2.softwareProject.mediathek.exceptions;

public class InvalidInputException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1;

	@Override
	public String toString() {
		return "Ivalid user-input";
	}
}
