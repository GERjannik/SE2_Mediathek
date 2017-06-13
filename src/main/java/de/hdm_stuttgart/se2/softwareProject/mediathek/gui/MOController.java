package de.hdm_stuttgart.se2.softwareProject.mediathek.gui;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.MediaStorage;
import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.Settings;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedialist;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

public class MOController implements Initializable {

	private static Logger log = LogManager.getLogger(MOController.class);

	private static MOController instance;

	IMedialist movies, audio;
	Settings s = new Settings();

	@FXML ToggleButton tb_movies;
	@FXML ToggleButton tb_audio;
	@FXML ToggleButton tb_books;

	@FXML Label playlist;
	@FXML Label list;
	@FXML Button btn_save;
	@FXML Button btn_edit;

	@FXML TextField tf_title;
	@FXML TextField tf_year;
	@FXML TextField tf_artist;
	@FXML TextField tf_genre;
	// Table FXML
	@FXML TableView<Media> tableview = new TableView<Media>();
	@FXML TableColumn<Media, String> col_title = new TableColumn<>("Titel");
	@FXML TableColumn<Media, Long> col_length = new TableColumn<Media, Long>("Länge");
	@FXML TableColumn<Media, String> col_date = new TableColumn<Media, String>("Erscheinung");
	@FXML TableColumn<Media, String> col_artist  = new TableColumn<Media, String>("Director");
	@FXML TableColumn<Media, String> col_genre  = new TableColumn<Media, String>("Genre");



	@Override
	public void initialize(URL location, ResourceBundle resources) {

		//TODO Liste wird immer neuer erzeugt, daher Verlust des Focus. Muss geändert werden! ABER WIE???

		// Zuweisung der Spalten, für das passende füllen der TableView
		col_title.setMinWidth(100);
		col_title.setCellValueFactory(
				new PropertyValueFactory<Media, String>("title"));
		col_length.setMinWidth(100);
		col_length.setCellValueFactory(
				new PropertyValueFactory<Media, Long>("length"));
		col_date.setMinWidth(100);
		col_date.setCellValueFactory(
				new PropertyValueFactory<Media, String>("date"));
		col_artist.setMinWidth(100);
		col_artist.setCellValueFactory(
				new PropertyValueFactory<Media, String>("artist"));
		col_genre.setMinWidth(100);
		col_genre.setCellValueFactory(
				new PropertyValueFactory<Media, String>("genre"));

		//Abfrage ob Pfad eingetragen ist, wenn ja füllen der ObservableList für die Tableview
		//Thread läuft alle 3 Sekunden geänderte Film oder Audio Listen anzupassen und neu in der 
		//Tableview anzuzeigen.
		new Thread(new Runnable() {
			public void run() {
				while (true) {

					// Erstellen der ObservableList zum füllen der TableView
					ObservableList<Media> data = FXCollections.observableArrayList();

					if (new File("settings.json").exists() && !(new File("settings.json").isDirectory())) {
						s.readDirectory();

						IMedialist[] scannedContent = MediaStorage.mediaScan(s.getMediaDirectory());
						movies = scannedContent[0];
						audio = scannedContent[1];

						for (IMedia i : movies.getContent().values()) {
							data.add(new Media(i.getTitle(), i.getDuration(), i.getDate(), i.getArtist(), i.getGenre()));
						}
						for (IMedia i : audio.getContent().values()) {
							data.add(new Media(i.getTitle(), i.getDuration(), i.getDate(), i.getArtist(), i.getGenre()));
						}
					} 
					tableview.setItems(data); 

					try {Thread.sleep(3000);} catch (InterruptedException e) {}

				}
			}}).start();

	}

	@FXML 
	public void btn_settings_clicked() {	

		new SettingWindow().show();			
	}

	@FXML
	public void btn_save_clicked(ActionEvent event){
		System.out.println("Saved");

	}

	@FXML
	public void btn_edit_clicked(ActionEvent event){

	}

	@FXML
	public void btn_play_clicked(ActionEvent event){

		String play_data = tableview.getSelectionModel().getSelectedItem().getTitle();

		Media.playMovie(s, play_data, movies, audio);

	}

	@FXML
	public void btn_end_clicked() {
		System.exit(0);
	}

	//TODO Hinzufügen von Medien in die Playlist
	// Idee: Mit getSelection Playliste und dann "Film" markieren?
	@FXML
	private void btn_plus_clicked() {

	}

	//TODO Entfernen von Medien aus der Playlist
	// Idee: Mit getSelection Playliste und dann "Film" markieren?
	@FXML
	private void btn_minus_clicked() {

	}

	public static MOController getInstance() {
		return instance;
	}

}
