package de.hdm_stuttgart.se2.softwareProject.mediathek.models;

import java.io.File;

import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;

abstract class Media implements IMedia{
	
	protected String title;
	protected boolean favorite;
	protected File file;
	protected boolean visible;
	
	public Media(String title, boolean favorite, File file, boolean visible) {
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
	public void getDetails() {
		if (this.visible == true) {
			System.out.println(this.title);
		}
	}

	@Override
	public void removeMedia() {
		this.visible = false;
	}

	@Override
	public String getTitle() {
		return this.title;
	}
	
	
	
	
	
}
