package de.hdm_stuttgart.se2.softwareProject.mediathek.driver;

import java.io.File;
import java.util.Scanner;

import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedialist;
import de.hdm_stuttgart.se2.softwareProject.mediathek.lists.ListFactory;
import de.hdm_stuttgart.se2.softwareProject.mediathek.models.MediaFactory;
import uk.co.caprica.vlcj.player.MediaMeta;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;

public class MediaStorage {
	
	// Test Path "/stud/ll040/Desktop/DB1_Tutorial_gr-1.mp4"
	public static MediaMeta readMetaData(String path) {
		MediaPlayerFactory factory = new MediaPlayerFactory();
		MediaMeta meta = factory.getMediaMeta(path, true);
		return meta;
	}
	
	/* public static IMedia createMovie(int id, String path) {
		String title;
		boolean favorite = false;
		File file = new File(path);
		int duration;
		
		MediaMeta meta = readMetaData(path);
		
		title = meta.getTitle();
		duration = (int) meta.getLength();
		meta.release();
		
		IMedia movie = MediaFactory.getInstance("video", title, favorite, file, true, duration);
		return movie;
	} */ 

	public static IMedialist[] mediaScan(File f) {

		// Angegebener Ordner wird gescannt und alle Dateien in Array geschrieben
		File[] scannedMedia = f.listFiles();
		
		// HashMaps für Medien werden erzeugt
		IMedialist movies = ListFactory.getInstance("video", "scannedMovies");
		IMedialist audio = ListFactory.getInstance("audio", "scannedAudio");
		IMedialist books = ListFactory.getInstance("book", "scannedBooks");

		// Aus jeder Datei des Arrays wird ein Objekt erstellt und der richtigen HashMap zugeordnet
		for (int i = 0; i < scannedMedia.length; i++) {
			String typ = null;
			MediaMeta meta = readMetaData(scannedMedia[i].toString());

			if (scannedMedia[i].getName().toLowerCase().matches("^.*\\.(avi|mp4|wmv|mdk|mkv|mpeg|mpg)$")) {
				typ = "video";
				IMedia temp = MediaFactory.getInstance(typ, meta.getTitle(), false, scannedMedia[i], meta.getLength(), true);
				movies.getContent().put(scannedMedia[i], temp);
			} else if (scannedMedia[i].getName().toLowerCase().matches("^.*\\.(mp3||wav|wma|aac|ogg)$")) {
				typ = "audio";
				IMedia temp = MediaFactory.getInstance(typ, meta.getTitle(), false, scannedMedia[i], meta.getLength(), true);
				audio.getContent().put(scannedMedia[i], temp);
			} /*else if (scannedMedia[i].getName().toLowerCase().matches("^.*\\.(doc|docx|pdf|html)$")) {
				typ = "book";
				IMedia temp = MediaFactory.getInstance(typ, meta.getTitle(), false, scannedMedia[i], size, true);
				books.getContent().put(scannedMedia[i], temp);
			}*/ else {
				System.out.println("Info: Dateityp nicht unterstützt. " + scannedMedia[i] + " wurde nicht eingelesen.");
			}
			meta.release();
		}
		 // Die drei Maps werden in ein Array geschrieben und zurückgegeben
		IMedialist[] allMedia = {movies, audio, books};
		return allMedia;
	}
	
	public static void deleteMedia(IMedia m) {
		Scanner s = new Scanner(System.in);
		
		System.out.println("Möchtest du das Medium " + m.getTitle() +  " von der Festplatte löschen? (Ja/Nein)\n");
		
		String input = s.nextLine();
		if (input.equals("Ja") || input.equals("ja")) {
			System.out.println("Das Medium " + m.getTitle() + " wurde von der Festplatte gelöscht");
			m.getFile().delete();
			m.removeMedia();
		} else if (input.equals("Nein") || input.equals("nein")) {
			System.out.println("Das Medium " + m.getTitle() + " wurde aus der Mediathek entfernt");
			m.removeMedia();
		} else {
			System.out.println("ungültige Eingabe");
		}
		s.close();
	}
}
