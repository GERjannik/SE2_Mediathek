package de.hdm_stuttgart.se2.softwareProject.mediathek.models;

import java.io.File;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;

public class MediaFactory {

	public static IMedia getInstance (String typ, String title, boolean favorite, File file, boolean visible, long duration,
			String releaseDate, String regisseur, String genre, String info) {
		switch (typ) {
		case "video":
			return new Movie(typ, title, favorite, file, visible, duration,
					releaseDate, regisseur, genre, info);
		case "audio":
			return new Audio(typ, title, favorite, file, visible, duration, regisseur,
					genre, releaseDate);
		case "book":
			return new Book(typ, title, favorite, file/*, size*/, visible);
		default: 
			return null; // TODO: Statt null soll Exception geworfen werden
		}
	}
}
