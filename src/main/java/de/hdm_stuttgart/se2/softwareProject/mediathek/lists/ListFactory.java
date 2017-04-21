package de.hdm_stuttgart.se2.softwareProject.mediathek.lists;

import de.hdm_stuttgart.se2.softwareProject.mediathek.exceptions.InvalidTypeException;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedialist;

public class ListFactory {

	public static IMedialist getInstance (String typ, String name) {
		switch (typ) {
		case "video":
			return new Movielist(name);
		case "audio":
			return new Audiolist(name);
		case "book":
			return new Booklist(name);
		default: 
			throw new InvalidTypeException();
		}
	}
}
