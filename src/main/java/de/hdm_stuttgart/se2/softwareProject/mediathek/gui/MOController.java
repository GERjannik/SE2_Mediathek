package de.hdm_stuttgart.se2.softwareProject.mediathek.gui;

import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.ResourceBundle;

import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.MediaStorage;
import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.Settings;
import de.hdm_stuttgart.se2.softwareProject.mediathek.driver.App;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedialist;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MOController implements Initializable {
	
	Settings s = new Settings();
	
	@FXML ToggleButton btn_movie;
	@FXML Label playlist;
	@FXML Label list;
	@FXML Button btn_save;
	@FXML Button btn_edit;
	// Table FXML
	@FXML TableView<IMedia> tableview;
	@FXML TableColumn<IMedia, String> col_title;
    @FXML TableColumn<IMedia, String> col_year;
    @FXML TableColumn<IMedia, String> col_genre;
    @FXML TableColumn<IMedia, String> col_director;
    
    
    

    public void initialize(URL location, ResourceBundle resources) {
    	    	
    	//MediathekGUI_Dummy.getInstance().getMovies();
		
		
    	
        col_title.setCellValueFactory(new PropertyValueFactory<IMedia, String>("name"));
        col_genre.setCellValueFactory(new PropertyValueFactory<IMedia, String>("genre"));
        
        final ObservableList<IMedia> table = FXCollections.observableArrayList();
        /* Map<File, IMedia> x = MediathekGUI_Dummy.getInstance().getMovies().getContent();
        table.addAll(x.values());
        for (Entry<File, IMedia> e : MediathekGUI_Dummy.getInstance().getMovies().getContent().entrySet()) {
        	table.add(e.getValue());
        }*/

    }

	
	@FXML 
	public void btn_settings_clicked() {
		
		
		//new SettingWindow().show();
				
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
