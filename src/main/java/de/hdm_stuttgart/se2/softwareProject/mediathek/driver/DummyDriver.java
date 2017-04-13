package de.hdm_stuttgart.se2.softwareProject.mediathek.driver;

import uk.co.caprica.vlcj.player.MediaMeta;

public class DummyDriver {

	public static void main(String[] args) {
		MediaMeta meta = MediaStorage.readMetaData("/stud/ll040/Desktop/DB1_Tutorial_gr-1.mp4");
		System.out.println(meta.getTitle());
	}

}
