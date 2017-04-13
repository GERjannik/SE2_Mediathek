package de.hdm_stuttgart.se2.softwareProject.mediathek.models;

import java.io.File;
import java.util.Date;

import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;

class Book extends Media implements IMedia {

	private int pages;
	private String author;
	private String info;
	private Date releaseDate;
	private String genre;
	private int ranking;




	public Book(String title, boolean favorite, File file/*, int pages*/, boolean visible) {
		super(title, favorite, file, visible);
		//this.pages = pages;
	}

	@Override
	public void getDetails() {
		if (this.visible == true) {
			System.out.println(this.title);
			if (this.pages != 0) {
				System.out.println("Seitenzahl: " + this.pages);
			}
			if (this.releaseDate != null && !(this.releaseDate.equals(""))) {
				System.out.println("Erscheinungsdatum: " + this.releaseDate);
			}
			if (this.author != null && !(this.author.equals(""))) {
				System.out.println("Autor: " + this.author);
			}
			if (this.genre != null && !(this.genre.equals(""))) {
				System.out.println("Genre: " + this.genre);
			}
			if (this.info != null && !(this.info.equals(""))) {
				System.out.println("Klappentext: " + this.info);
			}
			System.out.println("Bewertung: " + this.ranking);
		}
	}
}
