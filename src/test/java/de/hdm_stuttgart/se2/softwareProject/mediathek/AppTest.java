package de.hdm_stuttgart.se2.softwareProject.mediathek;

import java.io.File;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

import de.hdm_stuttgart.se2.softwareProject.mediathek.driver.MediaStorage;
import de.hdm_stuttgart.se2.softwareProject.mediathek.driver.Settings;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;

/**
 * Unit test for address database
 */
public class AppTest {

	@Test
	public void test_movieRead() {
		File f = new File ("/stud/js329/Documents/testVideos/");
		Settings test = new Settings(f);
		//HashMap<Integer, IMedia> m = MediaStorage.createMovieInList(test.getDirectory());
		//Assert.assertTrue(m.size() == f.listFiles().length);
		//m.get(1001).getDetails();
	}
	 
}
