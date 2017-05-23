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
	 * Erstellen von zunächst leeren Audiolist, Booklist und Movielist Objekten des
	 * Typs IMedialist. Genererieren einer ArrayList mit File Objekten sämtlicher Dateien
	 * des übergebenen Pfades. Durchitererieren der ArrayList. Generierung von von MediaMeta
	 * Objekten zu den jeweiligen File Objekten. Zuordnung des File/Media Meta Paares
	 * zu den eingangs generierten IMedialist Objekten und den darin enthaltenen HashMaps
	 * entsprechend ihres Dateityps.
	 * @param File Objekt des Pfades mit den enthaltenen Medien Dateien
	 * @return Array mit jeweils einem Audiolist, Booklist und Movielist Objekt 
	 * des Typen IMedialist
	 */
	public static IMedialist[] mediaScan(File file) {

		// HashMaps für Medien werden erzeugt
		IMedialist movies = ListFactory.getInstance("video", "scannedMovies");
		log.info("Liste für Videodateien erstellt");
		IMedialist audio = ListFactory.getInstance("audio", "scannedAudio");
		log.info("Liste für Audiodateien erstellt");
		IMedialist books = ListFactory.getInstance("book", "scannedBooks");
		log.info("Liste für Textdateien erstellt");

		// Array mit File Objekten zu allen Medien Dateien, unabhängig vom Typ
		ArrayList<File> files = ScanDirectoryRecursive.createFileList(file);

		// Aus jeder Datei des Arrays wird ein Objekt erstellt und der richtigen HashMap zugeordnet
		for (int i = 0; i < files.size(); i++) {
			String typ = null;
			MediaMeta meta = readMetaData(files.get(i));
			log.info("Metadaten von " + files.get(i) + " werden gelesen");

			if (files.get(i).getName().toLowerCase().matches("^.*\\.(avi|mp4|wmv|mdk|mkv|mpeg|mpg)$")) {
				typ = "video";
				IMedia temp = MediaFactory.getInstance(typ, meta.getTitle(), false, files.get(i),
						true, meta.getLength(), meta.getDate(), meta.getArtist(), meta.getGenre(), meta.getDescription());
				movies.getContent().put(files.get(i), temp);

			} else if (files.get(i).getName().toLowerCase().matches("^.*\\.(mp3||wav|wma|aac|ogg)$")) {
				typ = "audio";
				IMedia temp = MediaFactory.getInstance(typ, meta.getTitle(), false, files.get(i),
						true, meta.getLength(), meta.getDate(), meta.getArtist(), meta.getGenre(), meta.getDescription());
				audio.getContent().put(files.get(i), temp);
			} /*else if (scannedMedia[i].getName().toLowerCase().matches("^.*\\.(doc|docx|pdf|html|txt)$")) {
				typ = "book";
				IMedia temp = MediaFactory.getInstance(typ, meta.getTitle(), false, scannedMedia[i], size, true);
				books.getContent().put(scannedMedia[i], temp);
			}*/ else {
				log.info("Dateityp nicht unterstützt. " + files.get(i) + " wurde nicht eingelesen.");
			}
			meta.release();
		}
		// Die drei Maps werden in ein Array geschrieben und zurückgegeben
		IMedialist[] allMedia = {movies, audio, books};
		return allMedia;
	}

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

	public static void editMetaInformation(IMedia m, Scanner s) {

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
			meta.setDescription(s.nextLine());
			meta.setRating(rankingInput(s, m.getTyp()));
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
			meta.setDescription(s.nextLine());
			meta.setRating(rankingInput(s, m.getTyp()));
		}
		System.out.println("Folgende Eingaben speichern? (Ja/Nein)");
		System.out.println("Titel: " + meta.getTitle());
		System.out.println("Erscheinungsdatum: " + meta.getDate());
		System.out.println("Regisseur/Verfasser: " + meta.getArtist());
		System.out.println("Genre: " + meta.getGenre());
		System.out.println("Infos: " + meta.getDescription());
		System.out.println("Bewertung: " + meta.getRating() + "/5");

		boolean validInput = false;
		while (validInput == false) {
			s.nextLine();
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
				m.setInfo(meta.getDescription());
				m.setRanking(meta.getRating());
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
		// TODO: playlists.json muss erstellt/überschrieben werden
		// Schleife läuft durch Liste, die alle Playlists enthält
		try (PrintWriter writer = new PrintWriter(new File ("playlists.json"))) {
			for (IMedialist i : allLists) {
				HashMap<String, Object> obj = new HashMap<>();
				obj.put("name", i.getName());
				obj.put("type", i.getType());
				ArrayList<String> list = new ArrayList<>();
				for (File f : i.getContent().keySet()) {
					list.add(f.toString());
					// TODO: Alle Inhalte (Key-Value-Paare) der jew. Playlist müssen gespeichert werden
				}
				obj.put("content", list);
				JSONObject root = new JSONObject(obj);
				/*for (Entry<File, IMedia> o : i.getContent().entrySet()) {
				o.getKey();
				o.getValue();
			}*/
				// TODO: Überlegen, wie einzelne Playlists voneinander getrennt werden
				writer.println(root.toJSONString());
				log.info("Playlists erfolgreich in JSON gespeichert");
				log.info("Playlist " + i.getName() + " mit allen Inhalten erfolgreich in playlists.json geschrieben");
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
				IMedialist m = ListFactory.getInstance((String)root.get("type"), (String)root.get("name"));
				if (((String)root.get("type")).equals("video")) {
					for (String f : (ArrayList<String>)root.get("content")) {
						for  (Entry<File, IMedia> i : movies.getContent().entrySet()) {
							if (i.getKey().equals(new File(f))) {
								m.addMedia(i.getValue());
							}
						}
					}
				} else {
					for (String f : (ArrayList<String>)root.get("content")) {
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
