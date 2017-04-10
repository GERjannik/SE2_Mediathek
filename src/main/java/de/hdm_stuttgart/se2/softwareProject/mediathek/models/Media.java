package de.hdm_stuttgart.se2.softwareProject.mediathek.models;

import java.io.File;

abstract class Media {
	
	protected int id;
	protected String title;
	protected boolean favorite;
	protected File file;
	
	public Media(int id, String title, boolean favorite, File file) {
		this.id = id;
		this.title = title;
		this.favorite = favorite;
		this.file = file;
	}
	
}
