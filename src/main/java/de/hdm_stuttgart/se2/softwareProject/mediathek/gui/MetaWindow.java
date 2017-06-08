package de.hdm_stuttgart.se2.softwareProject.mediathek.gui;

import java.io.File;

import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.MediaStorage;
import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.Settings;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedialist;
import javafx.event.ActionEvent;
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

public class MetaWindow extends Stage {

	private static MetaWindow instance;

	// private static Logger log = LogManager.getLogger(SettingWindow.class);
	public MetaWindow(){

		//Aufbau des Einstellungsfenster
		BorderPane root = new BorderPane();
		Scene MetaGUI = new Scene(root,600,400);

		Settings s = new Settings();

		setTitle("Metadaten");


	}
}
