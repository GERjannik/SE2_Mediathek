package de.hdm_stuttgart.se2.softwareProject.mediathek.controller;

import java.io.File;
import java.util.ArrayList;

abstract class ScanDirectoryRecursive {
	
	/**
	 * Speichert Dateien eines Ordners und seiner Unterordner als File Objekte in 
	 * einer Liste bzw. fügt sie zu einer bestehenden ArrayList mit File Objekten
	 * hinzu.
	 * @param file Ordnerpfad in dem sich die Dateien und ggf. Unterordner befinden.
	 * @param list ArrayList mit bereits gescannten File Objekten.
	 * @return Liste mit File Objekten erweitert um die gescannten Ordnerpfade.
	 */
	private static ArrayList<File> getPaths(File file, ArrayList<File> list) {
		if (file == null || list == null || !file.isDirectory()) {
			return null;
		}
		File[] fileArray = file.listFiles();
		for (File f : fileArray) {
			if (f.isDirectory()) {
				getPaths(f, list);
			} else {
				list.add(f);
			}
		}
		return list;
	}
	
	/**
	 * Erstellt Liste mit File Objekten innerhalb eines gegebenen Pfades
	 * @param file Pfad der Dateien enthält
	 * @return Liste mit File Objekten der im angegebeben Pfad enthaltenen Dateien
	 */
	public static ArrayList<File> createFileList (File file) {
		return getPaths(file, new ArrayList<>());	
	}
}
