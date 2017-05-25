package de.hdm_stuttgart.se2.softwareProject.mediathek.gui;

import java.awt.List;
import java.io.IOException;

import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.MediaStorage;
import de.hdm_stuttgart.se2.softwareProject.mediathek.driver.App;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedialist;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MOController {
	
	
	@FXML ToggleButton btn_movie;
	@FXML Label playlist;
	@FXML Label list;
	@FXML Button btn_save;
	@FXML Button btn_edit;
	// Table FXML
	@FXML TableView<IMedia> table;
	@FXML TableColumn<IMedia, String> col_title;
    @FXML TableColumn<IMedia, String> col_year;
    @FXML TableColumn<IMedia, String> col_genre;
    @FXML TableColumn<IMedia, String> col_director;

	
	@FXML public void btn_settings_clicked() {
		
		new SettingWindow().show();
				
	}

	@FXML
	public void btn_save_clicked(ActionEvent event){
		System.out.println("Saved");
	}
	
	@FXML
	public void btn_edit_clicked(ActionEvent event){
		System.out.println("Edit");
	}
	

	
}
