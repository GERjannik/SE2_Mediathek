package de.hdm_stuttgart.se2.softwareProject.mediathek.models;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hdm_stuttgart.se2.softwareProject.mediathek.exceptions.InvalidTypeException;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;

/**
 * 
 * Factory zur Erzeugung von Movie-, Audio sowie Book-Objekten, wobei Typ und
 * Methadaten beim Aufrufen der getInstance-Methode mit übergeben werden
 * müssen. Nicht-zulässige Listen-Typen haben eine InvalidTypeException zur
 * Folge.
 * 
 * @author ll040
 * 
 */
public class MediaFactory {

	private static Logger log = LogManager.getLogger(MediaFactory.class);
	
	/**
	 * 
	 * @param typ			Typ des Objektes (Book, Media oder Movie)
	 * @param title			Titel des Objektes
	 * @param favorite		Toggle für Favoriten-Status
	 * @param file			Zugrundeliegender Dateipfad des Objektes
	 * @param visible		Setzt Visibilität in des Playlists. Wenn Objekte aus Liste gelöscht werden, ist der Wert false, ansonsten true.
	 * @param duration		Länge des jeweiligen Mediums (in Sekunden bzw. Anzahl an Seiten)
	 * @param releaseDate	Release-Datum
	 * @param regisseur		Author (Book), Interpret (Audio) bzw. Regisseur (Movie) des zugrundeliegenden Mediums
	 * @param genre			Genre
	 * @param info			Anmerkungen vom Benutzer
	 * @return				Book-, Audio- oder Movie-Objekte mit entsprechendem Pfad und Metadaten
	 */
	public static IMedia getInstance (String typ, String title, File file, long duration,
			String releaseDate, String regisseur, String genre, String info, boolean favorite, boolean visible) {
		switch (typ) {
		case "video":
			log.debug("Neues Objekt vom Typ Video erstellt");
			return new Movie(typ, title, file, duration,
					releaseDate, regisseur, genre, info, favorite, visible);
		case "audio":
			log.debug("Neues Objekt vom Typ Audio erstellt");
			return new Audio(typ, title, file, duration, regisseur,
					genre, releaseDate, info, favorite, visible);
		case "book":
			log.debug("Neues Objekt vom Typ Buch erstellt");
			return new Book(typ, title, favorite, file/*, size*/, visible);
		default: 
			log.catching(new InvalidTypeException());
			throw new InvalidTypeException();
		}
	}
}
