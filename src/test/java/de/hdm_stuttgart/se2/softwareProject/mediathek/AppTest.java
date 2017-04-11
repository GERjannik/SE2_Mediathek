package de.hdm_stuttgart.se2.softwareProject.mediathek;

import java.io.File;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

import de.hdm_stuttgart.se2.softwareProject.mediathek.driver.MediaStorage;
import de.hdm_stuttgart.se2.softwareProject.mediathek.driver.Settings;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedialist;

/**
 * Unit test for address database
 */
public class AppTest {

	@Test
	public void test_movieRead() {
		File f = new File ("/stud/js329/Documents/testVideos/");
		Settings test = new Settings(f);
		IMedialist[] m = MediaStorage.createMedialists(f);
		IMedialist movies = m[0];
		IMedialist audio = m[1];
		IMedialist books = m[2];
		//Assert.assertTrue(m.size() == f.listFiles().length);
		movies.getContent().get(1001).getDetails();
	}
	 
}
