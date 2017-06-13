package de.hdm_stuttgart.se2.softwareProject.mediathek.gui;

import java.io.File;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.MediaStorage;
import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.Settings;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedialist;
import javafx.application.Application;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MediathekGUI extends Application {

	private static MediathekGUI instance;
	private static Logger log = LogManager.getLogger(MediathekGUI.class);

	private Stage primaryStage;
	private BorderPane rootLayout;

	Settings s = new Settings();

	IMedialist movies, audio;

	@Override
	public void start(Stage primaryStage) throws IOException {

		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Mediathek by DJLB");

		initRootLayout();
	}

	private void initRootLayout() throws IOException {

		boolean scanSuccessful = false;
		try {
			if (new File("settings.json").exists() && !(new File("settings.json").isDirectory())) {
				s.readDirectory();
			}
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
			loader.setLocation(MediathekGUI.class.getResource("MediathekOverview.fxml"));

			//Sprung in die MOController initialize Methode
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

	public static MediathekGUI getInstance() {
		return instance;
	}
}
