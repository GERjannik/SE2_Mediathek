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

		TableView view = new TableView();
				
		TableColumn titel = new TableColumn("Titel");
		TableColumn large = new TableColumn("Länge");

		view.getColumns().addAll(titel,large);
		
		


		root.setCenter(view);

		primaryStage.setTitle("Mediathek by DJLB");
		primaryStage.setScene(MediaGUI);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
