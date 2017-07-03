package de.hdm_stuttgart.se2.softwareProject.mediathek.gui;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MediathekGUI extends Application {

	/*private static MediathekGUI instance;*/
	private static Logger log = LogManager.getLogger(MediathekGUI.class);

	private Stage primaryStage;
	private BorderPane rootLayout;


	@Override
	public void start(Stage primaryStage) throws IOException {

		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Mediathek by DJLB");

		initRootLayout();
	}

	private void initRootLayout() throws IOException {

		//Load root layout from fxml file.
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MediathekGUI.class.getResource("MediathekOverview.fxml"));

		//Sprung in die MOController initialize Methode
		rootLayout = (BorderPane) loader.load();

		//Show the scene containing the root layout.
		Scene scene = new Scene(rootLayout);
		scene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();	
		
		log.debug("GUI wurde geladen");
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	          public void handle(WindowEvent we) {
	        	  log.debug("GUI wurde beendet");
	          }
	      }); 
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {

		launch(args);
	}
/*
	public static MediathekGUI getInstance() {
		return instance;
	}*/
}
