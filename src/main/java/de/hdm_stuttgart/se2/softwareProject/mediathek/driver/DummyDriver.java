package de.hdm_stuttgart.se2.softwareProject.mediathek.driver;

import uk.co.caprica.vlcj.player.MediaMeta;

public class DummyDriver {

	public static void main(String[] args) {
		MediaMeta meta = MediaStorage.readMetaData("/stud/js329/Documents/testVideos/Sample Videos (52) - Copy.mp4.mp4");
		System.out.println(meta.getTitle());
		System.out.println(meta.getArtist());
		meta.setTitle("Test333");
		System.out.println(meta.getDate());
		System.out.println(meta.getLength());
		System.out.println(meta.getGenre());
		System.out.println(meta.getTitle());
		System.out.println(meta.getDescription());
		System.out.println(meta.getRating());
		meta.save();
		meta.release();
		
	}

}
