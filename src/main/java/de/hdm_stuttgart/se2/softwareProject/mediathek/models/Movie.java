package de.hdm_stuttgart.se2.softwareProject.mediathek.models;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;

import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;

class Movie extends Media implements IMedia {

	private List<String> actor;
	private long duration;
	private String releaseDate;
	private String regisseur;
	private String genre;
	private String info;
	private String ranking;


	public Movie(String typ, String title, boolean favorite, File file, boolean visible, long duration,
			String releaseDate, String regisseur, String genre, String info) {
		super(typ, title, favorite, file, visible);
		this.duration = duration;
		this.releaseDate = releaseDate;
		this.regisseur = regisseur;
		this.genre = genre;
		this.info = info;

	}


	/* (non-Javadoc)
	 * @see de.hdm_stuttgart.se2.softwareProject.mediathek.models.IMedia#showDetails()
	 */
	@Override
	public void getDetails() {
		if (this.visible == true) {
			System.out.println(this.title);

			if (this.actor != null && this.actor.size() != 0) {
				System.out.println("Schauspieler:");
				// Schleife, damit alle Schauspieler, die die Liste enthält, ausgegeben werden
				for (String i : this.actor) {
					System.out.println(i);
				}
			}
			if (this.duration != 0) {
				if (this.duration / 60000 != 0)	{
					System.out.format("Dauer: %d Minuten\n", this.duration / 60000);
				} else {
					System.out.format("Dauer: %d Sekunden\n", this.duration / 1000);
				}
			}
			if (this.releaseDate != null && !(this.releaseDate.equals("")) && !(this.releaseDate.equals("0"))) {
				System.out.println("Erscheinungsdatum: " + this.releaseDate);
			}
			if (this.regisseur != null && !(this.regisseur.equals(""))) {
				System.out.println("Regisseur: " + this.regisseur);
			}
			if (this.genre != null && !(this.genre.equals(""))) {
				System.out.println("Genre: " + this.genre);
			}
			if (this.info != null && !(this.info.equals(""))) {
				System.out.println("Filmbeschreibung: " + this.info);
			}
			if (this.info != null && !(this.info.equals(""))) {
				System.out.println("Bewertung: " + this.ranking);
			}
		}
	}
	@Override
	public void setDate(String date) {
		this.releaseDate = date;
	}

	@Override
	public void setRegisseur(String regisseur) {
		this.regisseur = regisseur;
	}

	@Override
	public void setGenre(String genre) {
		this.genre = genre;
	}

	@Override
	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public void setRanking(String ranking) {
		this.ranking = ranking;
	}


	@Override
	public void open() {
		Desktop d = Desktop.getDesktop();  
		try {
			d.open(this.file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
