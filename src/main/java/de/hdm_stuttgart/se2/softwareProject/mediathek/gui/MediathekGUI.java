package de.hdm_stuttgart.se2.softwareProject.mediathek.gui;

import java.util.ArrayList;

import com.sun.jna.NativeLibrary;

import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.MediaStorage;
import de.hdm_stuttgart.se2.softwareProject.mediathek.controller.Settings;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedia;
import de.hdm_stuttgart.se2.softwareProject.mediathek.interfaces.IMedialist;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

public class MediathekGUI extends Application {


	
	public static void main(String[] args) {
				
		launch(args);	
		
	}

	@Override
	public void start(Stage s) throws Exception {

		ObservableList<IMedia> Media = FXCollections.observableArrayList();

		BorderPane layout = new BorderPane();

		TableView<Node> table = new TableView<Node>();
		table.setItems(layout.getChildren());

		TableColumn<Node, String> column = new TableColumn<Node, String>("Test");
		column.setCellValueFactory(new PropertyValueFactory<Node, String>());
		table.getColumns().add(column);

		layout.setCenter(table);

		s.setScene(new Scene(layout));
		s.show();
	}
}
