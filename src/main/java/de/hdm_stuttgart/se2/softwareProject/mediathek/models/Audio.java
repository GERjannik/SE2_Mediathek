package de.hdm_stuttgart.se2.softwareProject.mediathek.models;

import java.io.File;
import java.util.Date;

import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;

class Audio extends Media implements IMedia {

	private int duration;
	private String interpret;
	private String genre;
	private int ranking;
	private Date releaseDate;
	
	
	public Audio(int id, String title, boolean favorite, File file/*, int duration*/) {
		super(id, title, favorite, file);
		//this.duration = duration;
	}


	@Override
	public void getDetails() {
		System.out.println(this.title);
		System.out.println("Länge: " + this.duration);
		System.out.println("Erscheinungsdatum: " + this.releaseDate);
		System.out.println("Interpret: " + this.interpret);
		System.out.println("Genre: " + this.genre);
		System.out.println("Bewertung: " + this.ranking);
	}
}
