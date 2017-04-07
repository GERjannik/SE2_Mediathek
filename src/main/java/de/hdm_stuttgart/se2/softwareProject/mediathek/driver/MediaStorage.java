package de.hdm_stuttgart.se2.softwareProject.mediathek.driver;

import java.io.File;

import de.hdm_stuttgart.se2.softwareProject.mediathek.models.Movielist;

public class MediaStorage {
	
	static private File[] scanPath(File f) {
		return f.listFiles();
	}
	
	public static Movielist createMovieInList(File f) {
		File[] scannedMedia = scanPath(f);
		String typ;
		Movielist allFilms = new Movielist();
		allFilms.createMap();
		for (int i = 0; i < scannedMedia.length; i++) {
			if (scannedMedia[i].getName().toLowerCase().matches("^.*\\.(avi|mp4|wmv|mdk|mkv|mpeg|mpg)$")) {
				typ = "video";
			} else if (scannedMedia[i].getName().toLowerCase().matches("^.*\\.(mp3||wav|wma|aac|ogg)$")) {
				typ = "audio";
			} else if (scannedMedia[i].getName().toLowerCase().matches("^.*\\.(doc|docx|pdf|html)$")) {
				typ = "book";
			}
			// TODO: new Movie durch MediaFactory austauschen
			//allFilms.content.put(i, new Movie(scannedMedia[i].getName(), false, scannedMedia[i]));
		}
		return allFilms;
	}
}
