package de.hdm_stuttgart.se2.softwareProject.mediathek.driver;

import java.io.File;
import de.hdm_stuttgart.se2.softwareProject.mediathek.Models.Movie;
import de.hdm_stuttgart.se2.softwareProject.mediathek.Models.Movielist;

public class MediaStorage {
	
	static private File[] scanPath(File f) {
		return f.listFiles();
	}
	
	public static Movielist createMovieInList(File f) {
		File[] scannedMovies = scanPath(f);
		Movielist allFilms = new Movielist();
		allFilms.createMap();
		for (int i = 0; i < scannedMovies.length; i++) {
			allFilms.content.put(i, new Movie(scannedMovies[i].getName(), false, scannedMovies[i]));
		}
		return allFilms;
	}
}
