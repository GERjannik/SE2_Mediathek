package de.hdm_stuttgart.se2.softwareProject.mediathek.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class FXMLController {

	TableView tableview;
	
	@FXML Button btn_save;
	@FXML Button btn_edit;
	
	@FXML
	public void btn_save_clicked(ActionEvent event){
		System.out.println("Saved");
	}
	
	@FXML
	public void btn_edit_clicked(ActionEvent event){
		System.out.println("Edit");
	}
	
	

}
