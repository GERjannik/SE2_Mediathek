package de.hdm_stuttgart.se2.softwareProject.mediathek.driver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Settings {

	private static Logger log = LogManager.getLogger(Settings.class);
	
	// Name der Settingsdatei im Prokjekt Stammverzeichnis
	private File settings = new File("settings.json");

	public File getDirectory() {

		File directory = null;

		try {
			/* Settings-Datei wird über den Scanner eingelesen und dann Zeilenweise einen String angehängt */
			log.info("Settings-datei wird gelesen");
			Scanner input = new Scanner(settings);

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
			// TODO Auto-generated catch block
			log.info("Einlesen der Settings fehlgeschlagens");
			log.error(e.toString());
			e.printStackTrace();
		}
		
		log.info("Settings erfolgreich gelesen");
		return directory;

	}
	
	/**
	 * Erstellen eines neuen JSON Objekts und anschließendes schreiben
	 * @param path Pfad zum Verzeichnis mit den Filmen
	 */
	@SuppressWarnings("unchecked")
	public void setDirectory(String path) {

		JSONObject root = new JSONObject();       
		root.put("directory", path);

		try (PrintWriter writer = new PrintWriter(settings)) {
			writer.print(root.toJSONString());
			log.info("Verzeichnis erfolgreich in JSON gespeichert");
		} catch (FileNotFoundException e) {
			log.info("Speichern des Verzeichnisses in JSON fehlgeschlagen");
			log.error(e.toString());
			e.printStackTrace();
		}

	}
}
