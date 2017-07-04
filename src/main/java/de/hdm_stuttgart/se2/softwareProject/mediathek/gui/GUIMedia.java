package de.hdm_stuttgart.se2.softwareProject.mediathek.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.Settings;
import de.hdm_stuttgart.se2.softwareProject.mediathek.exceptions.InvalidInputException;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedialist;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import uk.co.caprica.vlcj.player.MediaMeta;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;

/**
 * Hilfsklasse für MOController
 * Auslagerung aller extra Methoden und Klassen
 *
 */
public class GUIMedia {

	private static Logger log = LogManager.getLogger(GUIMedia.class);

	private static GUIMedia instance;

	private final SimpleBooleanProperty favo;
	private final SimpleStringProperty title;
	private final SimpleStringProperty length;
	private final SimpleStringProperty date;
	private final SimpleStringProperty artist;
	private final SimpleStringProperty genre; 
	private final SimpleStringProperty file;
	private final SimpleStringProperty ranking;

	/**
	 * Setzen der Property Objekte
	 * @param favo Favoriten Toggle für die Media Objekte.
	 * @param title Title des Mediums.
	 * @param length Länge des Filmes/Liedes.
	 * @param date Release Datum.
	 * @param artist Regisseur/Interpret.
	 * @param genre Film- oder Musik-Genre.
	 * @param file File Objekt mit Pfadangabe.
	 * @param ranking Bewertung des Mediums als String zwischen 1 und 5.
	 */
	public GUIMedia(Boolean favo, String title, String length, String date, String artist, String genre, File file, String ranking) {
		this.favo = new SimpleBooleanProperty(favo);
		this.title = new SimpleStringProperty(title);
		this.length = new SimpleStringProperty(length);
		this.date = new SimpleStringProperty(date);
		this.artist = new SimpleStringProperty(artist);
		this.genre = new SimpleStringProperty(genre);
		this.file = new SimpleStringProperty(file.toString());
		this.ranking = new SimpleStringProperty(ranking);
	}

	public boolean getFavo() {
		return favo.get();
	}

	public String getTitle() {
		return title.get();
	}

	public String getLength() {
		return length.get();
	}

	public String getDate() {
		return date.get();
	}

	public String getArtist() {
		return artist.get();
	}

	public String getGenre() {
		return genre.get();
	}

	public File getFile() {
		return new File(this.file.getValue());
	}

	public String getRanking() {
		return ranking.get();
	}


	public static GUIMedia getInstance() {
		return instance;
	}


	public static void setInstance(GUIMedia instance) {
		GUIMedia.instance = instance;
	}

	/**
	 * Wiedergabe der Mediendateien.
	 * @param s Settings Objekt mit Ordnerpfad aller Mediendateien.
	 * @param play_data File Objekt mit Referenz zur Mediendatei.
	 * @param movies Liste mit Movie Objekten.
	 * @param audio Liste mit Audio Objekten.
	 */
	public static void playMovie(Settings s, File play_data, IMedialist movies, IMedialist audio) {

		new Thread(new Runnable() {
			public void run() {

				try {
					getInput(s, play_data, movies, audio).open();
				} catch (InvalidInputException e) {
					log.log(Level.ERROR, "InvalidInputException in" + Thread.currentThread().getName(), e);
					e.printStackTrace();
				}

			}
		}).start();

	}

	/**
	 * Filtern aller Mediendateien enstprechend des Übergebenen File Objektes.
	 * @param s //TODO kann raus?
	 * @param play_data File Objekt welches das gesuchte IMedia Objekt referenziert.
	 * @param movies IMedialist Objekt mit einer Liste aller Movie Objekte.
	 * @param audio IMedialist Objekt mit einer Liste aller Movie Objekte.
	 * @return IMedia Objekt, welches die Referenz zum gesuchten File Objekt enthält. 
	 * @throws InvalidInputException 
	 */
	public static IMedia getInput(Settings s, File play_data, IMedialist movies, IMedialist audio) throws InvalidInputException {
		
		// Schreibt alle Medien in eine gemeinsamme Collection
		List<IMedia> tempmedia = new ArrayList<>();
		tempmedia.addAll(movies.getContent().values());
		tempmedia.addAll(audio.getContent().values());

		// Wenn übergebener Dateiname mit einem Objekt der Mediathek übereinstimmt, wird dieses zurückgegeben
		// ansonsten kommt es zu InvalidInputException
		try {
			return tempmedia.parallelStream()
					.filter(i -> i.getFile().equals(play_data))
					.findAny()
					.get();
		} catch (NoSuchElementException e) {
			throw new InvalidInputException();
		}

	}


	public static MediaMeta readMetaData(File file) {
		MediaPlayerFactory factory = new MediaPlayerFactory();
		MediaMeta meta = factory.getMediaMeta(file.toString(), true);
		return meta;
	}

	public static void editMetaInformation(IMedia m,String save_titel,String save_year, String save_artist, String save_genre, String ranking, boolean save_favo) throws ParseException {

		MediaMeta meta = readMetaData(m.getFile());
		log.info("Metadaten von " + m.getFile() + " werden bearbeitet");


		do {
			meta.setTitle(save_titel);
		} while (meta.getTitle().isEmpty() || meta.getTitle().matches("^\\s*$"));
		meta.setDate(save_year);
		meta.setArtist(save_artist);
		meta.setGenre(save_genre);

		HashMap<String, Object> root = new HashMap<>();
		//root.put("infos", meta);
		root.put("ranking", ranking);
		root.put("favorite", save_favo);
		root.put("visible", true);
		JSONObject metaInfos = new JSONObject(root);
		meta.setDescription("");
		meta.setDescription(metaInfos.toString());

		// Metainformationen werden in Datei gespeichert
		meta.save();
		meta.release();
		log.info("Metadaten der Datei " + m.getFile() + "erfolgreich gespeichert.");
	}

	public static void deleteMedia(Settings s, File play_data, IMedialist movies, IMedialist audio, boolean delete) throws InvalidInputException {

		IMedia m = getInput(s, play_data, movies, audio);		

		if (delete == true) {
			m.getFile().delete();
			log.info("Das Medium " + m.getTitle() + " wurde von der Festplatte gelöscht");
		} else {
			m.removeMedia();
			log.info("Das Medium " + m.getTitle() + " wird nicht mehr in der Mediathek angezeigt");
		}
	}
}
