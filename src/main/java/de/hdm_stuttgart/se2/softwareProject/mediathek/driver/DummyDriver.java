package de.hdm_stuttgart.se2.softwareProject.mediathek.driver;

import java.io.File;
import java.util.InputMismatchException;
import java.util.Map.Entry;
import java.util.Scanner;

import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.MediaStorage;
import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.Settings;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedialist;


public class DummyDriver {


	public static void main(String[] args) {

		try(Scanner scan = new Scanner(System.in)) {
			//String path = "/stud/js329/Documents/testVideos/Sample Videos (52) - Copy.mp4.mp4";
			Settings s = new Settings();
			
			IMedialist[] scannedContent = MediaStorage.mediaScan(s.getMediaDirectory());
			IMedialist movies = scannedContent[0];
			IMedialist audio = scannedContent[1];
			/* Implementierung von books nur angedeutet (Interfaces) */
			//IMedialist books = scannedContent[2];
			
			System.out.println("Was soll angezeigt werden? (0: Filme, 1: Audios)");
			int input = scan.nextInt();
			if (input == 0) {
				movies.printList();
			}
			if (input == 1) {
				audio.printList();
			}

			
			System.out.println("Welcher Film soll gespielt werden?");
			scan.nextLine();
			String input2 = scan.nextLine();
			for (Entry<File, IMedia> i : movies.getContent().entrySet()) {
				if (i.getValue().getTitle().equals(input2)) {
					i.getValue().open();
					break;
				}
			}

			
			System.out.println("Die Metainformationen welcher Datei sollen geändert werden? (Dateiname)");
			scan.nextLine();
			//String input2 = scan.nextLine();
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
		} catch(NullPointerException e) {
			System.out.println("Path oder File nicht gefunden");
			// logging
			throw e;
		} catch(InputMismatchException e) {
			System.out.println("Input stimmt nicht mit Variablentyp überein");
			// logging
			throw e;
		} catch(Exception e) {
			// logging
			throw e;
		}
	}
}