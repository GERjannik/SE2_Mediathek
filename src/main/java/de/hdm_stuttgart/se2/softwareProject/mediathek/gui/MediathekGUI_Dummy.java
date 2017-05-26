package de.hdm_stuttgart.se2.softwareProject.mediathek.gui;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.MediaStorage;
import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.Settings;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedialist;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MediathekGUI_Dummy extends Application {
	
	private static Logger log = LogManager.getLogger(MediathekGUI_Dummy.class);
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	Settings s = new Settings();
	
	IMedialist movies;
	IMedialist audio;
	
	public IMedialist getMovies() {
		return movies;
	}

	public void setMovies(IMedialist movies) {
		this.movies = movies;
	}

	public IMedialist getAudio() {
		return audio;
	}

	public void setAudio(IMedialist audio) {
		this.audio = audio;
	}
	
	private static MediathekGUI_Dummy instance;
		
	public static MediathekGUI_Dummy getInstance() {
		return instance;
	}
	
	private void test() {
		
		
		Scanner scan = new Scanner(System.in);
		
		if (new File("settings.json").exists() && !(new File("settings.json").isDirectory())) {
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
					log.debug("Kein gültiges Verzeichnis angegeben");
					System.out.println("Die Eingabe ist kein gültiges Verzeichnis");
				}
			}
			s.readDirectory();
			scan.close();
		}
		
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Mediathek");
		
		initRootLayout();
	}

	private void initRootLayout() {
		
		try {
			test();
			IMedialist[] scannedContent = MediaStorage.mediaScan(s.getMediaDirectory());
			setMovies(scannedContent[0]);
			setAudio(scannedContent[1]);
			
			//Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MediathekGUI_Dummy.class.getResource("mediathek.fxml"));
			//loader.setLocation(MediathekGUI_Dummy.class.getResource("MediathekOverview.fxml"));
			rootLayout = (BorderPane) loader.load();
			
			//Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			scene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());
			
			
			
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
				
				
		launch(args);
		
		
	}
}
