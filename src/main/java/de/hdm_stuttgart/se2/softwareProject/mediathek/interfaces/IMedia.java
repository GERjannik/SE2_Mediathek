package de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces;

import java.io.File;

/**
 * Interface für alle gängigen Medien-Typen einer Mediathek (Book, Audio,
 * Movie). Das Interface definiert Methoden zum Auslesen und Setzen folgender
 * generischer Metadaten: 
 * 
 * - Titel
 * - Erscheinungsdatum
 * - Regisseur
 * - Genre
 * - Info (vom Benutzer definiert)
 * - Ranking
 * - Favoriten-Toggle
 * 
 * Es enthält zusetzlich eine Methode zum Öffnen des Mediums.
 * 
 * @author ll040
 * 
 *
 */
public interface IMedia {

	void getDetails();
	void removeMedia();
	String getTitle();
	File getFile();
	String getTyp();
	void setTitle(String title);
	void setFavorite(boolean favorite);
	void setDate(String date);
	void setRegisseur(String regisseur);
	void setGenre(String genre);
	void setInfo(String info);
	void setRanking(int ranking);
	void open();
	boolean getFavorite();

}