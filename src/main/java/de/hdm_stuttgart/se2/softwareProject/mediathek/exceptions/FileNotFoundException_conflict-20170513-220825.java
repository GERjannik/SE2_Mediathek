package de.hdm_stuttgart.se2.softwareProject.mediathek.exceptions;

public class FileNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1;

	@Override
	public String toString() {
		return "File not found";
	}
}
