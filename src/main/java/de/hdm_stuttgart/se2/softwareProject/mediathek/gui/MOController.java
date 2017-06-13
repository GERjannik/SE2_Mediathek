package de.hdm_stuttgart.se2.softwareProject.mediathek.gui;

import java.io.File;
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

	ObservableList<Media> data;
	FilteredList<Media> filterdData;
	SortedList<Media> sortedData;

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
	@FXML TableView<Media> tableview = new TableView<Media>();
	@FXML TableColumn<Media, String> col_title = new TableColumn<>("Titel");
	@FXML TableColumn<Media, Long> col_length = new TableColumn<Media, Long>("Länge");
	@FXML TableColumn<Media, String> col_date = new TableColumn<Media, String>("Erscheinung");
	@FXML TableColumn<Media, String> col_artist  = new TableColumn<Media, String>("Director");
	@FXML TableColumn<Media, String> col_genre  = new TableColumn<Media, String>("Genre");
	@FXML TableColumn<Media, String> col_ranking = new TableColumn<Media, String>("Bewertung");

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
		col_ranking.setMinWidth(100);
		col_ranking.setCellValueFactory(
				new PropertyValueFactory<Media, String>("ranking"));

		//Abfrage ob Pfad eingetragen ist, wenn ja füllen der ObservableList für die Tableview
		//Thread läuft alle 3 Sekunden geänderte Film oder Audio Listen anzupassen und neu in der 
		//Tableview anzuzeigen.
		new Thread(new Runnable() {
			public void run() {
				while (true) {

					// Erstellen der ObservableList zum füllen der TableView
					data = FXCollections.observableArrayList();

					if (new File("settings.json").exists() && !(new File("settings.json").isDirectory())) {
						s.readDirectory();

						IMedialist[] scannedContent = MediaStorage.mediaScan(s.getMediaDirectory());
						movies = scannedContent[0];
						audio = scannedContent[1];

						for (IMedia i : movies.getContent().values()) {
							data.add(new Media(i.getFile().getName(), i.getDuration(), i.getDate(), i.getArtist(), i.getGenre()));
						}

						for (IMedia i : audio.getContent().values()) {
							data.add(new Media(i.getFile().getName(), i.getDuration(), i.getDate(), i.getArtist(), i.getGenre()));
						}
					}    

					tableview.setItems(data); 

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


					try {Thread.sleep(60000);} catch (InterruptedException e) {}

				}

			}}).start();
	}

	public void btn_search_clicked() {


		//		filterdData = new FilteredList<>(data, f -> true);
		//
		//
		//		// 2. Set the filter Predicate whenever the filter changes.
		//		tf_search.textProperty().addListener((observable, oldValue, newValue) -> {
		//			filterdData.setPredicate(Media -> {
		//				// If filter text is empty, display all Media.
		//				if (newValue == null || newValue.isEmpty()) {
		//					System.out.println("Filtertext ist empty");
		//					return true;
		//
		//				}
		//
		//				// Compare first name and last name of every person with filter text.
		//				String lowerCaseFilter = newValue.toLowerCase();
		//
		//				if (Media.getTitle().toLowerCase().contains(lowerCaseFilter)) {
		//					System.out.println("false");
		//					return true; // Filter matches titel.
		//				} 
		//				System.out.println("false");
		//				return false; // Does not match.
		//
		//			});
		//		});
		//
		//
		//		// 3. Wrap the FilteredList in a SortedList. 
		//		sortedData = new SortedList<>(filterdData);
		//
		//		// 4. Bind the SortedList comparator to the TableView comparator.
		//		sortedData.comparatorProperty().bind(tableview.comparatorProperty());
		//
		//		// 5. Add sorted (and filtered) data to the table.
		//		tableview.setItems(sortedData);


	}

	@FXML 
	public void btn_settings_clicked() {	

		new SettingWindow().show();			
	}

	//TODO Fehler im Ranking, hat aber schon funktioniert...
	@FXML
	public void btn_save_clicked(ActionEvent event) throws ParseException{

		String save_titel = tf_title.getText();
		String save_year = tf_year.getText();
		String save_artist = tf_artist.getText();
		String save_genre = tf_genre.getText();
		
		//String ranking = radioGroup.getSelectedToggle().toString().substring(radioGroup.getSelectedToggle().toString().length()-2, radioGroup.getSelectedToggle().toString().length()-1);
		
		String ranking = "1";
		
		Toggle favo = favoGroup.getSelectedToggle();

		IMedia media;
				
		play_data = tableview.getSelectionModel().getSelectedItem().getTitle().toString();

		media = Media.getInput(s, play_data, movies, audio);
		
		Media.editMetaInformation(media, save_titel, save_year, save_artist, save_genre, ranking, favo); 
				
		tf_title.setText(null);
		tf_year.setText(null);
		tf_artist.setText(null);
		tf_genre.setText(null);
		radioGroup.selectToggle(null);
		favoGroup.selectToggle(null);

		initialize(null, null);


	}

	@FXML
	public void btn_edit_clicked(ActionEvent event){

		tf_title.setText(tableview.getSelectionModel().getSelectedItem().getTitle());
		tf_year.setText(tableview.getSelectionModel().getSelectedItem().getDate());
		tf_artist.setText(tableview.getSelectionModel().getSelectedItem().getArtist());
		tf_genre.setText(tableview.getSelectionModel().getSelectedItem().getGenre());
		
	}

	@FXML
	public void btn_play_clicked(ActionEvent event){

		play_data = tableview.getSelectionModel().getSelectedItem().getTitle();
		
		Media.playMovie(s, play_data, movies, audio);

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
