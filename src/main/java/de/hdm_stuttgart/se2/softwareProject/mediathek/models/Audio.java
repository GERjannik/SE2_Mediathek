package de.hdm_stuttgart.se2.softwareProject.mediathek.models;

import java.io.File;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;

class Audio extends Media implements IMedia {

	private long duration;
	private String interpret;
	private String genre;
	private String info;
	private String ranking;
	private String releaseDate;

	public Audio(String typ, String title, boolean favorite, File file, boolean visible, long duration, String interpret,
			String genre, String releaseDate, String info) {
		super(typ, title, favorite, file, visible);
		this.duration = duration;
		this.interpret = interpret;
		this.genre = genre;
		this.releaseDate = releaseDate;
		this.info = info;
	}


	@Override
	public void getDetails() {
		if (this.visible == true) {
			System.out.println(this.title);
			if (this.duration != 0) {
				System.out.format("Dauer: %d:%02d Minuten\n", this.duration / 60000, (this.duration % 60000) / 1000 );
			}
			if (this.releaseDate != null && !(this.releaseDate.equals("")) && !(this.releaseDate.equals("0"))) {
				System.out.println("Erscheinungsdatum: " + this.releaseDate);
			}
			if (this.interpret != null && !(this.interpret.equals(""))) {
				System.out.println("Interpret: " + this.interpret);
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
		this.interpret = regisseur;
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
		// TODO Auto-generated method stub
		
	}
}
