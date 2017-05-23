package de.hdm_stuttgart.se2.softwareProject.mediathek;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;
import de.hdm_stuttgart.se2.softwareProject.mediathek.models.MediaFactory;

public class MediaFactoryTest {

	@Test
	public void testMediaFactoryMovie() {
	IMedia media = MediaFactory.getInstance("video", "aaa", new File("bbb.avi"), 5454, "2005", null, null, null, false, true, "4");
	Assert.assertEquals("Movie", media.getClass().getSimpleName());
	}
	
	@Test
	public void testMediaFactoryAudio() {
	IMedia media = MediaFactory.getInstance("audio", "aaa", new File("bbb.avi"), 5454, "2005", null, null, null, false, true, "4");
	Assert.assertEquals("Audio", media.getClass().getSimpleName());
	}
	
	@Test
	public void testMediaFactoryBook() {
	IMedia media = MediaFactory.getInstance("book", "aaa", new File("bbb.avi"), 5454, "2005", null, null, null, false, true, "4");
	Assert.assertEquals("Book", media.getClass().getSimpleName());
	}
	
	@Test
	public void testMediaFactoryNegative() {
	IMedia media = MediaFactory.getInstance("falseType", "aaa", new File("bbb.avi"), 5454, "2005", null, null, null, false, true, "4");
	Assert.assertEquals(null, media);
	}
}
