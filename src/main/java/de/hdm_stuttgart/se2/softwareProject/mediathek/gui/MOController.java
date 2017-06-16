package de.hdm_stuttgart.se2.softwareProject.mediathek.gui;

import java.awt.Color;
import java.io.File;
import java.lang.Thread.State;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import javax.swing.text.TabExpander;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;

import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.MediaStorage;
import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.Settings;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedialist;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import uk.co.caprica.vlcj.player.MediaMeta;

public class MOController implements Initializable {

	private static Logger log = LogManager.getLogger(MOController.class);

	private static MOController instance;

	IMedialist movies, audio;
	Settings s = new Settings();
	String play_data;
	String ranking;

	ObservableList<GUIMedia> data;
	FilteredList<GUIMedia> filterdData;
	SortedList<GUIMedia> sortedData;
	IMedialist[] scannedContent;

	@FXML ToggleGroup toggleGroup;
	@FXML ToggleButton tb_movies;
	@FXML ToggleButton tb_audio;
	@FXML ToggleButton tb_books;

	@FXML ToggleGroup favoGroup;
	@FXML ToggleButton tb_yes;
	@FXML ToggleButton tb_no;

	@FXML ToggleGroup radioGroup;
	@FXML RadioButton rb_one;
	@FXML RadioButton rb_two;
	@FXML RadioButton rb_three;
	@FXML RadioButton rb_four;
	@FXML RadioButton rb_five;

	@FXML Label playlist;
	@FXML Label list;
	@FXML Label l_news;

	@FXML Button btn_save;
	@FXML Button btn_edit;
	@FXML Button btn_search;

	@FXML TextField tf_title;
	@FXML TextField tf_year;
	@FXML TextField tf_artist;
	@FXML TextField tf_genre;
	@FXML TextField tf_search;

	// Table FXML
	@FXML TableView<GUIMedia> tableview = new TableView<GUIMedia>();
	@FXML TableColumn<GUIMedia, String> col_title = new TableColumn<>("Titel");
	@FXML TableColumn<GUIMedia, Long> col_length = new TableColumn<GUIMedia, Long>("Länge");
	@FXML TableColumn<GUIMedia, String> col_date = new TableColumn<GUIMedia, String>("Erscheinung");
	@FXML TableColumn<GUIMedia, String> col_artist  = new TableColumn<GUIMedia, String>("Director");
	@FXML TableColumn<GUIMedia, String> col_genre  = new TableColumn<GUIMedia, String>("Genre");
	@FXML TableColumn<GUIMedia, String> col_ranking = new TableColumn<GUIMedia, String>("Bewertung");

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Zuweisung der Spalten, für das passende füllen der TableView
		col_title.setMinWidth(100);
		col_title.setCellValueFactory(
				new PropertyValueFactory<GUIMedia, String>("title"));
		col_length.setMinWidth(100);
		col_length.setCellValueFactory(
				new PropertyValueFactory<GUIMedia, Long>("length"));
		col_date.setMinWidth(100);
		col_date.setCellValueFactory(
				new PropertyValueFactory<GUIMedia, String>("date"));
		col_artist.setMinWidth(100);
		col_artist.setCellValueFactory(
				new PropertyValueFactory<GUIMedia, String>("artist"));
		col_genre.setMinWidth(100);
		col_genre.setCellValueFactory(
				new PropertyValueFactory<GUIMedia, String>("genre"));
		col_ranking.setMinWidth(100);
		col_ranking.setCellValueFactory(
				new PropertyValueFactory<GUIMedia, String>("ranking"));

		data = FXCollections.observableArrayList();

		if (new File("settings.json").exists() && !(new File("settings.json").isDirectory())) {
			s.readDirectory();

			scannedContent = MediaStorage.mediaScan(s.getMediaDirectory());
			movies = scannedContent[0];
			audio = scannedContent[1];


			for (IMedia i : movies.getContent().values()) {
				data.add(new GUIMedia(i.getFile().getName(), i.getDuration(), i.getDate(), i.getArtist(), i.getGenre()));
			}

			for (IMedia i : audio.getContent().values()) {
				data.add(new GUIMedia(i.getFile().getName(), i.getDuration(), i.getDate(), i.getArtist(), i.getGenre()));
			}
			tableview.setItems(data);
		}
		//Abfrage ob Pfad eingetragen ist, wenn ja füllen der ObservableList für die Tableview
		//Thread läuft alle 3 Sekunden geänderte Film oder Audio Listen anzupassen und neu in der 
		//Tableview anzuzeigen.
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					if(tf_search.getText() == null || tf_search.getText().isEmpty()) {
						// Erstellen der ObservableList zum füllen der TableView

						if (new File("settings.json").exists() && !(new File("settings.json").isDirectory())) {
							s.readDirectory();
							scannedContent = MediaStorage.mediaScan(s.getMediaDirectory());

							//TODO Abfrage aktuell über die Größe, muss noch angepasst werden. Vergleich mit aktuellen Daten muss erstellt werden...
							if ( movies.getContent().size() != scannedContent[0].getContent().size() || audio.getContent().size() != scannedContent[1].getContent().size()) {

								movies = scannedContent[0];
								audio = scannedContent[1];

								data.clear();

								for (IMedia i : movies.getContent().values()) {
									data.add(new GUIMedia(i.getFile().getName(), i.getDuration(), i.getDate(), i.getArtist(), i.getGenre()));
								}

								for (IMedia i : audio.getContent().values()) {
									data.add(new GUIMedia(i.getFile().getName(), i.getDuration(), i.getDate(), i.getArtist(), i.getGenre()));
								}
								tableview.setItems(data);
							}							
						}

						filterdData = new FilteredList<>(data, f -> true);

						// 2. Set the filter Predicate whenever the filter changes.
						tf_search.textProperty().addListener((observable, oldValue, newValue) -> {
							filterdData.setPredicate(Media -> {
								// If filter text is empty, display all Media.
								if (newValue == null || newValue.isEmpty()) {
									System.out.println("Filtertext ist empty");
									return true;
								}

								// Compare first name and last name of every person with filter text.
								String lowerCaseFilter = newValue.toLowerCase();

								if (Media.getTitle().toLowerCase().contains(lowerCaseFilter)) {
									System.out.println("false");
									return true; // Filter matches titel.
								} 
								System.out.println("false");
								return false; // Does not match.
							});
						});
						// 3. Wrap the FilteredList in a SortedList. 
						sortedData = new SortedList<>(filterdData);

						// 4. Bind the SortedList comparator to the TableView comparator.
						sortedData.comparatorProperty().bind(tableview.comparatorProperty());

						// 5. Add sorted (and filtered) data to the table.
						tableview.setItems(sortedData);

						try {Thread.sleep(1000);} catch (InterruptedException e) {}
					} 
				} 
			}}, "rescanThread").start();
	}
	@FXML 
	public void btn_settings_clicked() {	

		new SettingWindow().show();			
	}

	@FXML
	public void btn_save_clicked(ActionEvent event) throws ParseException{

		String save_titel = tf_title.getText();
		String save_year = tf_year.getText();
		String save_artist = tf_artist.getText();
		String save_genre = tf_genre.getText();
		boolean save_favo = false;

		if (radioGroup.hasProperties()){
			ranking = radioGroup.getSelectedToggle().toString().substring(radioGroup.getSelectedToggle().toString().length()-2, radioGroup.getSelectedToggle().toString().length()-1);
		} 		

		//TODO
		ToggleButton favo = (ToggleButton) favoGroup.getSelectedToggle();
		
		if (favo == null){
			save_favo = false;
		}
		else if (favo.equals(tb_yes)) {
			save_favo = true;
		}

		IMedia media;

		if (tableview.getSelectionModel().isEmpty()){
			l_news.setTextFill(javafx.scene.paint.Color.RED);
			l_news.setText("Stellen Sie sicher, das ein Medium ausgewählt ist");
		} else {
			play_data = tableview.getSelectionModel().getSelectedItem().getTitle().toString();

			media = GUIMedia.getInput(s, play_data, movies, audio);

			GUIMedia.editMetaInformation(media, save_titel, save_year, save_artist, save_genre, ranking, save_favo); 

			tf_title.setText(null);
			tf_year.setText(null);
			tf_artist.setText(null);
			tf_genre.setText(null);
			radioGroup.selectToggle(null);
			favoGroup.selectToggle(null);

			initialize(null, null);
		}
	}

	@FXML
	public void tableview_mouse_clicked(){

		tf_title.setText(tableview.getSelectionModel().getSelectedItem().getTitle());
		tf_year.setText(tableview.getSelectionModel().getSelectedItem().getDate());
		tf_artist.setText(tableview.getSelectionModel().getSelectedItem().getArtist());
		tf_genre.setText(tableview.getSelectionModel().getSelectedItem().getGenre());

	}

	@FXML
	public void btn_play_clicked(ActionEvent event){

		play_data = tableview.getSelectionModel().getSelectedItem().getTitle();

		GUIMedia.playMovie(s, play_data, movies, audio);

	}

	@FXML
	public void btn_del_clicked() {

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
