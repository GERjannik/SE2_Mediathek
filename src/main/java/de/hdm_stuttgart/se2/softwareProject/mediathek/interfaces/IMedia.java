package de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces;

import java.io.File;

public interface IMedia {

	void getDetails();
	void removeMedia();
	String getTitle();
	File getFile();
	String getTyp();
	void setTitle(String title);
	void setFavorite(boolean favorite);
	void setDate(String date);
	void setRegisseur(String regisseur);
	void setGenre(String genre);
	void setInfo(String info);
	void setRanking(String ranking);
	void open();

}