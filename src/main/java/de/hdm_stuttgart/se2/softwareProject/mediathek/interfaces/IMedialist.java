package de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces;

import java.util.HashMap;
import java.util.Map;

public interface IMedialist {

	void createMap();
	Map<Integer, IMedia> getContent();
	void removeMedia(IMedia m);

}