package de.hdm_stuttgart.se2.softwareProject.mediathek.driver;

import java.io.File;

import javax.rmi.CORBA.StubDelegate;

import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;
import de.hdm_stuttgart.se2.softwareProject.mediathek.models.MediaFactory;
import de.hdm_stuttgart.se2.softwareProject.mediathek.models.Movielist;

public class MediaStorage {
	
	static private File[] scanPath(File f) {
		return f.listFiles();
	}
	
	public static Movielist createMovieInList(File f) {
		File[] scannedMedia = scanPath(f);
		Movielist allFilms = new Movielist();
		allFilms.createMap();
		for (int i = 0; i < scannedMedia.length; i++) {
			
			String typ = null;
			
			if (scannedMedia[i].getName().toLowerCase().matches("^.*\\.(avi|mp4|wmv|mdk|mkv|mpeg|mpg)$")) {
				typ = "video";
			} else if (scannedMedia[i].getName().toLowerCase().matches("^.*\\.(mp3||wav|wma|aac|ogg)$")) {
				typ = "audio";
			} else if (scannedMedia[i].getName().toLowerCase().matches("^.*\\.(doc|docx|pdf|html)$")) {
				typ = "book";
			} else {
				System.out.println("Info: Dateityp nicht unterstützt. " + scannedMedia[i] + " wurde nicht eingelesen.");
			}
			// TODO: Methode um Dauer/Seitenanzahl zu ermitteln fehlt noch (size)
			int size = 0;
			IMedia temp = MediaFactory.getInstance(typ, i, scannedMedia[i].getName(), false, scannedMedia[i], size);
		}
		return allFilms;
	}
}
