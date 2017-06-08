package de.hdm_stuttgart.se2.softwareProject.mediathek.gui;

import java.io.File;
import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.MediaStorage;
import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.Settings;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedialist;
import javafx.event.*;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SettingWindow extends Stage{

	// private static Logger log = LogManager.getLogger(SettingWindow.class);
	public SettingWindow(){

		//Aufbau des Einstellungsfenster
		BorderPane root = new BorderPane();
		Scene SettingGUI = new Scene(root,600,400);

		Settings s = new Settings();

		setTitle("Einstellungen");

		VBox v_path = new VBox();
		v_path.setId("v_path");
		HBox h_path = new HBox();
		h_path.setId("h_path");

		Label l_path = new Label("Bitte geben sie das Verzeichnis in dem sich die Medien befinden ein.");
		l_path.setId("l_path");
		TextField tf_path = new TextField();
		tf_path.setId("tf_path");

		Label l_path_succes = new Label();
		l_path_succes.setId("l_path_succes");

		Label l_path_error = new Label();
		l_path_error.setId("l_path_error");

		Label l_path_warning = new Label();
		l_path_error.setId("l_path_warning");

		/* Überprüfung ob eine settings.json vorhanden ist und wenn ja, wird der dort enthaltene Pfad in das Texfeld eingefügt */
		if (new File("settings.json").exists() && !(new File("settings.json").isDirectory())) {

			s.readDirectory();
			tf_path.setText(s.getMediaDirectory().toString());
		} else {
			tf_path.setPromptText("C:/Pfad/...");
		}

		Button btn_accept = new Button("Bestätigen");
		btn_accept.setId("btn_accept");	
		btn_accept.setDisable(true);


		tf_path.setOnKeyTyped(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				btn_accept.setDisable(false);	
			}
		});

		Button btn_end = new Button("Beenden");
		btn_end.setId("btn_end");
		
		Button btn_scan = new Button("Rescan");
		btn_scan.setId("btn_scan");


		btn_accept.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				File input = new File (tf_path.getText());
				if (input.exists() && input.isDirectory()) {
					s.setDirectory(input.toString());
					s.readDirectory();
					l_path_succes.setText("Pfad wurde gespeichert");
				} else {
					l_path_error.setText("Die Eingabe ist kein gültiges Verzeichnis");
				}		
			}
		});


		btn_end.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				close();
			}
		});
		
		btn_scan.setOnAction(new EventHandler<ActionEvent>() {
			
			class RescanCommand implements Runnable {

				@Override
				public void run() {
					IMedialist[] returns = MediaStorage.mediaScan(s.getMediaDirectory());
					IMedialist movies = returns[0];
					IMedialist audio = returns[1];
				}
				
			}
			@Override
			public void handle(ActionEvent event) {
				new Thread(new RescanCommand()).start();
			}
		});

		v_path.getChildren().addAll(l_path,tf_path,h_path,l_path_succes,l_path_warning,l_path_error);
		h_path.getChildren().addAll(btn_accept,btn_end,btn_scan);

		//root.setTop();
		//root.setLeft();
		root.setCenter(v_path);
		//root.setRight(); 
		//root.setBottom();

		setScene(SettingGUI);

	}
}


