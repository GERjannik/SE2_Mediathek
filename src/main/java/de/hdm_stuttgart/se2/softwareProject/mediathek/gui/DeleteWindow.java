package de.hdm_stuttgart.se2.softwareProject.mediathek.gui;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.MediaStorage;
import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.Settings;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedialist;
import de.hdm_stuttgart.se2.softwareProject.mediathek.gui.MOController;

public class DeleteWindow extends Stage{
	
	private static DeleteWindow instance;	
	
	private static Logger log = LogManager.getLogger(DeleteWindow.class);
	
	public DeleteWindow() {	
		
		Settings s = new Settings();
		
		String del_data = "test.mp4";

		
		//Aufbau des Einstellungsfenster
		BorderPane root = new BorderPane();
		Scene DeleteGUI = new Scene(root);
		
		setTitle("Löschen");
		
		VBox v_box = new VBox();
		v_box.setId("v_box");
		HBox h_box = new HBox();
		h_box.setId("h_box");
		h_box.setSpacing(5);
		HBox h_box2 = new HBox();
		h_box2.setId("h_box2");
		h_box2.setSpacing(5);
		
		Button btn_delete_disk = new Button ("Löschen von Festplatte");
		btn_delete_disk.setId("btn_delete_disk");
		Button btn_delete_mediathek = new Button("Löschen aus Mediathek");
		btn_delete_mediathek.setId("btn_delete_mediathek");
		
		Button btn_cancle = new Button ("Zurück zur Mediathek");
		
		Label l_info = new Label();
		l_info.setId("l_info");
		l_info.setPadding(new Insets(5,5,5,5));
		l_info.setText("Bitte wählen Sie ob das Medium von Festplatte "
				+ "\noder nur aus der Mediathek entfernt werden soll.");	
		
		
		h_box.getChildren().addAll(btn_delete_mediathek,btn_delete_disk);
		h_box2.getChildren().addAll(btn_cancle);
		v_box.getChildren().addAll(l_info,h_box,h_box2);
		
		
		root.setCenter(v_box);
		
		setResizable(false);
		setScene(DeleteGUI);
		
		btn_delete_mediathek.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event){	
				
				boolean delete = false;		
				
							
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Löschen von Mediathek");
				alert.setHeaderText("Sie sind dabei ein Medium aus der Mediathek zu löschen.\n"
						+ "Nach erfolgreicher Löschung wird es erst nach einem manuellen Suchlauf wieder hinzugefügt.");
				alert.setContentText("Sind sie sich sicher?");
				
				Optional<ButtonType> result = alert.showAndWait();
				
				if (result.get() == ButtonType.OK){	
					
//					GUIMedia.deleteMedia(s, play_data, movies, audio, delete);
					
					close();
									    
				} else {
				   
				}		
			}
		});
		
		btn_delete_disk.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event){
				
				String del_data = MOController.getInstance().tableview.getSelectionModel().getSelectedItem().getTitle();
				IMedialist[] returns = MediaStorage.mediaScan(s.getMediaDirectory());
				IMedialist movies = returns[0];
				IMedialist audio = returns[1];
				boolean delete = true;

				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Löschen von Festplatte");
				alert.setHeaderText("Sie sind dabei ein Medium von der Festplatte zu löschen.");
				alert.setContentText("Sind sie sich sicher?");

				Optional<ButtonType> result = alert.showAndWait();

				if (result.get() == ButtonType.OK){

					GUIMedia.deleteMedia(s, del_data, movies, audio, delete);

					close();
					
				} else {

				}		
												
			}
		});
		
		btn_cancle.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				close();
				
			}
		});
	}
	
	public static DeleteWindow getInstance() {
		return instance;
	}
	
}
