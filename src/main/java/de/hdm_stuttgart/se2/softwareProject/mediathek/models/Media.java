package de.hdm_stuttgart.se2.softwareProject.mediathek.models;

import java.io.File;

import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;

abstract class Media implements IMedia{
	
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
	
	@Override
	public void openFile() {
		// TODO Impelementiere mich
	}

	@Override
	public int getId() {
		return this.id;
	}
	
	@Override
	public void getDetails() {
		System.out.println(this.title);
	}
	
}
