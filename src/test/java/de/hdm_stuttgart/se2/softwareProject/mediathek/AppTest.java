package de.hdm_stuttgart.se2.softwareProject.mediathek;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import de.hdm_stuttgart.se2.softwareProject.mediathek.Models.Movie;
import de.hdm_stuttgart.se2.softwareProject.mediathek.Models.Movielist;
import de.hdm_stuttgart.se2.softwareProject.mediathek.driver.MediaStorage;
import de.hdm_stuttgart.se2.softwareProject.mediathek.driver.Settings;

/**
 * Unit test for address database
 */
public class AppTest {

	@Test
	public void test_movieRead() {
		File f = new File ("/stud/js329/Documents/testVideos/");
		Settings test = new Settings(f);
		Movielist m = MediaStorage.createMovieInList(test.getDirectory());
		Assert.assertTrue(m.content.size() == f.listFiles().length);
	}
	 
}
