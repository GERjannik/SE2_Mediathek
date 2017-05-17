package de.hdm_stuttgart.se2.softwareProject.mediathek.gui;

import java.awt.List;

import de.hdm_stuttgart.se2.softwareProject.mediathek.driver.App;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedialist;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;

public class MOController {
	
	@FXML
	public ToggleButton btn_movie;
	public Label playlist;
	public TableView<IMedia> table;
	public Label list;
	
	public void handleButtonClick() {
		
	}


}
