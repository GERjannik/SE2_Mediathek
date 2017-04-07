package de.hdm_stuttgart.se2.softwareProject.mediathek.models;

import java.util.HashMap;
import java.util.Map;

public class Movielist {

	public Map<Integer, Movie> content;

	public void createMap() {
		this.content = new HashMap<Integer, Movie>();
	}

	public Map<Integer, Movie> getContent() {
		return content;
	}
}
