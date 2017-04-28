package de.hdm_stuttgart.se2.softwareProject.mediathek.lists;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.Settings;
import de.hdm_stuttgart.se2.softwareProject.mediathek.exceptions.InvalidTypeException;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedialist;

public class ListFactory {

	private static Logger log = LogManager.getLogger(Settings.class);
	
	public static IMedialist getInstance (String typ, String name) {
		switch (typ) {
		case "video":
			log.debug("Movielist " + name + " erfolgreich erstellt");
			return new Movielist(name);
		case "audio":
			log.debug("Audiolist " + name + " erfolgreich erstellt");
			return new Audiolist(name);
		case "book":
			log.debug("Booklist " + name + " erfolgreich erstellt");
			return new Booklist(name);
		default:
			log.catching(new InvalidTypeException());
			throw new InvalidTypeException();
		}
	}
}
