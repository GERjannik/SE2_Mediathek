package de.hdm_stuttgart.se2.softwareProject.mediathek.gui;

import java.io.File;
import java.util.Map.Entry;

import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.Settings;
import de.hdm_stuttgart.se2.softwareProject.mediathek.exceptions.InvalidInputException;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedialist;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Hilfsklasse für MOController
 * Auslagerung aller extra Methoden und Klassen
 *
 */
public class Media {

	private static Media instance;

	private final SimpleStringProperty title;
	private final SimpleLongProperty length;
	private final SimpleStringProperty date;
	private final SimpleStringProperty artist;
	private final SimpleStringProperty genre; 

	public Media(String title, Long length, String date, String artist, String genre) {
		this.title = new SimpleStringProperty(title);
		this.length = new SimpleLongProperty(length);
		this.date = new SimpleStringProperty(date);
		this.artist = new SimpleStringProperty(artist);
		this.genre = new SimpleStringProperty(genre);
	}

	public String getTitle() {
		return title.get();
	}

	public long getLength() {
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


	public static Media getInstance() {
		return instance;
	}


	public static void setInstance(Media instance) {
		Media.instance = instance;
	}


	public static void playMovie(Settings s, String play_data, IMedialist movies, IMedialist audio) {

		new Thread(new Runnable() {
			public void run() {

				getInput(s, play_data, movies, audio).open();

			}
		}).start();

	}
	

	public static IMedia getInput(Settings s, String play_data, IMedialist movies, IMedialist audio) {

		for (Entry<File, IMedia> i : movies.getContent().entrySet()) {
			if (i.getValue().getTitle().equals((play_data))) {
				return i.getValue();
			}
		}
		for (Entry<File, IMedia> i : audio.getContent().entrySet()) {
			if (i.getValue().getTitle().equals(play_data)) {
				return i.getValue();
			}
		}
		throw new InvalidInputException();
	}
}
