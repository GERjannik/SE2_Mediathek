package de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces;

import java.io.File;
import java.util.Map;

/**
 * 
 * Interface für das Handling von assoziativen Speicherstrukturen (Maps)
 * für Book-, Audio- und Movie-Objekte. Erlaubt das Anlegen eines Assoziativ-
 * speichers, das hinzufügen und entfernen von IMedia-Objekten und weitere
 * aktionen wie Rückgabe des Speicherinhaltes, des Speichernamens sowie die
 * Auflistung des Speicherinhaltes.
 * 
 * @author ll040
 *
 */
public interface IMedialist {

	Map<File, IMedia> getContent();
	String getName();
	String getType();
	void removeMedia(IMedia m);
	void printList();
	void setName(String name);
	void addMedia(IMedia media);
}