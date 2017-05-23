package de.hdm_stuttgart.se2.softwareProject.mediathek.gui;

import java.io.File;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.Settings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SettingWindow extends Stage{
	
	private static Logger log = LogManager.getLogger(SettingWindow.class);
	
	public SettingWindow(){
		
				
		BorderPane root = new BorderPane();
		Scene SettingGUI = new Scene(root,600,400);
		
		setTitle("Einstellungen");

		VBox v_path = new VBox();
		v_path.setId("v_path");
		HBox h_path = new HBox();
		h_path.setId("h_path");
		
		Label l_path = new Label("Pfadeingabe:");
		l_path.setId("l_path");
		TextField tf_path = new TextField();
		tf_path.setId("tf_path");
		tf_path.setPromptText("c:/pfad/...");
		
		Button btn_accept = new Button("Bestätigen");
		btn_accept.setId("btn_accept");
		Button btn_cancel = new Button("Abbruch");
		btn_cancel.setId("btn_cancel");
		
		Label l_path_error = new Label();
		l_path_error.setId("l_path_error");
		
		
		btn_accept.setOnAction(new EventHandler<ActionEvent>() {
			
			Settings s = new Settings();
			
			@Override
			public void handle(ActionEvent event) {
				if ((tf_path.getText() != null && !tf_path.getText().isEmpty())) {
					
					Scanner scan = new Scanner(System.in);
					File input = new File (scan.nextLine());
					if (input.exists() && input.isDirectory()) {
						s.setDirectory(input.toString());
					} else {
						log.debug("Kein gültiges Verzeichnis angegeben");
						System.out.println("Die Eingabe ist kein gültiges Verzeichnis");
					}
					scan.close();
		        } else {
		        	l_path_error.setText("Sorry falsche Eingabe");
		        }
				
			}
			
		});
		
		
		
		
		
		v_path.getChildren().addAll(l_path,tf_path,h_path,l_path_error);
		h_path.getChildren().addAll(btn_accept,btn_cancel);
		
		//root.setTop();
		//root.setLeft();
		root.setCenter(v_path);
		//root.setRight(); 
		//root.setBottom();

		setScene(SettingGUI);
		
	}
	
}
	

