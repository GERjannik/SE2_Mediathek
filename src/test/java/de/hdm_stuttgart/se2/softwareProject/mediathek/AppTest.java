package de.hdm_stuttgart.se2.softwareProject.mediathek;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import de.hdm_stuttgart.se2.softwareProject.mediathek.driver.MediaStorage;
import de.hdm_stuttgart.se2.softwareProject.mediathek.driver.Settings;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedialist;

public class AppTest {

	@Test
	public void test_movieReadAtHDM() {
		File f = new File ("/stud/js329/Documents/testVideos/");
		Settings test = new Settings(f);
		IMedialist[] m = MediaStorage.mediaScan(test.getDirectory());
		IMedialist movies = m[0];
		IMedialist audio = m[1];
		IMedialist books = m[2];
		Assert.assertTrue(movies.getContent().size() + audio.getContent().size() + books.getContent().size() == f.listFiles().length);
		movies.printList();
		System.out.println("---------------------------");
		audio.printList();
		System.out.println("---------------------------");
		books.printList();
	}
	 
	@Test
	public void test_movieReadJannikAtHome() {
		File f = new File ("/home/jannik/Downloads/testVideos/");
		Settings test = new Settings(f);
		IMedialist[] m = MediaStorage.mediaScan(test.getDirectory());
		IMedialist movies = m[0];
		IMedialist audio = m[1];
		IMedialist books = m[2];
		Assert.assertTrue(movies.getContent().size() + audio.getContent().size() + books.getContent().size() == f.listFiles().length);
		movies.printList();
		System.out.println("---------------------------");
		audio.printList();
		System.out.println("---------------------------");
		books.printList();
	}
	
	@Test
	public void test_deleteMedia() {
		File f = new File ("/home/jannik/Downloads/testVideos/");
		Settings test = new Settings(f);
		IMedialist[] m = MediaStorage.mediaScan(test.getDirectory());
		IMedialist movies = m[0];
		IMedialist audio = m[1];
		IMedialist books = m[2];
		movies.printList();
		MediaStorage.deleteMedia(movies.getContent().get(movies.getContent().entrySet().iterator().next().getKey()));
		movies.printList();
	}
}
