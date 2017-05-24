package de.hdm_stuttgart.se2.softwareProject.mediathek.gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.tools.Tool;

import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.MediaStorage;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedialist;
import de.hdm_stuttgart.se2.softwareProject.mediathek.lists.ListFactory;
import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.Settings;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MediathekGUI_Dummy extends Application {
	
	private static Logger log = LogManager.getLogger(MediathekGUI_Dummy.class);
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	private static MediathekGUI_Dummy instance;
		
	public static MediathekGUI_Dummy getInstance() {
		return instance;
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Mediathek");
		
		initRootLayout();
	}

	private void initRootLayout() {
		
		try {
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
		//TODO 
		// DANI????
		// Settings
		//Settings s = new Settings();
		//ArrayList<IMedialist> allLists = new ArrayList<>();
		//s.readDirectory();
		//IMedialist[] scannedContent = MediaStorage.mediaScan(s.getMediaDirectory());
		//IMedialist movies = scannedContent[0];
		//System.out.println(movies);
		//ObservableList<IMedia> Media = FXCollections.observableArrayList();
				
		launch(args);
		
		
	}
}
