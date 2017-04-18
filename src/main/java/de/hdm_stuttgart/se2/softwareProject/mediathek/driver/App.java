package de.hdm_stuttgart.se2.softwareProject.mediathek.driver;

import java.io.File;
import java.util.Map.Entry;
import java.util.Scanner;

import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedialist;

// Ansatz - Enthält Fehler
public class App {

	public static void main(String[] args) {
		// TODO Dateiverzeichnis manuell vom Benutzer festlegen lassen
		Settings s = new Settings(new File ("/stud/js329/Documents/testVideos/"));
		System.out.println("Medien werden eingelesen");
		IMedialist[] scannedContent = MediaStorage.mediaScan(s.getDirectory());
		IMedialist movies = scannedContent[0];
		IMedialist audio = scannedContent[1];
		IMedialist books = scannedContent[2];
		menu(s, movies, audio);
		
	}
	
	public static void menu(Settings s, IMedialist movies, IMedialist audio) {
		System.out.println("Menü: \n"
				+ "0: Filme anzeigen\n"
				+ "1: Audios anzeigen\n"
				+ "2: Alle Medien anzeigen\n"
				+ "3: Metadaten bearbeiten\n"
				+ "4: Medium löschen\n"
				+ "5: Programm beenden\n");
		Scanner scan = new Scanner(System.in);
		int input = scan.nextInt();
		switch (input) {
		case 0:
			movies.printList();
			break;
		case 1:
			audio.printList();
			break;
		case 2:
			movies.printList();
			audio.printList();
			break;
		case 3:
			movies.printList();
			audio.printList();
			MediaStorage.editMetaInformation(getInput(s, scan, movies, audio), scan);
			break;
		case 4:
			movies.printList();
			audio.printList();
			MediaStorage.deleteMedia(getInput(s, scan, movies, audio));
			break;
		}
		menu(s, movies, audio);
	}
	
	public static IMedia getInput(Settings s, Scanner scan, IMedialist movies, IMedialist audio) {
		System.out.println("Gib den Titel des Mediums ein");
		scan.nextLine();
		String input = scan.nextLine();
		try {
			for (Entry<File, IMedia> i : movies.getContent().entrySet()) {
				if (i.getValue().getTitle().equals(input)) {
					return i.getValue();
				}
			}
			for (Entry<File, IMedia> i : audio.getContent().entrySet()) {
				if (i.getValue().getTitle().equals(input)) {
					return i.getValue();
				}
			}
		} catch (Exception e) {
			System.out.println("Kein Medium mit diesem Titel gefunden. Kehre zurück ins Hauptmenü");
			menu(s, movies, audio);
		}
		return null;
	}

} 
