package de.hdm_stuttgart.se2.softwareProject.mediathek.gui;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.MediaStorage;
import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.Settings;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedialist;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
<<<<<<< HEAD
=======
import javafx.event.ActionEvent;
>>>>>>> a1381fcf753878609eda00b54e8a495aae8be211
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

		if (new File("settings.json").exists() && !(new File("settings.json").isDirectory())) {
			s.readDirectory();
		}
	}


	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Mediathek");

		initRootLayout();
	}

	private void initRootLayout() {
		
		boolean scanSuccessful = false;
		try {
			test();
			if (s.getMediaDirectory() != null && s.getMediaDirectory().isDirectory()) {
				class RescanCommand implements Runnable {

					@Override
					public void run() {
						IMedialist[] returns = MediaStorage.mediaScan(s.getMediaDirectory());
						movies = returns[0];
						audio = returns[1];
					}

				}

				new Thread(new RescanCommand()).start();
				scanSuccessful = true;
			}

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
			
			if (scanSuccessful == false) {
				new SettingWindow().show();
			}
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
