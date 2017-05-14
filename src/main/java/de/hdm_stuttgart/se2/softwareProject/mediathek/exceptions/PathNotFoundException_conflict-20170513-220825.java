package de.hdm_stuttgart.se2.softwareProject.mediathek.exceptions;

public class PathNotFoundException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1;

	@Override
	public String toString() {
		return "Path not found";
	}
}
