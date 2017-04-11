package de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces;

import java.io.File;
import java.util.Map;

public interface IMedialist {

	Map<File, IMedia> getContent();
	void removeMedia(IMedia m);

}