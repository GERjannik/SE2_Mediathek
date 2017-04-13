package de.hdm_stuttgart.se2.softwareProject.mediathek.driver;

import java.io.File;

import javax.rmi.CORBA.StubDelegate;

import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;
import de.hdm_stuttgart.se2.softwareProject.mediathek.models.MediaFactory;
import de.hdm_stuttgart.se2.softwareProject.mediathek.models.Movielist;
import uk.co.caprica.vlcj.player.MediaMeta;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.headless.HeadlessMediaPlayer;

public class MediaStorage {
	
	static private File[] scanPath(File f) {
		return f.listFiles();
	}
	
	
	// Test Path "/stud/ll040/Desktop/DB1_Tutorial_gr-1.mp4"
	public static MediaMeta readMetaData(String path) {
		MediaPlayerFactory factory = new MediaPlayerFactory();
		MediaMeta meta = factory.getMediaMeta(path, true);
		return meta;
	}
	
	public static IMedia createMovie(int id, String path) {
		String title;
		boolean favorite = false;
		File file = null;
		int duration;
		
		MediaMeta meta = readMetaData(path);
		
		title = meta.getTitle();
		duration = (int) meta.getLength();
		meta.release();
		
		IMedia movie = MediaFactory.getInstance("video", id, title, favorite, file, duration);
		return movie;
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
