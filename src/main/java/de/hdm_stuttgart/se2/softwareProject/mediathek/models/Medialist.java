package de.hdm_stuttgart.se2.softwareProject.mediathek.models;

import java.util.HashMap;
import java.util.Map;

import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedialist;

public class Medialist implements IMedialist {

	private Map<Integer, IMedia> content;

	@Override
	public void createMap() {
		this.content = new HashMap<Integer, IMedia>();
	}

	@Override
	public Map<Integer, IMedia> getContent() {
		return content;
	}
	
	@Override
	public void removeMedia(IMedia m) {
		this.content.remove(m.getId());
	}
}
