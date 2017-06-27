package de.hdm_stuttgart.se2.softwareProject.mediathek.controller;

import java.io.File;
import java.util.ArrayList;

abstract class ScanDirectoryRecursive {

	private static ArrayList<File> getPaths(File file, ArrayList<File> list) {
		if (file == null || list == null || !file.isDirectory()) {
			return null;
		}
		File[] fileArray = file.listFiles();
		for (File f : fileArray) {
			if (f.isHidden()) {
				continue;
			}
			if (f.isDirectory()) {
				getPaths(f, list);
			} else {
				list.add(f);
			}
		}
		return list;
	}
	
	public static ArrayList<File> createFileList (File file) {
		return getPaths(file, new ArrayList<>());	
	}
}
