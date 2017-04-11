package de.hdm_stuttgart.se2.softwareProject.mediathek.models;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;

class Movie extends Media implements IMedia {

	private List<String> actor;
	private int duration;
	private Date releaseDate;
	private String regisseur;
	private String genre;
	private String info;
	private int ranking;
	
	public Movie(int id, String title, boolean favorite, File file/*, int duration*/) {
		super(id, title, favorite, file);
		//this.duration = duration;
	}	


	/* (non-Javadoc)
	 * @see de.hdm_stuttgart.se2.softwareProject.mediathek.models.IMedia#showDetails()
	 */
	@Override
	public void getDetails() {
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
