package de.hdm_stuttgart.se2.softwareProject.mediathek.models;

import java.io.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;

/**
 * Implementiert das IMedia Interface. Erweiterung um medienspezifische
 * Attribute: actor, duration (Typ an Medium angepasst), releaseDate, regisseur, info
 * und ranking. Die spezifischen Attribute werden anhand des Constructors 
 * gesetzt.
 * 
 * @author ll040
 *
 */
class Movie extends Media implements IMedia {
	
	private static Logger log = LogManager.getLogger(Movie.class);
	
	private long duration;
	private String releaseDate;
	private String regisseur;
	private String genre;
	private String info;
	private int ranking;

	/**
	 * 
	 * @param typ			Typ des Mediums
	 * @param title			Titel des Films
	 * @param favorite		Toggle für Favoriten-Liste
	 * @param file			Pfad der Film-Datei
	 * @param visible		Visibilität für die getDetails()-Methode
	 * @param duration		Dauer
	 * @param releaseDate	Erscheinungsdatum
	 * @param regisseur		Regisseur
	 * @param genre			Film-Genre
	 * @param info			benutzerspezifische Informationen
	 */
	public Movie(String typ, String title, File file, long duration,
			String releaseDate, String regisseur, String genre, String info, boolean favorite, boolean visible, String ranking) {
		super(typ, title, favorite, file, visible);
		this.duration = duration;
		this.releaseDate = releaseDate;
		this.regisseur = regisseur;
		this.genre = genre;
		this.info = info;
		try {
			int rankInt = Integer.parseInt(ranking);
			if (rankInt > 0 && rankInt < 6) {
				this.ranking = rankInt;
			} else {
				this.ranking = 1;
			}
		} catch (NumberFormatException e) {
			log.catching(e);
			log.error("Ungültige Bewertung in Metadaten");
			this.ranking = 1;
		}
	}

	/**
	 * Auslesen der Metadaten, sofern visible == true
	 */
	@Override
	public void getDetails() {
		if (this.visible == true) {
			System.out.println(this.title);

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
				System.out.println("Bewertung: " + this.ranking + "/5");
			}
		} else {
			log.debug("Metadaten des Mediums " + this.getFile() + " nicht ausgegeben, da Attribut visible == false");
		}
	}
	@Override
	public void setDate(String date) {
		this.releaseDate = date;
		log.debug("Erscheinungsdatum des Mediums " + this.getFile() + " auf " + this.releaseDate + " geändert");
	}

	@Override
	public void setRegisseur(String regisseur) {
		this.regisseur = regisseur;
		log.debug("Regisseur des Mediums " + this.getFile() + " auf " + this.regisseur + " geändert");
	}

	@Override
	public void setGenre(String genre) {
		this.genre = genre;
		log.debug("Genre des Mediums " + this.getFile() + " auf " + this.genre + " geändert");
	}

	@Override
	public void setInfo(String info) {
		this.info = info;
		log.debug("Beschreibung des Mediums " + this.getFile() + " auf geändert");
	}

	@Override
	public void setRanking(int ranking) {
		this.ranking = ranking;
		log.debug("Bewertung des Mediums " + this.getFile() + " auf " + this.ranking + " geändert");
	}
	
	@Override
	public boolean getFavorite() {
		return this.favorite;
	}
}
