package de.hdm_stuttgart.se2.softwareProject.mediathek.gui;

import java.io.File;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.Settings;
import de.hdm_stuttgart.se2.softwareProject.mediathek.exceptions.InvalidInputException;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedialist;
import javafx.beans.property.SimpleLongProperty;
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

	private final SimpleStringProperty title;
	private final SimpleStringProperty length;
	private final SimpleStringProperty date;
	private final SimpleStringProperty artist;
	private final SimpleStringProperty genre; 
	private final SimpleStringProperty file;
	private final SimpleStringProperty ranking;

	public GUIMedia(String title, String length, String date, String artist, String genre, File file, String ranking) {
		this.title = new SimpleStringProperty(title);
		this.length = new SimpleStringProperty(length);
		this.date = new SimpleStringProperty(date);
		this.artist = new SimpleStringProperty(artist);
		this.genre = new SimpleStringProperty(genre);
		this.file = new SimpleStringProperty(file.toString());
		this.ranking = new SimpleStringProperty(ranking);
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


	public static void playMovie(Settings s, File play_data, IMedialist movies, IMedialist audio) {

		new Thread(new Runnable() {
			public void run() {

				getInput(s, play_data, movies, audio).open();

			}
		}).start();

	}


	public static IMedia getInput(Settings s, File play_data, IMedialist movies, IMedialist audio) {

		for (Entry<File, IMedia> i : movies.getContent().entrySet()) {
			if (i.getValue().getFile().equals((play_data))) {
				return i.getValue();
			}
		}
		for (Entry<File, IMedia> i : audio.getContent().entrySet()) {
			if (i.getValue().getFile().equals(play_data)) {
				return i.getValue();
			}
		}
		throw new InvalidInputException();
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



		//JSONObject root = (JSONObject) new JSONParser().parse(meta.getDescription());

		// Metainformationen werden in Datei gespeichert

		meta.save();


		// Metainformationen werden in Attribute des IMedia Objekts geschrieben
		//				m.setTitle(meta.getTitle());
		//				m.setDate(meta.getDate());
		//				m.setRegisseur(meta.getArtist());
		//				m.setGenre(meta.getGenre());
		//				m.setInfo((String) root.get("infos"));
		//				m.setRanking(Integer.parseInt((String)root.get("ranking")));
		//				m.setFavorite((Boolean)root.get("favorite"));
		//				meta.release();
		//				log.info("Änderungen erfolgreich gespeichert.");
		meta.release();
		log.info("Metadaten der Datei " + m.getFile() + "erfolgreich gespeichert.");
	}

	public static void deleteMedia(Settings s, File play_data, IMedialist movies, IMedialist audio, boolean delete) {

		IMedia m = getInput(s, play_data, movies, audio);		

		boolean validInput = false;
		while (validInput == false) {
			if (delete == true) {
				validInput = true;
				m.getFile().delete();
				log.info("Das Medium " + m.getTitle() + " wurde von der Festplatte gelöscht");	
			} else if (delete == false) {
				validInput = true;
				m.removeMedia();
				log.info("Das Medium " + m.getTitle() + " wird nicht mehr in der Mediathek angezeigt");
			} else {
				System.out.println("ungültige Eingabe - entweder für 'Ja' / 'ja' oder 'Nein' / 'nein' entscheiden");
			}
		}
	}

}
