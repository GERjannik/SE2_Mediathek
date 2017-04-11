package de.hdm_stuttgart.se2.softwareProject.mediathek.driver;

import java.io.File;
import java.util.HashMap;

import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedialist;

public class App {

	public static void main(String[] args) {
		// TODO Dateiverzeichnis manuell vom Benutzer festlegen lassen
		Settings s = new Settings(new File ("/stud/js329/Documents/testVideos/"));
		IMedialist[] scannedContent = MediaStorage.mediaScan(s.getDirectory());
		IMedialist movies = scannedContent[0];
		IMedialist audio = scannedContent[1];
		IMedialist books = scannedContent[2];
	}

}
