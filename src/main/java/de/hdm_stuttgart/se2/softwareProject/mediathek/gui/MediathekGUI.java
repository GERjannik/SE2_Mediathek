package de.hdm_stuttgart.se2.softwareProject.mediathek.gui;

import javafx.application.Application;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MediathekGUI extends Application {

	@Override
	public void start(Stage primaryStage) {

		BorderPane root = new BorderPane();
		Scene MediaGUI = new Scene(root,600,400);

		ToolBar toolbar = new ToolBar();
		MenuBar menubar = new MenuBar();
		VBox left = new VBox();
		VBox right = new VBox();
		HBox statusbar = new HBox();
		TableView view = new TableView();
		
		Label cover = new Label("Cover");
		Label metadata = new Label("Metadata");
		Label playlist = new Label ("Playlist");
		
		TableColumn titel = new TableColumn("Titel");
		TableColumn large = new TableColumn("Länge");

		view.getColumns().addAll(titel,large);
		
		ObservableList<String> options = 
			    FXCollections.observableArrayList(
			        "Filme",
			        "Musik",
			        "Bücher"
			    );
			final ComboBox comboBox = new ComboBox(options);
		
		right.getChildren().addAll(cover,metadata);
		left.getChildren().add(comboBox);

		

		

		

		
		root.setTop(menubar);
		root.setLeft(left);
		root.setCenter(view);
		root.setRight(right); 
		root.setBottom(statusbar);

		primaryStage.setTitle("Mediathek by DYLB");
		primaryStage.setScene(MediaGUI);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
