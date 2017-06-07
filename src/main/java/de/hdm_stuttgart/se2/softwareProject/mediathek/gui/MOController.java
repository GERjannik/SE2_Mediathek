package de.hdm_stuttgart.se2.softwareProject.mediathek.gui;

import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

import javax.print.DocFlavor.URL;

import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.MediaStorage;
import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.Settings;
import de.hdm_stuttgart.se2.softwareProject.mediathek.driver.App;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedialist;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.stage.Stage;

public class MOController {
	
	
	@FXML
	public ToggleButton btn_movie;
	public Label playlist;
	public Label list;
	
	@FXML
	public TableView<IMedia> tableview;
	@FXML
	public TableColumn<IMedialist, String> name;
	
	
	Settings s = new Settings();
	
		
	public void btn_settings_clicked() {
		
		new SettingWindow().show();
				
	}

	public void tableview_onItem() {
		
		s.getMediaDirectory();
		ObservableList<IMedia> Media = FXCollections.observableArrayList();
		
		tableview.setItems(Media);
	}
}
