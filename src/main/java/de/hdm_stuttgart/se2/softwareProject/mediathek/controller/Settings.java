package de.hdm_stuttgart.se2.softwareProject.mediathek.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedialist;
import de.hdm_stuttgart.se2.softwareProject.mediathek.lists.ListFactory;

/**
 * 
 * 
 * Klasse mit File Objekt als Repräsentation des Dateipfades. Methoden zum 
 * Auslesen, Wiedergeben und Setzen des Pfades für die 
 * Medien mittels eines settings.json Datei. Handling mittels eines
 * JSON Objektes.
 * Enthält IMedialist Objekte zum Speichern aller eingelesenen Medien. 
 *
 *@author ll040
 *
 */
public class Settings {

	private static Logger log = LogManager.getLogger(Settings.class);
	// Name der Settingsdatei im Prokjekt Stammverzeichnis
	private File mediaDirectory;
	
	public Settings() {
		
	}
	
	/**
	 * Rückgabe des Dateipfades der Medien
	 * @return File Objekt als Handler für den Dateipfad der Medien-
	 * 		   dateien
	 */
	public File getMediaDirectory() {
		return mediaDirectory;
	}
	
	
	/**
	 * Einlesen des Dateipfades der Medien-Dateien aus des settings.json.
	 * Parsen der settings.json zur Erstellung eines JSON Objektes, aus
	 * dem der Pfad gelesen werden kann.
	 */
	public void readDirectory() {

		File directory = null;

		try {
			/* Settings-Datei wird über den Scanner eingelesen und dann Zeilenweise einen String angehängt */
			log.info("Settings-Datei wird gelesen");
			Scanner input = new Scanner(new File ("settings.json"));

			StringBuilder jsonIn = new StringBuilder();

			while (input.hasNextLine()) {
				jsonIn.append(input.nextLine());
			}

			// Der String wird über einen JSON Parser geparst und anschließend an ein JSON objekt übergeben
			JSONParser parser = new JSONParser();
			JSONObject root = (JSONObject) parser.parse(jsonIn.toString());

			// Der Dateiname wird aus dem JSON Objekt geholt und ein neues File Objekt erstellt
			directory = new File(root.get("directory").toString());
			input.close();
			
		} catch (FileNotFoundException | ParseException e) {
			log.info("Einlesen der Settings fehlgeschlagens");
			log.catching(e);
			e.printStackTrace();
		}
		
		log.info("Settings erfolgreich gelesen");
		this.mediaDirectory = directory;

	}
	
	/**
	 * Erstellen eines neuen JSON Objekts und anschließendes schreiben
	 * @param path Pfad zum Verzeichnis mit den Filmen
	 */
	public void setDirectory(String path) {
		
		HashMap<String,Object> pfad = new HashMap<String,Object>();
		pfad.put("directory", path);
		JSONObject root = new JSONObject(pfad);
		

		try (PrintWriter writer = new PrintWriter(new File ("settings.json"))) {
			writer.print(root.toJSONString());
			log.info("Verzeichnis erfolgreich in JSON gespeichert");
		} catch (FileNotFoundException e) {
			log.info("Speichern des Verzeichnisses in JSON fehlgeschlagen");
			log.catching(e);;
			e.printStackTrace();
		}

	}
}
