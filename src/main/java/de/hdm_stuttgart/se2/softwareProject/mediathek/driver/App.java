package de.hdm_stuttgart.se2.softwareProject.mediathek.driver;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map.Entry;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.MediaStorage;
import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.Settings;
import de.hdm_stuttgart.se2.softwareProject.mediathek.exceptions.InvalidInputException;
import de.hdm_stuttgart.se2.softwareProject.mediathek.exceptions.InvalidTypeException;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedialist;
import de.hdm_stuttgart.se2.softwareProject.mediathek.lists.ListFactory;
import uk.co.caprica.vlcj.player.MediaMeta;

public class App {

	private static Logger log = LogManager.getLogger(App.class);

	public static void main(String[] args) throws InvalidTypeException, InvalidInputException {

		Settings s = new Settings();
		
		Scanner scan = new Scanner(System.in);
		ArrayList<IMedialist> allLists = new ArrayList<>();

		if (new File("settings.json").exists() && !(new File("settings.json").isDirectory())) {
			s.readDirectory();
		} else {
			boolean validInput = false;
			while (validInput == false) {
				System.out.println("Welches Verzeichnis soll nach Medien durchsucht werden?");
				File input = new File (scan.nextLine());
				if (input.exists() && input.isDirectory()) {
					validInput = true;
					s.setDirectory(input.toString());
				} else {
					log.debug("Kein gültiges Verzeichnis angegeben");
					System.out.println("Die Eingabe ist kein gültiges Verzeichnis");
				}
			}
			s.readDirectory();
		}

		IMedialist[] scannedContent = MediaStorage.mediaScan(s.getMediaDirectory());
		IMedialist movies = scannedContent[0];
		IMedialist audio = scannedContent[1];	
		
		allLists = MediaStorage.loadPlaylists(movies, audio);
		
		IMedialist movieFavorites = ListFactory.getInstance("video", "Favoriten (Video)");
		for (Entry<File, IMedia> i : movies.getContent().entrySet()) {
			if (i.getValue().getFavorite()) {
				movieFavorites.getContent().put(i.getKey(), i.getValue());
			}
		}
		IMedialist audioFavorites = ListFactory.getInstance("audio", "Favoriten (Audio)");
		for (Entry<File, IMedia> i : audio.getContent().entrySet()) {
			if (i.getValue().getFavorite()) {
				audioFavorites.getContent().put(i.getKey(), i.getValue());
			}
		}
		allLists.add(movieFavorites);
		allLists.add(audioFavorites);
		
		loop:while(true) {
			menu();
			boolean validInput = false;
			String input = scan.next();
			switch (input) {
			case "0":
				movies.printList();
				break;
			case "1":
				audio.printList();
				break;
			case "2":
				movies.printList();
				audio.printList();
				break;
			case "3":
				movies.printList();
				audio.printList();
				IMedia media;
				try {
					media = getInput(s, scan, movies, audio);
				} catch (InvalidInputException e) {
					System.out.println("Kein Medium mit diesem Titel gefunden. Kehre zurück ins Hauptmenü.");
					break;
				}
				try {
					MediaStorage.editMetaInformation(media, scan);
				} catch (ParseException e1) {
					log.catching(e1);
					log.error("Fehler beim Parsen in JSON Objekt beim Bearbeiten der Metadaten");
				}
				break;
			case "4":
				movies.printList();
				audio.printList();
				MediaStorage.deleteMedia(s, scan, movies, audio);
				break;
			case "5":
				while (validInput == false) {
					System.out.println("Welches Verzeichnis soll nach Medien durchsucht werden?");
					scan.nextLine();
					File f = new File (scan.nextLine());
					if (f.exists() && f.isDirectory()) {
						validInput = true;
						s.setDirectory(f.toString());
						s.readDirectory();
					} else {
						System.out.println("Die Eingabe ist kein gültiges Verzeichnis.");
					}
				}
				break;
			case "6":
				MediaStorage.mediaScan(s.getMediaDirectory());
				break;
			case "7":
				validInput = false;
				String typ = "";
				while (validInput == false) {
					System.out.println("Für welche Medien soll die Playlist erstellt werden? (0: Videos, 1: Audios)");
					String in = scan.next();
					if (in.equals("0")) {
						typ = "video";
						validInput = true;
					} else if (in.equals("1")) {
						typ = "audio";
						validInput = true;
					} else {
						System.out.println("Eingabe ungültig. Nur '0' oder '1' erlaubt");
					}
					scan.nextLine();
				}
				boolean correctName = false;
				String nameInput = null;
				while (correctName == false) {
					System.out.println("Wie soll die Playlist heißen?");
					nameInput = scan.nextLine();
					if (nameInput.isEmpty() || nameInput == null) {
						System.out.println("Kein Name für die Playlist eingegeben. Bitte wähle einen Namen.");
						continue;
					}
					// Schleife prüft, ob gewählter Name für eine Playlist schon existiert.
					if (!allLists.isEmpty()) {
						for (int i = 0; i < allLists.size(); i++) {
							if (allLists.get(i).getName().toLowerCase().equals(nameInput.toLowerCase())) {
								System.out.println("Playlist mit dem Namen " + allLists.get(i).getName() + " existiert bereits.\n"
										+ "Bitte wähle einen anderen Namen.");
								break;
							}
							if (i == allLists.size() - 1) {
								correctName = true;
							}
						}
					} else {
						break;
					}
				}
				IMedialist playlist = ListFactory.getInstance(typ, nameInput);
				allLists.add(playlist);
				break;
			case "8":
				try {
					for (IMedialist i : allLists) {
						System.out.println(i.getName());
					}
				} catch (NullPointerException e) {
					log.warn(e.getMessage() + "\nPlaylist nicht korrekt gelöscht.");
					System.out.println("Es existieren keine Playlists.");
				}
				break;
			case "9":
				allLists = editPlaylist(allLists, scan, s, movies, audio);
				break;
			case "10":
				try {
					MediaStorage.playMovie(s, scan, movies, audio);
				} catch (InvalidInputException e) {
					log.error("Kein passendes Medium zum Abspielen gefunden");
				}
				break;
			case "11":
				System.out.println("Bye");
				if (allLists.size() > 0) {
				MediaStorage.savePlaylists(allLists);
				}
				break loop;
			default:
				System.out.println("Ungültige Eingabe");
			}
		}
	}
	

	public static void menu() {
		System.out.println("Menü: \n"
				+ "0: Filme anzeigen\n"
				+ "1: Audios anzeigen\n"
				+ "2: Alle Medien anzeigen\n"
				+ "3: Metadaten bearbeiten\n"
				+ "4: Medium löschen\n"
				+ "5: Pfad setzen\n"
				+ "6: Medien neu einscannen\n"
				+ "7: Playlist erstellen\n"
				+ "8: Alle Playlists anzeigen\n"
				+ "9: Playlist bearbeiten\n"
				+ "10: Medium abspielen\n"
				+ "11: Programm beenden\n");
	}
	
	// TODO klären, wozu Parameter s benötigt wird
	/**
	 * Methode zur Auslesen von IMedia Objekten aus der Map eines IMedialist Objektes nach gegebenen User Input.
	 * 
	 * @param s Objekt vom Typ 'Settings'
	 * @param scan Objekt vom Typ 'Scanner'
	 * @param movies Objekt vom Typ 'IMedialist', enhält Map mit Movie Objekten
	 * @param audio Objekt vom Typ 'IMedialist', enhält Map mit Movie Objekten
	 * @return IMedia Objekt
	 * @throws InvalidInputException 
	 */
	public static IMedia getInput(Settings s, Scanner scan, IMedialist movies, IMedialist audio) throws InvalidInputException {
		System.out.println("Gib den Titel des Mediums ein");
		scan.nextLine();
		String input = scan.nextLine();
		for (Entry<File, IMedia> i : movies.getContent().entrySet()) {
			if (i.getValue().getTitle().equals(input)) {
				return i.getValue();
			}
		}
		for (Entry<File, IMedia> i : audio.getContent().entrySet()) {
			if (i.getValue().getTitle().equals(input)) {
				return i.getValue();
			}
		}
		throw new InvalidInputException();
	}

	public static ArrayList<IMedialist> editPlaylist(ArrayList<IMedialist> allLists, Scanner scan, Settings s, IMedialist movies, IMedialist audio) {
		IMedialist playlist;
		int choice;
		while (true) {
			System.out.println("Welche Playlist soll berbeitet werden?");
			for (int i = 2; i < allLists.size(); i++) {
				System.out.println(i + ": " + allLists.get(i).getName());
			}
			scan.nextLine();
			try { 
				choice = scan.nextInt();
				if (choice >= 0 && choice < allLists.size()) {
					playlist = allLists.get(choice);
					break;
				} else {
					System.out.println("Ungültige Eingabe");
				}
			} catch (InputMismatchException e) {
				log.warn("Ungültige Eingabe\n" + e.fillInStackTrace());
				System.out.println("Ungültige Eingabe");
			}
		}
		while(true) {
			System.out.println("0: weitere Medien zu Playlist " + playlist.getName() + " hinzufügen\n"
					+ "1: Name der Playlist " + playlist.getName() + " ändern\n"
					+ "2: Playlist " + playlist.getName() + " nicht weiter bearbeiten\n"
					+ "3: Playlist " + playlist.getName() + " löschen");
			String input = scan.next();
			switch (input) {
			case "0":
				System.out.println("Welches Medium soll zur Playlist " + playlist.getName() + " hinzugefügt werden?");
				try {
					if (playlist.getName().equals("Favoriten (Video)") || playlist.getName().equals("Favoriten (Audio)")) {
						IMedia in = getInput(s, scan, movies, audio);
						in.setFavorite(true);
						MediaMeta meta = MediaStorage.readMetaData(in.getFile());
						String settings = meta.getDescription();
						HashMap<String, Object> out = new HashMap<>();
						JSONObject root = (JSONObject) new JSONParser().parse(settings);
						out.put("infos", root.get("infos"));
						out.put("favorite", root.get("favorite"));
						out.put("visible", root.get("visible"));
						out.put("ranking", root.get("ranking"));
						out.replace("favorite", true);
						root = new JSONObject(out);
						meta.setDescription(root.toString());
						meta.save();
						meta.release();
						playlist.addMedia(in);
					}
					playlist.addMedia(getInput(s, scan, movies, audio));
				} catch (InvalidInputException e) {
					log.warn("Ungültige Eingabe. Eingegebener Titel nicht gefunden");
					log.catching(e);
				} catch (ParseException e) {
					log.catching(e);
					log.error("Parsen in JSON Objekt fehlgeschlagen");
				}
				break;
			case "1":
				boolean correctName = false;
				String nameInput = null;
				while (correctName == false) {
					System.out.println("Wie soll die Playlist heißen?");
					nameInput = scan.nextLine();

					// Schleife prüft, ob gewählter Name für eine Playlist schon existiert.
					if (!allLists.isEmpty()) {
						for (int i = 0; i < allLists.size(); i++) {
							if (allLists.get(i).getName().toLowerCase().equals(nameInput.toLowerCase())) {
								System.out.println("Playlist mit dem Namen " + allLists.get(i).getName() + " existiert bereits.\n"
										+ "Bitte wähle einen anderen Namen.");
								break;
							}
							if (i == allLists.size() - 1) {
								correctName = true;
							}
						}
					} else {
						break;
					}
				}
				playlist.setName(nameInput);
				break;
			case "2":
				return allLists;
			case "3":
				if (!playlist.getName().equals("Favoriten (Video)") && !playlist.getName().equals("Favoriten (Audio)")) {
					allLists.remove(choice);
				} else {
					log.info("Playlists mit Favoriten kann nicht gelöscht werden");
				}
				return allLists;
			default:
				System.out.println("Ungültige Eingabe!");
			}
		}
	}
} 
