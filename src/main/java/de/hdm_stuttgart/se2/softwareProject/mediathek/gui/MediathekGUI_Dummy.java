package de.hdm_stuttgart.se2.softwareProject.mediathek.gui;

import java.io.File;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.MediaStorage;
import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.Settings;
import de.hdm_stuttgart.se2.softwareProject.mediathek.gui.MOController.Media;
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

public class MediathekGUI_Dummy extends Application {

	private static MediathekGUI_Dummy instance;
	private static Logger log = LogManager.getLogger(MediathekGUI_Dummy.class);

	private Stage primaryStage;
	private BorderPane rootLayout;


	@FXML ToggleButton btn_movie;
	@FXML Label playlist;
	@FXML Label list;
	@FXML Button btn_save;
	@FXML Button btn_edit;
	// Table FXML
	@FXML TableView<Media> tableview = new TableView<Media>();
	@FXML TableColumn<Media, String> col_title = new TableColumn<>("Titel");
	@FXML TableColumn<Media, Long> col_length = new TableColumn<Media, Long>("Länge");
	@FXML TableColumn<Media, String> col_date = new TableColumn<Media, String>("Erscheinung");
	@FXML TableColumn<Media, String> col_artist  = new TableColumn<Media, String>("Director");
	@FXML TableColumn<Media, String> col_genre  = new TableColumn<Media, String>("Genre");

	Settings s = new Settings();

	IMedialist movies, audio;

	@Override
	public void start(Stage primaryStage) throws IOException {

		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Mediathek");

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
			loader.setLocation(MediathekGUI_Dummy.class.getResource("mediathek.fxml"));
			//loader.setLocation(MediathekGUI_Dummy.class.getResource("MediathekOverview.fxml"));

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








	public class Media {

		private final SimpleStringProperty title;
		private final SimpleLongProperty length;
		private final SimpleStringProperty date;
		private final SimpleStringProperty artist;
		private final SimpleStringProperty genre; 

		public Media(String title, Long length, String date, String artist, String genre) {
			this.title = new SimpleStringProperty(title);
			this.length = new SimpleLongProperty(length);
			this.date = new SimpleStringProperty(date);
			this.artist = new SimpleStringProperty(artist);
			this.genre = new SimpleStringProperty(genre);
		}

		public String getTitle() {
			return title.get();
		}

		public long getLength() {
			return length.get();
		}

		public String getDate() {
			return date.get();
		}

		public String getArtist() {
			return artist.get();
		}

		public String getGenre() {
			return genre.get();
		}

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

	public static MediathekGUI_Dummy getInstance() {
		return instance;
	}
}
