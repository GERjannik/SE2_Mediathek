package de.hdm_stuttgart.se2.softwareProject.mediathek.driver;

import java.io.File;
import java.util.HashMap;

import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;

public class App {

	public static void main(String[] args) {
		// TODO Dateiverzeichnis manuell vom Benutzer festlegen lassen
		Settings s = new Settings(new File ("/stud/js329/Documents/testVideos/"));
		//HashMap<Integer, IMedia> allMedia = MediaStorage.createMovieInList(s.getDirectory());
	}

}
