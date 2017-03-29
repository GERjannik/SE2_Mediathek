package de.hdm_stuttgart.se2.softwareProject.mediathek.Models;

import java.io.File;
import java.util.Date;
import java.util.List;

public class Movie {

	private String title;
	private List<String> actor;
	private int duration;
	private Date releaseDate;
	private String regisseur;
	private boolean favorite;
	private String genre;
	private String info;
	private int ranking;
	private List<Integer> inPlaylists;
	private File file;
	
	private void showDetails(Movie m) {
		System.out.println(m.title + "\nSchauspieler:");
		// Schleife, damit alle Schauspieler, die die Liste enthält, ausgegeben werden
		for (String i : m.actor) {
			System.out.println(i);
		}
		System.out.println("Dauer: " + m.duration);
		System.out.println("Erscheinungsdatum: " + m.releaseDate);
		System.out.println("Regisseur: " + m.regisseur);
		System.out.println("Genre: " + m.genre);
		System.out.println("Filmbeschreibung: " + m.info);
		System.out.println("Bewertung: " + m.ranking);
	}	
	
	
	
}
