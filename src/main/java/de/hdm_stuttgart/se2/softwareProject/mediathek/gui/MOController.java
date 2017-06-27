package de.hdm_stuttgart.se2.softwareProject.mediathek.gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;


public class MOController implements Initializable {

	private static Logger log = LogManager.getLogger(MOController.class);

	private static MOController instance;	 

	IMedialist movies, audio;
	Settings s = new Settings();
	File play_data;
	String del_data;
	String ranking;
	HashSet<File> visibility = new HashSet<>();	

	ObservableList<GUIMedia> data;
	FilteredList<GUIMedia> filterdData;
	SortedList<GUIMedia> sortedData;
	IMedialist[] scannedContent;

	@FXML ToggleGroup toggleGroup;
	@FXML ToggleButton tb_movies;
	@FXML ToggleButton tb_audio;
	@FXML ToggleButton tb_books;

	@FXML ToggleGroup favoGroup;
	@FXML ToggleButton tb_favo_yes;
	@FXML ToggleButton tb_favo_no;

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
	@FXML TableColumn<GUIMedia, Image> col_favo = new TableColumn<GUIMedia, Image>("Favorit");
	@FXML TableColumn<GUIMedia, String> col_title = new TableColumn<GUIMedia, String>("Titel");
	@FXML TableColumn<GUIMedia, Long> col_length = new TableColumn<GUIMedia, Long>("Länge");
	@FXML TableColumn<GUIMedia, String> col_date = new TableColumn<GUIMedia, String>("Erscheinung");
	@FXML TableColumn<GUIMedia, String> col_artist  = new TableColumn<GUIMedia, String>("Director");
	@FXML TableColumn<GUIMedia, String> col_genre  = new TableColumn<GUIMedia, String>("Genre");
	@FXML TableColumn<GUIMedia, String> col_ranking = new TableColumn<GUIMedia, String>("Bewertung");

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		readVisibilityJSON();
		if (new File("settings.json").exists() && !(new File("settings.json").isDirectory())) {
			s.readDirectory();
		}
		if (s.getMediaDirectory() == null || !s.getMediaDirectory().isDirectory()) {
			new SettingWindow(visibility).show();
		} 

		// Zuweisung der Spalten, für das passende füllen der TableView
		col_favo.setMinWidth(10);
		col_favo.setCellValueFactory(
				new PropertyValueFactory<GUIMedia, Image>("favo"));
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

		//Abfrage ob Pfad eingetragen ist, wenn ja füllen der ObservableList für die Tableview
		//Thread läuft alle 3 Sekunden geänderte Film oder Audio Listen anzupassen und neu in der 
		//Tableview anzuzeigen.
		Thread rescanThread = new Thread(new Runnable() {
			public void run() {
				HashSet<File> lastVisibleMedia = new HashSet<>();
				while (true) {
					if(tf_search.getText() == null || tf_search.getText().isEmpty()) {
						// Erstellen der ObservableList zum füllen der TableView

						if (new File("settings.json").exists() && !(new File("settings.json").isDirectory())) {
							s.readDirectory();
						}

						if (s.getMediaDirectory() != null && s.getMediaDirectory().isDirectory()) {
							scannedContent = MediaStorage.mediaScan(s.getMediaDirectory());

							//TODO Abfrage aktuell über die Größe, muss noch angepasst werden. Vergleich mit aktuellen Daten muss erstellt werden...
							if (contentEqualsCheck(scannedContent, lastVisibleMedia) == false) {

								lastVisibleMedia.clear();
								for (File f : visibility) {
									lastVisibleMedia.add(f);
								}
								movies = scannedContent[0];
								audio = scannedContent[1];

								data.clear();

								for (IMedia i : movies.getContent().values()) {
									if (visibility == null || !visibility.contains(i.getFile())) {
										data.add(new GUIMedia(i.getFavorite(),i.getTitle(), i.getDuration(), i.getDate(), i.getArtist(), i.getGenre(), i.getFile(), i.getRanking()));
									}
								}

								for (IMedia i : audio.getContent().values()) {
									if (visibility == null || !visibility.contains(i.getFile())) {
										data.add(new GUIMedia(i.getFavorite(),i.getTitle(), i.getDuration(), i.getDate(), i.getArtist(), i.getGenre(), i.getFile(), i.getRanking()));
									}
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
									return true;
								}

							// Compare first name and last name of every person with filter text.
									String lowerCaseFilter = newValue.toLowerCase();
							
								if (Media.getTitle().toLowerCase().contains(lowerCaseFilter)) {
									log.info("Filter matched");
								return true; // Filter matches titel.
								} 
								log.info("Filter do not matched");
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
			}}, "rescanThread");

		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					if (new File("settings.json").exists() && !(new File("settings.json").isDirectory())) {
						s.readDirectory();
					}

					if (s.getMediaDirectory() != null && s.getMediaDirectory().isDirectory()) {
						scannedContent = MediaStorage.mediaScan(s.getMediaDirectory());
						movies = scannedContent[0];
						audio = scannedContent[1];

						for (IMedia i : movies.getContent().values()) {
							if (visibility == null || !visibility.contains(i.getFile())) {
								data.add(new GUIMedia(i.getFavorite(),i.getTitle(), i.getDuration(), i.getDate(), i.getArtist(), i.getGenre(), i.getFile(), i.getRanking()));
							} else {
								log.debug(i.getFile() + " nicht in Mediathek angezeigt, da visible == false");
							}
						}

						for (IMedia i : audio.getContent().values()) {
							if (visibility == null || !visibility.contains(i.getFile())) {
								data.add(new GUIMedia(i.getFavorite(),i.getTitle(), i.getDuration(), i.getDate(), i.getArtist(), i.getGenre(), i.getFile(), i.getRanking()));
							} else {
								log.debug(i.getFile() + " nicht in Mediathek angezeigt, da visible == false");
							}
						}
						tableview.setItems(data);
						break;
					}
				}
				rescanThread.start();
			}
		}, "initialScan").start();
	}

	@FXML 
	public void btn_settings_clicked() throws InterruptedException {	
		new SettingWindow(visibility).show();
	}

	@FXML
	public void btn_save_clicked(ActionEvent event) throws ParseException{

		String save_titel = tf_title.getText();
		String save_year = tf_year.getText();
		String save_artist = tf_artist.getText();
		String save_genre = tf_genre.getText();
		boolean save_favo = false;

		try {
			switch (radioGroup.getSelectedToggle().toString()) {

			case "RadioButton[id=rb_one, styleClass=radio-button]'1'": ranking = "1"; break;
			case "RadioButton[id=rb_two, styleClass=radio-button]'2'": ranking = "2"; break;
			case "RadioButton[id=rb_three, styleClass=radio-button]'3'": ranking = "3"; break;
			case "RadioButton[id=rb_four, styleClass=radio-button]'4'": ranking = "4"; break;
			case "RadioButton[id=rb_five, styleClass=radio-button]'5'": ranking = "5"; break;
			default: ranking = "0"; break;
			}
		} catch (NullPointerException e) {
			ranking = "0";
			log.info("Keine Auswahl bei Bewertung angeklickt"); 
		}

		try {
			String a = favoGroup.getSelectedToggle().toString().substring(favoGroup.getSelectedToggle().toString().length()-3, favoGroup.getSelectedToggle().toString().length()-1);
			if (a.equals("Ja")) {
				save_favo = true;
			} else {
				save_favo = false;
			}
		}catch (NullPointerException e) {
			log.error("Keine Auswahl bei Favoriten angeklickt");
			save_favo = false;
		}

		IMedia media;

		if (tableview.getSelectionModel().isEmpty()){
			l_news.setTextFill(javafx.scene.paint.Color.RED);
			l_news.setText("Stellen Sie sicher, das ein Medium ausgewählt ist");
		} else {
			play_data = tableview.getSelectionModel().getSelectedItem().getFile();

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
	public void btn_cancel_clicked(){

		tableview_mouse_clicked();

	}

	@FXML
	public void tableview_mouse_clicked(){
		
		try {

		try {
			tf_title.setText(tableview.getSelectionModel().getSelectedItem().getTitle());
			tf_year.setText(tableview.getSelectionModel().getSelectedItem().getDate());
			tf_artist.setText(tableview.getSelectionModel().getSelectedItem().getArtist());
			tf_genre.setText(tableview.getSelectionModel().getSelectedItem().getGenre());

			switch (tableview.getSelectionModel().getSelectedItem().getRanking()) {

			case "1": radioGroup.selectToggle(rb_one); break;
			case "2": radioGroup.selectToggle(rb_two); break;
			case "3": radioGroup.selectToggle(rb_three); break;
			case "4": radioGroup.selectToggle(rb_four); break;
			case "5": radioGroup.selectToggle(rb_five); break;
			default: radioGroup.selectToggle(null); break;
			}
		} catch (NullPointerException e) {
			log.debug("Kein Objekt der Tabelle angeklickt");
		}

		if (tableview.getSelectionModel().getSelectedItem().getFavo() == true) {		
			favoGroup.selectToggle(tb_favo_yes);		
		} else if (tableview.getSelectionModel().getSelectedItem().getFavo() == false) {			
			favoGroup.selectToggle(tb_favo_no);			
		}	
		} catch (NullPointerException e) {
			log.error("Tableview ist nicht inizialisiert");
		}
	}

	@FXML
	public void btn_play_clicked(ActionEvent event){

		play_data = tableview.getSelectionModel().getSelectedItem().getFile();

		GUIMedia.playMovie(s, play_data, movies, audio);
	}

	@FXML
	public void btn_del_clicked() {

		try {
			play_data = tableview.getSelectionModel().getSelectedItem().getFile();
			del_data = tableview.getSelectionModel().getSelectedItem().getTitle();

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Löschen");
			alert.setHeaderText(del_data + " aus der Mediathek entfernen oder von Festplatte löschen");

			ButtonType bt_mediathek = new ButtonType("Mediathek");
			ButtonType bt_hdd = new ButtonType("Festplatte");

			ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

			alert.getButtonTypes().setAll(bt_mediathek, bt_hdd, buttonTypeCancel);

			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == bt_mediathek){

				Alert finish = new Alert(AlertType.CONFIRMATION);
				finish.setTitle("Letzte Warnung");
				finish.setHeaderText(del_data + " wird aus Mediathek entfernt.");

				ButtonType bt_okay = new ButtonType("Okay");
				ButtonType bt_cancel = new ButtonType("Cancel", ButtonData.BACK_PREVIOUS);

				finish.getButtonTypes().setAll(bt_okay, bt_cancel);

				Optional<ButtonType> last = finish.showAndWait();

				if (last.get() == bt_okay) {
					visibility.add(play_data);
					writeVisibilityJSON();
					boolean delete = false;
					GUIMedia.deleteMedia(s, play_data, movies, audio, delete);;
					initialize(null, null);
				} 	

			} else if (result.get() == bt_hdd) {

				Alert finish = new Alert(AlertType.CONFIRMATION);
				finish.setTitle("Letzte Warnung");
				finish.setHeaderText(del_data + " wird von Festplatte gelöscht");
				finish.setContentText("Sind sie sich wirklich sicher?");

				ButtonType bt_yes = new ButtonType("Ja");
				ButtonType bt_no = new ButtonType("Nein", ButtonData.BACK_PREVIOUS);

				finish.getButtonTypes().setAll(bt_yes, bt_no);

				Optional<ButtonType> last = finish.showAndWait();

				if (last.get() == bt_yes) {
					boolean delete = true;
					GUIMedia.deleteMedia(s, play_data, movies, audio, delete);
					initialize(null, null);
				} 
			} 

		} catch (NullPointerException e) {
			log.catching(e);
			l_news.setTextFill(javafx.scene.paint.Color.RED);
			l_news.setText("Bitte wählen sie ein zu löschendes Medium aus");	
		}	
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

	public void writeVisibilityJSON() {
		HashMap<Integer, String> map = new HashMap<>();
		int i = 0;
		for (File f : this.visibility) {
			map.put(i++, f.toString());
		}
		JSONObject obj = new JSONObject(map);

		try (PrintWriter writer = new PrintWriter(new File ("visibility.json"))) {
			writer.print(obj.toJSONString());
			log.info("Nicht sichtbare Medien erfolgreich in JSON gespeichert");
			writer.close();
		} catch (FileNotFoundException e) {
			log.info("Speichern sichtbarer Medien in JSON fehlgeschlagen");
			log.catching(e);;
			e.printStackTrace();
		}
	}

	public void readVisibilityJSON() {
		try {
			/* Settings-Datei wird über den Scanner eingelesen und dann Zeilenweise einen String angehängt */
			log.info("Visibility-Datei wird gelesen");
			Scanner input = new Scanner(new File ("visibility.json"));

			StringBuilder jsonIn = new StringBuilder();

			while (input.hasNextLine()) {
				jsonIn.append(input.nextLine());
			}

			// Der String wird über einen JSON Parser geparst und anschließend an ein JSON objekt übergeben
			JSONParser parser = new JSONParser();
			JSONObject root = new JSONObject();
			try {
				root = (JSONObject) parser.parse(jsonIn.toString());
			} catch (Exception e) {
				log.debug("Keine Daten aus visibility.json eingelesen. Ggf. liegt ein Feheler vor");
			}


			for (Object s : root.values()) {
				this.visibility.add(new File(s.toString()));
			}
			input.close();

		} catch (FileNotFoundException e) {
			log.info("visibility.json wurde nicht gefunden. Neue, leere Datei wird erstellt");
			log.catching(e);
			e.printStackTrace();
			try {
				new File("visibility.json").createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private boolean contentEqualsCheck(IMedialist[] scannedContent, HashSet<File> lastVisible) {
		// Prüft ob Dateien im Ordner noch dieselben sind, wie die bereits eingelesenen Dateien 
		Set<File> tempSet = scannedContent[0].getContent().keySet();
		for (File f : movies.getContent().keySet()) {
			if (!tempSet.contains(f)) {
				return false;
			}
		}
		tempSet = scannedContent[1].getContent().keySet();
		for (File f : audio.getContent().keySet()) {
			if (!tempSet.contains(f)) {
				return false;
			}
		}

		// Prüft ob Stand der sichtbaren Medien gleich zum zuletzt angezeigten Stand ist
		if (!lastVisible.equals(this.visibility)) {
			return false;
		}

		return true;
	}
}
