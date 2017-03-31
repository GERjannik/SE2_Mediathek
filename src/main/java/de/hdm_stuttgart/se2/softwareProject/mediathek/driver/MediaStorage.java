package de.hdm_stuttgart.se2.softwareProject.mediathek.driver;

import java.io.File;
import java.nio.file.Path;

import de.hdm_stuttgart.se2.softwareProject.mediathek.Models.Movie;

public class MediaStorage {
	
	private File[] scanPath(Path p) {
		return p.toFile().listFiles();
	}
	
	public void createMovie(Path p) {
		File[] f = scanPath(p);
		for (File x : f) {
			Movie temp = new Movie(x.getName(), false, x);
		}
	}
}
