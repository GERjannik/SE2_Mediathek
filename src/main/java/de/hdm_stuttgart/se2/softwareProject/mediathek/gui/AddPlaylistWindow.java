package de.hdm_stuttgart.se2.softwareProject.mediathek.gui;

import java.io.File;
import java.util.ArrayList;

import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.MediaStorage;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedialist;
import de.hdm_stuttgart.se2.softwareProject.mediathek.lists.ListFactory;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AddPlaylistWindow extends Stage {



	public AddPlaylistWindow() {
		
		ArrayList<IMedialist> allLists = new ArrayList<>();
		//allLists = MediaStorage.loadPlaylists(movies, audio);

		//MediasavePlaylists(ArrayList<IMedialist> allLists)
		HBox root = new HBox();
		Scene addPlaylistGui = new Scene(root);

		setTitle("Playliste hinzufügen");

		Label l_playlist = new Label("Name der Playlist: ");
		TextField tf_playlist = new TextField();

		Button btn_add = new Button("Hinzufügen");


		btn_add.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println(tf_playlist.getText());
				IMedialist playlist = ListFactory.getInstance("video", tf_playlist.getText());
				
				allLists.add(playlist);
				MediaStorage.savePlaylists(allLists);
			}
		});



		root.getChildren().addAll(l_playlist, tf_playlist, btn_add);

		//root.setCenter(v_path);
		//root.setRight(); 
		//root.setBottom();

		setScene(addPlaylistGui);



	}
}
