package de.hdm_stuttgart.se2.softwareProject.mediathek.gui;
/*
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MediathekGUI extends Application {

	@Override
	public void start(Stage primaryStage) {

		BorderPane root = new BorderPane();
		Scene MediaGUI = new Scene(root,600,400);

		TableView view = new TableView();
				
		TableColumn titel = new TableColumn("Titel");
		

		
		
		ObservableList<Person> Olist = FXCollections.observableList(
				new Person ("test1"),
				new Person ("test2")
				);

		titel.setCellValueFactory(new PropertyValueFactory<String, String>("title"));
		view.setItems(Olist);
		view.getColumns().addAll(titel);
		root.setCenter(view);

		primaryStage.setTitle("Mediathek by DJLB");
		primaryStage.setScene(MediaGUI);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public static class Person {
		 
        private final SimpleStringProperty firstName;
        private final SimpleStringProperty lastName;
        private final SimpleStringProperty email;
 
        private Person(String fName, String lName, String email) {
            this.firstName = new SimpleStringProperty(fName);
            this.lastName = new SimpleStringProperty(lName);
            this.email = new SimpleStringProperty(email);
        }
 
        public String getFirstName() {
            return firstName.get();
        }
 
        public void setFirstName(String fName) {
            firstName.set(fName);
        }
 
        public String getLastName() {
            return lastName.get();
        }
 
        public void setLastName(String fName) {
            lastName.set(fName);
        }
 
        public String getEmail() {
            return email.get();
        }
 
        public void setEmail(String fName) {
            email.set(fName);
        }
    }
}
*/



import java.io.File;
import java.util.Scanner;

import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.MediaStorage;
import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.Settings;
import de.hdm_stuttgart.se2.softwareProject.mediathek.gui.MediathekGUI.Media;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedialist;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
 
public class MediathekGUI extends Application {
 
	IMedialist movies, audio;
	Settings s = new Settings();
	
    private TableView<Media> table = new TableView<Media>();
    
   
    public static void main(String[] args) {
        launch(args);
    }
 
    @Override
    public void start(Stage stage) {
    	
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
					System.out.println("Die Eingabe ist kein gültiges Verzeichnis");
				}
			}
			s.readDirectory();
		}
    	
    	IMedialist[] scannedContent = MediaStorage.mediaScan(s.getMediaDirectory());
		movies = scannedContent[0];
		audio = scannedContent[1];
		
        Scene scene = new Scene(new Group());
        stage.setTitle("Mediathek");
        stage.setWidth(600);
        stage.setHeight(500);
 
        final Label label = new Label("Medien Liste");
        label.setFont(new Font("Arial", 20));
 
        table.setEditable(true);
 
        TableColumn<Media, String> title_col = new TableColumn<>("Titel");
        title_col.setMinWidth(100);
        title_col.setCellValueFactory(
                new PropertyValueFactory<Media, String>("title"));
        
        TableColumn<Media, String> length_col = new TableColumn<Media, String>("Länge");
        length_col.setMinWidth(100);
        length_col.setCellValueFactory(
                new PropertyValueFactory<Media, String>("length"));
        

        TableColumn<Media, String> date_col = new TableColumn<Media, String>("Erscheinung");
        date_col.setMinWidth(100);
        date_col.setCellValueFactory(
                new PropertyValueFactory<Media, String>("date"));
        

        TableColumn<Media, String> artist_col = new TableColumn<Media, String>("Director");
        artist_col.setMinWidth(100);
        artist_col.setCellValueFactory(
                new PropertyValueFactory<Media, String>("artist"));
        

        TableColumn<Media, String> genre_col = new TableColumn<Media, String>("Genre");
        genre_col.setMinWidth(100);
        genre_col.setCellValueFactory(
                new PropertyValueFactory<Media, String>("genre"));
        
        ObservableList<Media> data = FXCollections.observableArrayList();
        for (IMedia i : movies.getContent().values()) {
        	data.add(new Media(i.getTitle(), i.getDuration(), i.getDate(), i.getArtist(), i.getGenre()));
        }
 
        table.setItems(data);
        table.getColumns().addAll(title_col, length_col, date_col, artist_col, genre_col);
 
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
 
        stage.setScene(scene);
        stage.show();
    }
 
    public static class Media {
 
        private final SimpleStringProperty title;
        private final SimpleStringProperty length;
        private final SimpleStringProperty date;
        private final SimpleStringProperty artist;
        private final SimpleStringProperty genre;
 
        private Media(String title, Long length, String date, String artist, String genre) {
            this.title = new SimpleStringProperty(title);
            this.length = new SimpleStringProperty(length.toString());
            this.date = new SimpleStringProperty(date);
            this.artist = new SimpleStringProperty(artist);
            this.genre = new SimpleStringProperty(genre);
        }

		public String getTitle() {
			return title.get();
		}

		public String getLength() {
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
} 