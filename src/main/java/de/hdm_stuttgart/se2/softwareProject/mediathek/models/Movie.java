package de.hdm_stuttgart.se2.softwareProject.mediathek.models;

import java.io.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;

/**
 * Implementiert das IMedia Interface. Erweiterung um medienspezifische
 * Attribute: actor, duration (Typ an Medium angepasst), releaseDate, regisseur,
 * info und ranking. Die spezifischen Attribute werden anhand des Constructors
 * gesetzt.
 * 
 * @author ll040
 *
 */
class Movie extends Media implements IMedia {

	private static Logger log = LogManager.getLogger(Movie.class);

	private String releaseDate;
	private String genre;
	private String info;
	private int ranking;

	/**
	 * 
	 * @param typ
	 *            Typ des Mediums
	 * @param title
	 *            Titel des Films
	 * @param favorite
	 *            Toggle für Favoriten-Liste
	 * @param file
	 *            Pfad der Film-Datei
	 * @param visible
	 *            Visibilität für die getDetails()-Methode
	 * @param length
	 *            Dauer
	 * @param releaseDate
	 *            Erscheinungsdatum
	 * @param artist
	 *            Regisseur
	 * @param genre
	 *            Film-Genre
	 * @param info
	 *            benutzerspezifische Informationen
	 */
//	public Movie(String typ, String title, File file, long duration, String releaseDate, String regisseur, String genre,
//			String info, boolean favorite, boolean visible, String ranking) {
//		super(typ, title, favorite, file, visible);
//		this.duration = duration;
//		this.releaseDate = releaseDate;
//		this.regisseur = regisseur;
//		this.genre = genre;
//		this.info = info;
//		try {
//			int rankInt = Integer.parseInt(ranking);
//			if (rankInt > 0 && rankInt < 6) {
//				this.ranking = rankInt;
//			} else {
//				this.ranking = 1;
//			}
//		} catch (NumberFormatException e) {
//			log.catching(e);
//			log.error("Ungültige Bewertung in Metadaten");
//			this.ranking = 1;
//		}
//	}

	public Movie(File file) {
		super(file);
		this.length = super.metaData.getLength();
		this.artist = super.metaData.getArtist(); // TODO testen ob richtige Methode getArtist()
		this.genre = super.metaData.getGenre();
		this.releaseDate = super.metaData.getDate();		
	}

	/**
	 * Auslesen der Metadaten, sofern visible == true
	 */
	@Override
	public void getDetails() {
		if (this.visible == true) {
			System.out.println(this.title);

			if (this.length != 0) {
				if (this.length / 60000 != 0) {
					System.out.format("Dauer: %d Minuten\n", this.length / 60000);
				} else {
					System.out.format("Dauer: %d Sekunden\n", this.length / 1000);
				}
			}
			if (this.releaseDate != null && !(this.releaseDate.equals("")) && !(this.releaseDate.equals("0"))) {
				System.out.println("Erscheinungsdatum: " + this.releaseDate);
			}
			if (this.artist != null && !(this.artist.equals(""))) {
				System.out.println("Regisseur: " + this.artist);
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
