package de.hdm_stuttgart.se2.softwareProject.mediathek.driver;

import java.io.File;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedialist;
import de.hdm_stuttgart.se2.softwareProject.mediathek.lists.ListFactory;
import de.hdm_stuttgart.se2.softwareProject.mediathek.models.MediaFactory;

public class MediaStorage {

	static private File[] scanPath(File f) {
		return f.listFiles();
	}

	public static IMedialist[] createMedialists(File f) {

		File[] scannedMedia = scanPath(f);
		IMedialist movies = ListFactory.getInstance("video", "scannedMovies");
		IMedialist audio = ListFactory.getInstance("audio", "scannedAudio");
		IMedialist books = ListFactory.getInstance("book", "scannedBooks");

		for (int i = 0; i < scannedMedia.length; i++) {
			// TODO: Methode um Dauer/Seitenanzahl zu ermitteln fehlt noch (size)
			String typ = null;

			if (scannedMedia[i].getName().toLowerCase().matches("^.*\\.(avi|mp4|wmv|mdk|mkv|mpeg|mpg)$")) {
				typ = "video";
				IMedia temp = MediaFactory.getInstance(typ, scannedMedia[i].getName(), false, scannedMedia[i]/*, size*/);
				movies.getContent().put(scannedMedia[i], temp);
			} else if (scannedMedia[i].getName().toLowerCase().matches("^.*\\.(mp3||wav|wma|aac|ogg)$")) {
				typ = "audio";
				IMedia temp = MediaFactory.getInstance(typ, scannedMedia[i].getName(), false, scannedMedia[i]/*, size*/);
				audio.getContent().put(scannedMedia[i], temp);
			} else if (scannedMedia[i].getName().toLowerCase().matches("^.*\\.(doc|docx|pdf|html)$")) {
				typ = "book";
				IMedia temp = MediaFactory.getInstance(typ, scannedMedia[i].getName(), false, scannedMedia[i]/*, size*/);
				books.getContent().put(scannedMedia[i], temp);
			} else {
				System.out.println("Info: Dateityp nicht unterstützt. " + scannedMedia[i] + " wurde nicht eingelesen.");
			}
		}
		
		IMedialist[] allMedia = {movies, audio, books};
		return allMedia;
	}
}
