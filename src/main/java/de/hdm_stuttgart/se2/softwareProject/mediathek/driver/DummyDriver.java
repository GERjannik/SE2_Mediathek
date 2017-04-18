package de.hdm_stuttgart.se2.softwareProject.mediathek.driver;

import java.io.File;
import java.util.Map.Entry;
import java.util.Scanner;

import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedialist;


public class DummyDriver {

	public static void main(String[] args) {
		String path = "/stud/js329/Documents/testVideos/Sample Videos (52) - Copy.mp4.mp4";
		Settings s = new Settings(new File("/stud/dk100/Downloads/"));
		IMedialist[] scannedContent = MediaStorage.mediaScan(s.getDirectory());
		IMedialist movies = scannedContent[0];
		IMedialist audio = scannedContent[1];
		IMedialist books = scannedContent[2];
		Scanner scan = new Scanner(System.in);
		System.out.println("Was soll angezeigt werden? (0: Filme, 1: Audios)");
		int input = scan.nextInt();
		if (input == 0) {
			movies.printList();
		}
		if (input == 1) {
			audio.printList();
		}
		System.out.println("Die Metainformationen welcher Datei sollen geändert werden? (Dateiname)");
		scan.nextLine();
		String input2 = scan.nextLine();
		IMedia m = null;
		boolean finishedSearch = false;
		for (Entry<File, IMedia> i : movies.getContent().entrySet()) {
			if (i.getValue().getTitle().equals(input2)) {
				finishedSearch = true;
				m = i.getValue();
				break;
			}
		}
		if (finishedSearch == false) {
			for (Entry<File, IMedia> j : audio.getContent().entrySet()) {
				if (j.getValue().getTitle().equals(input2)) {
					finishedSearch = true;
					m = j.getValue();
					break;
				}
			}
		}
		MediaStorage.editMetaInformation(m, scan);
		System.out.println("Was soll angezeigt werden? (0: Filme, 1: Audios)");
		input = scan.nextInt();
		if (input == 0) {
			movies.printList();
		}
		if (input == 1) {
			audio.printList();
		}
		scan.close();
	}
}