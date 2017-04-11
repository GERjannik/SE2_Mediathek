package de.hdm_stuttgart.se2.softwareProject.mediathek.lists;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedialist;

class Audiolist implements IMedialist {
	
	private Map<File, IMedia> content;
	private String name;

	public Audiolist(String name) {
		this.content = new HashMap<File, IMedia>();
		this.name = name;
	}

	@Override
	public Map<File, IMedia> getContent() {
		return content;
	}

	@Override
	public void removeMedia(IMedia m) {
		// TODO Auto-generated method stub
		
	}

}
