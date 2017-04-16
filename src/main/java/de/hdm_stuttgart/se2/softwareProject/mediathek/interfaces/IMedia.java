package de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces;

import java.io.File;

public interface IMedia {

	void getDetails();
	void openFile();
	void removeMedia();
	String getTitle();
	File getFile();
	String getTyp();
	void setTitle(String title);
	void setFavorite(boolean favorite);

}