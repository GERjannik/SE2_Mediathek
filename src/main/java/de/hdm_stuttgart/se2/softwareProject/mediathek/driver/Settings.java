package de.hdm_stuttgart.se2.softwareProject.mediathek.driver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Settings {

	// Name der Settingsdatei im Prokjekt Stammverzeichnis
	private File settings = new File("settings.json");


	public File getDirectory() {

		File directory = null;

		try {
			/* Settings-Datei wird über den Scanner eingelesen und dann Zeilenweise einen String angehängt */
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

		} catch (FileNotFoundException | ParseException e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());
			e.printStackTrace();
		}

		return directory;

	}
	
	/**
	 * Erstellen eines neuen JSON Objekts und anschließendes schreiben
	 * @param path Pfad zum Verzeichnis mit den Filmen
	 */
	public void setDirectory(String path) {
		
		HashMap<String,Object> pfad = new HashMap<String,Object>();
		pfad.put("directory", path);
		JSONObject root = new JSONObject(pfad);
		

		try (PrintWriter writer = new PrintWriter(settings)) {
			writer.print(root.toJSONString());

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());
			e.printStackTrace();
		}

	}
}
