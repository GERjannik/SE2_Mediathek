package de.hdm_stuttgart.se2.softwareProject.mediathek.models;

import java.io.File;

import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;

abstract class Media implements IMedia{
	
	protected String typ;
	protected String title;
	protected boolean favorite;
	protected File file;
	protected boolean visible;
	
	public Media(String typ, String title, boolean favorite, File file, boolean visible) {
		this.typ = typ;
		this.title = title;
		this.favorite = favorite;
		this.file = file;
		this.visible = visible;
	}
	
	@Override
	public void openFile() {
		// TODO Impelementiere mich
	}

	@Override
	public File getFile() {
		return this.file;
	}
	
	@Override
	public String getTyp() {
		return this.typ;
	}

	@Override
	public void getDetails() {
		if (this.visible == true) {
			System.out.println(this.title);
		}
	}

	// Wenn Medium nur aus Mediathek gelöscht wird, wird dieses Medium einfach nicht mehr ausgegeben
	@Override
	public void removeMedia() {
		this.visible = false;
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;		
	}

	@Override
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}	
}
