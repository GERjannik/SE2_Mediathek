package de.hdm_stuttgart.se2.softwareProject.mediathek.driver;

import java.io.File;

public class Settings {

	private File directory;

	/**
	 * @param directory
	 */
	public Settings(File directory) {
		this.directory = directory;
	}

	public File getDirectory() {
		return directory;
	}

	public void setDirectory(File directory) {
		this.directory = directory;
	}
}
