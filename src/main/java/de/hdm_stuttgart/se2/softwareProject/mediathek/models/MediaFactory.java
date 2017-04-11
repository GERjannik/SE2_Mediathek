package de.hdm_stuttgart.se2.softwareProject.mediathek.models;

import java.io.File;

import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;

public class MediaFactory {

	public static IMedia getInstance (String typ, String title, boolean favorite, File file/*, int size*/) {
		switch (typ) {
		case "video":
			return new Movie(title, favorite, file/*, size*/);
		case "audio":
			return new Audio(title, favorite, file/*, size*/);
		case "book":
			return new Book(title, favorite, file/*, size*/);
		default: 
			return null; // TODO: Statt null soll Exception geworfen werden
		}
	}
}
