package de.hdm_stuttgart.se2.softwareProject.mediathek;

import static org.junit.Assert.*;

import org.junit.Test;

import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedialist;
import de.hdm_stuttgart.se2.softwareProject.mediathek.lists.ListFactory;

public class MedialistFactoryTest {

	@Test
	public void testMovielist() {
		IMedialist list = ListFactory.getInstance("video", "testListe");
		assertEquals("Movielist", list.getClass().getSimpleName());
	}
	
	@Test
	public void testAudiolist() {
		IMedialist list = ListFactory.getInstance("audio", "testListe");
		assertEquals("Audiolist", list.getClass().getSimpleName());
	}
	
	@Test
	public void testBookList() {
		IMedialist list = ListFactory.getInstance("book", "testListe");
		assertEquals("Booklist", list.getClass().getSimpleName());
	}
	
	@Test
	public void testMedialistNegative() {
		IMedialist list = ListFactory.getInstance("falseType", "testListe");
		assertEquals(null, list);
	}

}
