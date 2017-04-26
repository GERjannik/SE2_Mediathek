package de.hdm_stuttgart.se2.softwareProject.mediathek.models;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hdm_stuttgart.se2.softwareProject.mediathek.exceptions.InvalidTypeException;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;

public class MediaFactory {

	private static Logger log = LogManager.getLogger(MediaFactory.class);
	
	public static IMedia getInstance (String typ, String title, boolean favorite, File file, boolean visible, long duration,
			String releaseDate, String regisseur, String genre, String info) {
		switch (typ) {
		case "video":
			log.debug("Neues Objekt vom Typ Video erstellt");
			return new Movie(typ, title, favorite, file, visible, duration,
					releaseDate, regisseur, genre, info);
		case "audio":
			log.debug("Neues Objekt vom Typ Audio erstellt");
			return new Audio(typ, title, favorite, file, visible, duration, regisseur,
					genre, releaseDate, info);
		case "book":
			log.debug("Neues Objekt vom Typ Buch erstellt");
			return new Book(typ, title, favorite, file/*, size*/, visible);
		default: 
			log.catching(new InvalidTypeException());
			throw new InvalidTypeException();
		}
	}
}
