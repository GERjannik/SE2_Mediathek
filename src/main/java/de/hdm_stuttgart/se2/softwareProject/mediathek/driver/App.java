package de.hdm_stuttgart.se2.softwareProject.mediathek.driver;

import java.io.File;
import java.util.Map.Entry;
import java.util.Scanner;

import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.MediaStorage;
import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.Settings;
import de.hdm_stuttgart.se2.softwareProject.mediathek.exceptions.InvalidInputException;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedialist;

// Ansatz - Enthält Fehler
public class App {

	public static void main(String[] args) {

		Settings s = new Settings();
		Scanner scan = new Scanner(System.in);

		if (new File ("settings.json").exists() && !(new File ("settings.json").isDirectory())) {
			s.readDirectory();
		} else {
			boolean validInput = false;
			while (validInput == false) {
				System.out.println("Welches Verzeichnis soll nach Medien durchsucht werden?");
				File input = new File (scan.nextLine());
				if (input.exists() && input.isDirectory()) {
					validInput = true;
					s.setDirectory(input.toString());
				} else {
					System.out.println("Die Eingabe ist kein gültiges Verzeichnis");
				}
			}
			s.readDirectory();
		}

		IMedialist[] scannedContent = MediaStorage.mediaScan(s.getMediaDirectory());
		IMedialist movies = scannedContent[0];
		IMedialist audio = scannedContent[1];
		/* Implementierung von books nur angedeutet (Interfaces) */
		//IMedialist books = scannedContent[2];
		loop:while(true) {
			menu();
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
				IMedia media;
				try {
					media = getInput(s, scan, movies, audio);
				} catch (InvalidInputException e) {
					System.out.println("Kein Medium mit diesem Titel gefunden. Kehre zurück ins Hauptmenü");
					break;
				}
				MediaStorage.editMetaInformation(media, scan);
				break;
			case 4:
				movies.printList();
				audio.printList();
				MediaStorage.deleteMedia(getInput(s, scan, movies, audio));
				break;
			case 5:
				boolean validInput = false;
				while (validInput == false) {
					System.out.println("Welches Verzeichnis soll nach Medien durchsucht werden?");
					scan.nextLine();
					File f = new File (scan.nextLine());
					if (f.exists() && f.isDirectory()) {
						validInput = true;
						s.setDirectory(f.toString());
						s.readDirectory();
					} else {
						System.out.println("Die Eingabe ist kein gültiges Verzeichnis");
					}
				}
				break;
			case 6:
				scannedContent = MediaStorage.mediaScan(s.getMediaDirectory());
				movies = scannedContent[0];
				audio = scannedContent[1];
				break;
			case 7:
				System.out.println("Bye");

				break loop;
			}

		}
	}

	public static void menu() throws InvalidInputException {
		System.out.println("Menü: \n"
				+ "0: Filme anzeigen\n"
				+ "1: Audios anzeigen\n"
				+ "2: Alle Medien anzeigen\n"
				+ "3: Metadaten bearbeiten\n"
				+ "4: Medium löschen\n"
				+ "5: Pfad setzen\n"
				+ "6: Medien neu einscannen\n"
				+ "7: Programm beenden\n");
	}

	public static IMedia getInput(Settings s, Scanner scan, IMedialist movies, IMedialist audio) {
		System.out.println("Gib den Titel des Mediums ein");
		scan.nextLine();
		String input = scan.nextLine();
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
		throw new InvalidInputException();
	}

} 
