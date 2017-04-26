package de.hdm_stuttgart.se2.softwareProject.mediathek.models;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;

abstract class Media implements IMedia{
	
	private static Logger log = LogManager.getLogger(Media.class);
	
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
		log.debug("Titel des Mediums " + this.file + " auf " + this.title + " geändert");
	}

	@Override
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
		log.debug("Medium " + this.file + " zu Favoriten hinzugefügt");
	}
	
	@Override
	public void open() {
		Desktop d = Desktop.getDesktop();  
		try {
			d.open(this.file);
			log.info("Medium " + this.file + " geöffnet");
		} catch (IOException e) {
			log.catching(e);
			e.printStackTrace();
		}
	}
}
