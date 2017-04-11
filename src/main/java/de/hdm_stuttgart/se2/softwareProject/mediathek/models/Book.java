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
	
	
	
	
	public Book(String title, boolean favorite, File file/*, int pages*/) {
		super(title, favorite, file);
		//this.pages = pages;
	}

	@Override
	public void getDetails() {
		System.out.println(this.title);
		System.out.println("Seitenzahl: " + this.pages);
		System.out.println("Erscheinungsdatum: " + this.releaseDate);
		System.out.println("Autor: " + this.author);
		System.out.println("Genre: " + this.genre);
		System.out.println("Klappentext: " + this.info);
		System.out.println("Bewertung: " + this.ranking);
	}
}
