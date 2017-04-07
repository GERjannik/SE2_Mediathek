package de.hdm_stuttgart.se2.softwareProject.mediathek.models;

import java.io.File;
import java.util.Date;
import java.util.List;

import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;

class Movie implements IMedia {

	private int id;
	private String title;
	private List<String> actor;
	private int duration;
	private Date releaseDate;
	private String regisseur;
	private boolean favorite;
	private String genre;
	private String info;
	private int ranking;
	private File file;
	
	/**
	 * @param title
	 * @param duration
	 * @param favorite
	 * @param inPlaylists
	 * @param file
	 */
	/*public Movie(String title,  int duration , boolean favorite, File file) {
		// super(); automatisch generiert
		this.title = title;
		//this.duration = duration;
		this.favorite = favorite;
		this.file = file;
	} */

	public Movie(String title, boolean favorite, File file) {
		this.title = title;
		this.favorite = favorite;
		this.file = file;	
		}

	/* (non-Javadoc)
	 * @see de.hdm_stuttgart.se2.softwareProject.mediathek.models.IMedia#showDetails()
	 */
	@Override
	public void showDetails() {
		System.out.println(this.title + "\nSchauspieler:");
		// Schleife, damit alle Schauspieler, die die Liste enthält, ausgegeben werden
		for (String i : this.actor) {
			System.out.println(i);
		}
		System.out.println("Dauer: " + this.duration);
		System.out.println("Erscheinungsdatum: " + this.releaseDate);
		System.out.println("Regisseur: " + this.regisseur);
		System.out.println("Genre: " + this.genre);
		System.out.println("Filmbeschreibung: " + this.info);
		System.out.println("Bewertung: " + this.ranking);
	}	
	
	
	
}
