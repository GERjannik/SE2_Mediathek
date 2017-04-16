package de.hdm_stuttgart.se2.softwareProject.mediathek.models;

import java.io.File;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;

class Audio extends Media implements IMedia {

	private long duration;
	private String interpret;
	private String genre;
	private int ranking;
	private String releaseDate;

	public Audio(String typ, String title, boolean favorite, File file, boolean visible, long duration, String interpret,
			String genre, String releaseDate) {
		super(typ, title, favorite, file, visible);
		this.duration = duration;
		this.interpret = interpret;
		this.genre = genre;
		this.releaseDate = releaseDate;
	}


	@Override
	public void getDetails() {
		if (this.visible == true) {
			System.out.println(this.title);
			if (this.duration != 0) {
				System.out.format("Dauer: %d:%02d Minuten\n", this.duration / 60000, (this.duration % 60000) / 1000 );
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
