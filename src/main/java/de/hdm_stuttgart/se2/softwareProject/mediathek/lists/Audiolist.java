package de.hdm_stuttgart.se2.softwareProject.mediathek.lists;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
	public String getName() {
		return this.name;
	}

	@Override
	public void removeMedia(IMedia m) {
		this.content.remove(m.getFile());		
	}

	@Override
	public void printList() {
		for (Entry<File, IMedia> m : this.content.entrySet()) {
			m.getValue().getDetails();
		}
	}

	@Override
	public void setName(String name) {
		this.name = name;		
	}

	@Override
	public void addMedia(IMedia media) {
		this.content.put(media.getFile(), media);
	}
}
