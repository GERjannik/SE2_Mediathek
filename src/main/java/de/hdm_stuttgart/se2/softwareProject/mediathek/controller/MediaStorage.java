package de.hdm_stuttgart.se2.softwareProject.mediathek.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
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

import de.hdm_stuttgart.se2.softwareProject.mediathek.driver.App;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedialist;
import de.hdm_stuttgart.se2.softwareProject.mediathek.lists.ListFactory;
import de.hdm_stuttgart.se2.softwareProject.mediathek.models.MediaFactory;
import uk.co.caprica.vlcj.player.MediaMeta;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;

public class MediaStorage {

	private static Logger log = LogManager.getLogger(MediaStorage.class);

	/**
	 * Auslesen von Meta Daten aus den übergebenen Medien Dateien
	 * @param File Objekt der jeweiligen Medien-Datei
	 * @return MediaMeta Objekt mit Meta Informationen
	 */
	public static MediaMeta readMetaData(File file) {
		MediaPlayerFactory factory = new MediaPlayerFactory();
		MediaMeta meta = factory.getMediaMeta(file.toString(), true);
		return meta;
	}

	/**
	 * Genererieren einer ArrayList mit File Objekten sämtlicher Dateien
	 * des übergebenen Pfades. Durchitererieren der ArrayList. Generierung von von MediaMeta
	 * Objekten zu den jeweiligen File Objekten. Zuordnung des File/Media Meta Paares
	 * zu den IMedialist Objekten von Settings und den darin enthaltenen HashMaps
	 * entsprechend ihres Dateityps.
	 * @param Settings Globale Einstellungen, mit Dateipfad und IMedialistobjekten für alle Filme und Audios
	 * @return Array mit jeweils einem Audiolist, Booklist und Movielist Objekt 
	 * des Typen IMedialist
	 */
	public static IMedialist[] mediaScan(File file) {
		
		//HashMaps für Medien werden erzeugt
		IMedialist movies = ListFactory.getInstance("video", "scannedMovies");
		log.info("Liste für Videodateien erstellt");

		IMedialist audio = ListFactory.getInstance("audio", "scannedMovies");
		log.info("Liste für Musikdateien erstellt");
/*
		IMedialist books = ListFactory.getInstance("books", "scannedMovies");
		log.info("Liste für Textdateien erstellt");
*/	
		// Array mit File Objekten zu allen Medien Dateien, unabhängig vom Typ
		ArrayList<File> files = ScanDirectoryRecursive.createFileList(file);

		// Aus jeder Datei des Arrays wird ein Objekt erstellt und der richtigen HashMap zugeordnet
		for (int i = 0; i < files.size(); i++) {
			// Prüft ob Datei für Mediathek kompatibel ist
			if (!files.get(i).getName().toLowerCase().matches("^.*\\.(avi|mp4|wmv|mdk|mkv|mpeg|mpg)$")
					&& !files.get(i).getName().toLowerCase().matches("^.*\\.(mp3|wav|wma|aac|ogg)$")) {
				continue;
			}
			String typ = null;
			MediaMeta meta = readMetaData(files.get(i));
			log.info("Metadaten von " + files.get(i) + " werden gelesen");
			JSONObject root = new JSONObject();			
			boolean favo = false;
			boolean visible = true;
			String ranking = "0";
			
			try {
				root = (JSONObject) new JSONParser().parse(meta.getDescription());
			} catch (Exception e) {
				log.catching(e);
				log.error("Fehler beim Einlesen der erweiterten Metadaten von " + files.get(i));
				log.info("Default gesetzt (favo = false, visible = true, ranking = 0)");
			}
			
			try {
				if (root.get("favorite").equals(true)) {
					favo = true;
				} else {
					favo = false;
				}
			} catch (NullPointerException e) {
				log.error("Fehler beim Lesen des Metaattributs favorite von " + files.get(i));
				log.info("Default gesetzt (favo = false)");
			}
			
			try {
				if (root.get("visible").equals(true)) {
					visible = true;
				} else {
					visible = false;
				}
			} catch (NullPointerException e) {
				log.error("Fehler beim Lesen des Metaattributs visible von " + files.get(i));
				log.info("Default gesetzt (visible = true)");
			}
			
			try {
				ranking = root.get("ranking").toString();
			} catch (NullPointerException e) {
				log.error("Fehler beim Lesen des Metaattributs ranking von " + files.get(i));
				log.info("Default gesetzt (ranking = 0)");
			}
			
			if (files.get(i).getName().toLowerCase().matches("^.*\\.(avi|mp4|wmv|mdk|mkv|mpeg|mpg)$")) {
				typ = "video";
				IMedia temp = MediaFactory.getInstance(
						typ, meta.getTitle(), files.get(i), meta.getLength(),meta.getDate(),
						meta.getArtist(),
						meta.getGenre(),
						(String)root.get("infos"),
						favo,
						visible,
						ranking);
				movies.getContent().put(files.get(i), temp);

			} else if (files.get(i).getName().toLowerCase().matches("^.*\\.(mp3||wav|wma|aac|ogg)$")) {
				typ = "audio";
				IMedia temp = MediaFactory.getInstance(
						typ, meta.getTitle(), files.get(i), meta.getLength(), meta.getDate(),
						meta.getArtist(), 
						meta.getGenre(), 
						(String)root.get("infos"), 
						favo,
						visible,
						ranking);
				audio.getContent().put(files.get(i), temp);
			} /*else if (scannedMedia[i].getName().toLowerCase().matches("^.*\\.(doc|docx|pdf|html|txt)$")) {
				typ = "book";
				IMedia temp = MediaFactory.getInstance(typ, meta.getTitle(), false, scannedMedia[i], size, true);
				books.getContent().put(files.get(i), temp);
			}*/ else {
				log.info("Dateityp nicht unterstützt. " + files.get(i) + " wurde nicht eingelesen.");
			}
			meta.release();
		}
		
		//Die drei Maps werden in ein Array geschrieben und zrückgegeben
		IMedialist[] allMedia = {movies,audio};
		return allMedia;
	}
	
	/**
	 * Methode zum Löschen von Mediendateien aus dem Dateipfad der Mediathek.
	 * 
	 * @param s Objekt vom Typ 'Settings'
	 * @param scan Objekt vom Typ 'Scanner', enthält Referenz zum Pfad der Mediendateien
	 * @param movies Objekt vom Typ 'IMedialist', enhält Map mit Movie Objekten
	 * @param audio Objekt vom Typ 'IMedialist', enhält Map mit Audio Objekten
	 */
	public static void deleteMedia(Settings s, Scanner scan, IMedialist movies, IMedialist audio) {

		IMedia m = App.getInput(s, scan, movies, audio);		
		System.out.println("Möchtest du das Medium " + m.getTitle() +  " von der Festplatte löschen? (Ja/Nein)\n");

		boolean validInput = false;
		while (validInput == false) {
			String input = scan.nextLine();
			if (input.equals("Ja") || input.equals("ja")) {
				validInput = true;
				m.getFile().delete();
				log.info("Das Medium " + m.getTitle() + " wurde von der Festplatte gelöscht");
				m.removeMedia();
			} else if (input.equals("Nein") || input.equals("nein")) {
				validInput = true;
				m.removeMedia();
				log.info("Das Medium " + m.getTitle() + " wird nicht mehr in der Mediathek angezeigt");
			} else {
				System.out.println("ungültige Eingabe - entweder für 'Ja' / 'ja' oder 'Nein' / 'nein' entscheiden");
			}
		}
	}

	/**
	 * Methode zum Editieren von Metainformationen der Mediendateien. Metainformationen werden sowohl in den Feldern des IMedia 
	 * Objektes, als auch in den Mediendateien selbst gespeichert.
	 * 
	 * @param m Objekt vom Typ 'IMedia'
	 * @param s Objekt vom Typ 'Scanner'
	 * @throws ParseException Wird von JsonParser.parse() geworfen, wenn von der Methode MediaMeta.getDescription() ein unerwarteter
	 * String zurückgegeben wird. 
	 */
	public static void editMetaInformation(IMedia m, Scanner s) throws ParseException {

		MediaMeta meta = readMetaData(m.getFile());
		log.info("Metadaten von " + m.getFile() + " werden bearbeitet");

		if (m.getTyp().equals("video"))	{
			do {
				System.out.println("Wie lautet der Titel des Films?");
				meta.setTitle(s.nextLine());
			} while (meta.getTitle().isEmpty() || meta.getTitle().matches("^\\s*$"));
			System.out.println("Wann wurde der Film veröffentlicht?");
			meta.setDate(s.nextLine());
			System.out.println("Wer ist Regisseur des Films?");
			meta.setArtist(s.nextLine());
			System.out.println("Welchem Genre gehört der Film an?");
			meta.setGenre(s.nextLine());
			System.out.println("Weitere Infos zum Film?");
			String infos = s.nextLine();
			String ranking = rankingInput(s, m.getTyp());
			boolean favorite = false;
			boolean valid = false;
			while (valid == false) {
				System.out.println("Film zu Favoriten hinzufügen? (0: nein, 1: ja)");
				s.nextLine();
				String input = s.nextLine();
				if (input.equals("0")) {
					favorite = false;
					valid = true;
				} else if (input.equals("1")) {
					favorite = true;
					valid = true;
				} else {
					log.debug("Ungültige Eingabe");
				}
			}
			HashMap<String, Object> root = new HashMap<>();
			root.put("infos", infos);
			root.put("ranking", ranking);
			root.put("favorite", favorite);
			root.put("visible", true);
			JSONObject metaInfos = new JSONObject(root);
			meta.setDescription(metaInfos.toString());
		}
		if (m.getTyp().equals("audio"))	{
			do {
				System.out.println("Wie lautet der Titel der Audiodatei?");
				meta.setTitle(s.nextLine());
			} while (meta.getTitle().isEmpty() || meta.getTitle().matches("^\\s*$"));
			System.out.println("Wann wurde das Audio veröffentlicht?");
			meta.setDate(s.nextLine());
			System.out.println("Wer ist der Verfasser der Audios?");
			meta.setArtist(s.nextLine());
			System.out.println("Welchem Genre gehört das Audio an?");
			meta.setGenre(s.nextLine());
			System.out.println("Weitere Infos zum Audio?");
			String infos = s.nextLine();
			String ranking = rankingInput(s, m.getTyp());
			boolean favorite = false;
			boolean valid = false;
			while (valid == false) {
				System.out.println("Audio zu Favoriten hinzufügen? (0: nein, 1: ja)");
				s.nextLine();
				String input = s.nextLine();
				if (input.equals("0")) {
					favorite = false;
					valid = true;
				} else if (input.equals("1")) {
					favorite = true;
					valid = true;
				} else {
					log.debug("Ungültige Eingabe");
				}
			}
			HashMap<String, Object> root = new HashMap<>();
			root.put("infos", infos);
			root.put("ranking", ranking);
			root.put("favorite", favorite);
			root.put("visible", true);
			JSONObject metaInfos = new JSONObject(root);
			meta.setDescription(metaInfos.toString());
		}
		System.out.println("Folgende Eingaben speichern? (Ja/Nein)");
		System.out.println("Titel: " + meta.getTitle());
		System.out.println("Erscheinungsdatum: " + meta.getDate());
		System.out.println("Regisseur/Verfasser: " + meta.getArtist());
		System.out.println("Genre: " + meta.getGenre());
		JSONObject root = (JSONObject) new JSONParser().parse(meta.getDescription()); // hier wird die Exception geworfen
		System.out.println("Infos: " + root.get("infos"));
		System.out.println("Bewertung: " + root.get("ranking") + "/5");
		System.out.println("Favorit? " + root.get("favorite"));

		boolean validInput = false;
		while (validInput == false) {
			String input = s.nextLine();
			if (input.equals("Ja") || input.equals("ja")) {
				validInput = true;
				log.info("Metadaten für " + m.getFile() + " werden gespeichert");
				// Metainformationen werden in Datei gespeichert
				meta.save();

				// Metainformationen werden in Attribute des IMedia Objekts geschrieben
				m.setTitle(meta.getTitle());
				m.setDate(meta.getDate());
				m.setRegisseur(meta.getArtist());
				m.setGenre(meta.getGenre());
				m.setInfo((String) root.get("infos"));
				m.setRanking(Integer.parseInt((String)root.get("ranking")));
				m.setFavorite((Boolean)root.get("favorite"));
				meta.release();
				log.info("Änderungen erfolgreich gespeichert.");
			} else if (input.equals("Nein") || input.equals("nein")) {
				validInput = true;
				log.info("Änderungen werden verworfen.");
				meta.release();
			} else {
				System.out.println("ungültige Eingabe - entweder für 'Ja' / 'ja' oder 'Nein' / 'nein' entscheiden");
			}
		}
	}

	private static String rankingInput(Scanner s, String typ) {
		do {
			if (typ.equals("video")) {
				System.out.println("Welche Bewertung bekommt der Film? (1 bis 5)\n"
						+ "(1 = sehr schlecht, 5 = sehr gut)");
			} else if (typ.equals("audio")) {
				System.out.println("Welche Bewertung bekommt das Audio? (1 bis 5)\n"
						+ "(1 = sehr schlecht, 5 = sehr gut)");
			} else {
				System.out.println("Fehler, Bewertung auf 0 gesetzt");
				log.error("Kein gültiger Typ übergeben.");
				return "0";
			}

			int input = 0;
			try {
				input = s.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Ungültige Eingabe. Bewertung erfolgt in Nummern von 1-5");
				s.nextLine();
				continue;
			}
			if (input > 0 && input <= 5) {
				return "" + input;
			} else {
				System.out.println("Ungültige Eingabe. Bewertung erfolgt in Nummern von 1-5");
			}
		} while (true);
	}

	public static void savePlaylists(ArrayList<IMedialist> allLists) {
		// playlists.json muss erstellt/überschrieben werden
		// Schleife läuft durch Liste, die alle Playlists enthält
		try (PrintWriter writer = new PrintWriter(new File ("playlists.json"))) {
			for (IMedialist i : allLists) {
				if (!i.getName().equals("Favoriten (Video)") && !i.getName().equals("Favoriten (Audio)")) {
					HashMap<String, Object> obj = new HashMap<>();
					obj.put("name", i.getName());
					obj.put("type", i.getType());
					ArrayList<String> list = new ArrayList<>();
					for (File f : i.getContent().keySet()) {
						list.add(f.toString());
						// Alle Inhalte (Key-Value-Paare) der jew. Playlist müssen gespeichert werden
					}
					obj.put("content", list);
					JSONObject root = new JSONObject(obj);
					/*for (Entry<File, IMedia> o : i.getContent().entrySet()) {
					o.getKey();
					o.getValue();
					}*/
					// Überlegen, wie einzelne Playlists voneinander getrennt werden
					writer.println(root.toJSONString());
					log.info("Playlists erfolgreich in JSON gespeichert");
					log.info("Playlist " + i.getName() + " mit allen Inhalten erfolgreich in playlists.json geschrieben");
				}
			}	
		} catch (FileNotFoundException e) {
			log.info("Speichern der Playlists in JSON fehlgeschlagen");
			log.catching(e);;
			e.printStackTrace();
		}
	}

	public static ArrayList<IMedialist> loadPlaylists(IMedialist movies, IMedialist audio) {
		ArrayList<IMedialist> allLists = new ArrayList<>();
		try (Scanner input = new Scanner(new File("playlists.json"))) {
			while (input.hasNextLine()) {
				String jsonInput = input.nextLine();
				JSONObject root = (JSONObject) new JSONParser().parse(jsonInput.toString());
				@SuppressWarnings("unchecked")
				ArrayList<String> content = (ArrayList<String>) root.get("content");
				IMedialist m = ListFactory.getInstance((String)root.get("type"), (String)root.get("name"));
				if (((String)root.get("type")).equals("video")) {
					for (String f : content) {
						for  (Entry<File, IMedia> i : movies.getContent().entrySet()) {
							if (i.getKey().equals(new File(f))) {
								m.addMedia(i.getValue());
							}
						}
					}
				} else {
					for (String f : content) {
						for  (Entry<File, IMedia> i : audio.getContent().entrySet()) {
							if (i.getKey().equals(new File(f))) {
								m.addMedia(i.getValue());
							}
						}
					}
				}
				allLists.add(m);
			}
		} catch (FileNotFoundException e) {
			log.info("Keine Datei mit gespeicherten Playlists gefunden.");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return allLists;
	}

	public static void playMovie(Settings s, Scanner scan, IMedialist movies, IMedialist audio) {
		System.out.println("Welcher Film soll gespielt werden?");
		App.getInput(s, scan, movies, audio).open();
	}
}
