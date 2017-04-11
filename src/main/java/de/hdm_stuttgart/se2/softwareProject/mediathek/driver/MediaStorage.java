package de.hdm_stuttgart.se2.softwareProject.mediathek.driver;

import java.io.File;
import java.util.HashMap;


import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;
import de.hdm_stuttgart.se2.softwareProject.mediathek.models.MediaFactory;
import de.hdm_stuttgart.se2.softwareProject.mediathek.models.Medialist;

public class MediaStorage {
	
	static private File[] scanPath(File f) {
		return f.listFiles();
	}
	
	public static Medialist createMovieInList(File f) {
		
		File[] scannedMedia = scanPath(f);
		Medialist allMedia = null;
		allMedia.createMap();
		int videoId = 1000;
		int audioId = 2000;
		int bookId = 3000;
		
		for (int i = 0; i < scannedMedia.length; i++) {
			
			String typ = null;
			int id = 0;
			
			if (scannedMedia[i].getName().toLowerCase().matches("^.*\\.(avi|mp4|wmv|mdk|mkv|mpeg|mpg)$")) {
				typ = "video";
				id = ++videoId;
			} else if (scannedMedia[i].getName().toLowerCase().matches("^.*\\.(mp3||wav|wma|aac|ogg)$")) {
				typ = "audio";
				id = ++audioId;
			} else if (scannedMedia[i].getName().toLowerCase().matches("^.*\\.(doc|docx|pdf|html)$")) {
				typ = "book";
				id = ++bookId;
			} else {
				System.out.println("Info: Dateityp nicht unterstützt. " + scannedMedia[i] + " wurde nicht eingelesen.");
			}
			// TODO: Methode um Dauer/Seitenanzahl zu ermitteln fehlt noch (size)
			IMedia temp = MediaFactory.getInstance(typ, id, scannedMedia[i].getName(), false, scannedMedia[i]/*, size*/);
			allMedia.getContent().put(id, temp);
		}
		return allMedia;
	}
}
