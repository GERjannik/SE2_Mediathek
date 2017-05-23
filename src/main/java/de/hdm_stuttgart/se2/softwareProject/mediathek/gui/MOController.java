package de.hdm_stuttgart.se2.softwareProject.mediathek.gui;

import java.awt.List;
import java.io.IOException;

import de.hdm_stuttgart.se2.softwareProject.mediathek.driver.App;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedialist;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MOController {
	
	@FXML
	public ToggleButton btn_movie;
	public Label playlist;
	public TableView<IMedia> table;
	public Label list;
		
	public void handleButtonClick() {
		
		new SettingWindow().show();
	}


}
