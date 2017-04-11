package de.hdm_stuttgart.se2.softwareProject.mediathek.models;

import java.io.File;

import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;

abstract class Media implements IMedia{
	
	protected String title;
	protected boolean favorite;
	protected File file;
	
	public Media(String title, boolean favorite, File file) {
		this.title = title;
		this.favorite = favorite;
		this.file = file;
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
		System.out.println(this.title);
	}
	
}
