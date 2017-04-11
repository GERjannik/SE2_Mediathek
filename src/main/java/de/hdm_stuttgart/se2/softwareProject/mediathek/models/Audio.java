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


	public Audio(String title, boolean favorite, File file/*, int duration*/, boolean visible) {
		super(title, favorite, file, visible);
		//this.duration = duration;
	}


	@Override
	public void getDetails() {
		if (this.visible == true) {
			System.out.println(this.title);
			if (this.duration != 0) {
				System.out.println("Länge: " + this.duration);
			}
			if (this.releaseDate != null && !(this.releaseDate.equals(""))) {
				System.out.println("Erscheinungsdatum: " + this.releaseDate);
			}
			if (this.interpret != null && !(this.interpret.equals(""))) {
				System.out.println("Interpret: " + this.interpret);
			}
			if (this.genre != null && !(this.genre.equals(""))) {
				System.out.println("Genre: " + this.genre);
			}
			System.out.println("Bewertung: " + this.ranking);
		}
	}
}
