package de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces;

import java.io.File;
import java.util.Map;

public interface IMedialist {

	Map<File, IMedia> getContent();
	String getName();
	void removeMedia(IMedia m);
	void printList();
	void setName(String name);
	void addMedia(IMedia media);

}