			package de.hdm_stuttgart.se2.softwareProject.mediathek;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.logging.log4j.core.util.FileUtils;
import org.junit.Assert;
import org.junit.Test;


import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.MediaStorage;
import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.Settings;
import de.hdm_stuttgart.se2.softwareProject.mediathek.exceptions.InvalidInputException;
import de.hdm_stuttgart.se2.softwareProject.mediathek.gui.GUIMedia;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedialist;
import de.hdm_stuttgart.se2.softwareProject.mediathek.lists.ListFactory;

public class MediaManipulationTest {

	@Test
	public void deleteFile_Test() throws IOException {
		new File("testDirectory").mkdirs();
		Settings s = new Settings();
		s.setMediaDirectory(new File("testDirectory"));
		new File("testDirectory/testfile.mp4").createNewFile();
		IMedialist[] content = MediaStorage.mediaScan(s.getMediaDirectory());
		IMedialist movies = content[0];
		IMedialist audio = content[1];
		GUIMedia.deleteMedia(s, new File("testDirectory/testfile.mp4"), movies, audio, true);
		Assert.assertTrue(s.getMediaDirectory().listFiles().length == 0);
		new File("testDirecotry/testfile.mp4").delete();

	}

	@Test(expected=InvalidInputException.class)
	public void deleteFile_Exception () {
		new File("testDirectory").mkdirs();
		Settings s = new Settings();
		s.setMediaDirectory(new File("testDirectory"));
		GUIMedia.deleteMedia(s, new File("aaaabbbc"), ListFactory.getInstance("video", "movies"), ListFactory.getInstance("audio", "audios"), true);
		Assert.assertEquals(null, s);
	}

}
