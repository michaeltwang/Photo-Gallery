package controller;


import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import model.*;

import java.io.IOException;
import java.util.ArrayList;

import app.Photos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


/**
 * Controls the page where the list of albums is showing
 * 
 * @author Vilina Ong
 * @author Michael Wang
 *
 */
public class AlbumController {
	/**
	 * JavaFX item that allows for input of a new album name
	 */
	@FXML TextField albumname;
	
	/**
     * Admin / Data for users
     */
    public static Admin currentAdmin = Photos.admin;
    
    /**
     * NonAdmin that is currently logged in
     */
    public static NonAdmin currentUser;
	
    /**
     * JavaFX item that displays a listview
     */
	@FXML
	public ListView<Album> listview;
	
	/** 
	 * starts the display with all the albums of the current user displayed
	 * @param mainStage
	 */
	public void start(Stage mainStage) {
		//puts list into the listview for UI
		ObservableList<Album> convertedList = FXCollections.observableArrayList(currentUser.getAlbums());
		listview.setItems(convertedList);
	}
	
	/**
	 * creates a new album using the albumname
	 * @param e
	 * @throws IOException
	 */
	//add String in textfield albumname to the list of albums
	public void createalbum(ActionEvent e) throws IOException {
		if (!albumname.getText().isBlank()) {
			String name = albumname.getText();
			Album newAlbum = currentUser.addAlbum(name, new ArrayList<Photo>());
			if (newAlbum==null) {
				Alert alert2 = new Alert(AlertType.WARNING);
				alert2.setTitle("WARNING");
				alert2.setHeaderText("Album name already exists.");
				alert2.setContentText("You will need to think of a different album name.");
				alert2.showAndWait();
				return;
			}
			NonAdmin.write(currentUser);
			Alert alert2 = new Alert(AlertType.WARNING);
			alert2.setTitle("Success");
			alert2.setHeaderText("Album successfully made.");
			alert2.setContentText("You will be moved to the photo screen of your new album.");
			alert2.showAndWait();
			PhotosController.currentUser = currentUser;
			Photos.photoscreen(newAlbum);
		}
		else {
			Alert alert2 = new Alert(AlertType.WARNING);
			alert2.setTitle("WARNING");
			alert2.setHeaderText("No text entered.");
			alert2.setContentText("Please enter text in the field to the left.");
			alert2.showAndWait();
		}
	}
	
	/**
	 * Renames an existing album
	 * 
	 * @param e
	 * @throws IOException
	 */
	public void renamealbum(ActionEvent e) throws IOException {
		if(currentUser.getAlbums().isEmpty()) {
			Alert alert2 = new Alert(AlertType.WARNING);
			alert2.setTitle("WARNING");
			alert2.setHeaderText("No albums.");
			alert2.setContentText("There is nothing to rename.");
			alert2.showAndWait();
		} else {
			//rename this album
			Album album = listview.getSelectionModel().getSelectedItem();
			if (album!=null) {
				if (!albumname.getText().isBlank()) {
					currentUser.findAlbum(album).setName(albumname.getText());
					NonAdmin.write(currentUser);
					Photos.albumscreen(currentUser);
				}
				else {
					Alert alert2 = new Alert(AlertType.WARNING);
					alert2.setTitle("Want to rename this album?");
					alert2.setHeaderText("Make sure to fill in the text field");
					alert2.setContentText("Type the new name in the field, then click this button again.");
					alert2.showAndWait();
				}
			}
			else {
				Alert alert2 = new Alert(AlertType.WARNING);
				alert2.setTitle("WARNING");
				alert2.setHeaderText("Before you can click this button, do two things:");
				alert2.setContentText("1. Select the album.  2. Type the new name in the field to the left.");
				alert2.showAndWait();
			}
		}		
	}
	
	/**
	 * deletes the current selected album, if any.
	 * @param e
	 * @throws IOException
	 */
	public void deletealbum(ActionEvent e) throws IOException {
		//warning if there are no albums to select <- need to test this still
				if(currentUser.getAlbums().isEmpty()) {
					Alert alert2 = new Alert(AlertType.WARNING);
					alert2.setTitle("WARNING");
					alert2.setHeaderText("No albums.");
					alert2.setContentText("There is nothing to delete.");
					alert2.showAndWait();
				} else {
					//delete this album
					Album album = listview.getSelectionModel().getSelectedItem();
					if (album!=null) {
						currentUser.deleteAlbum(album.getName());
						NonAdmin.write(currentUser);
						Photos.albumscreen(currentUser);
					}
					else {
						Alert alert2 = new Alert(AlertType.WARNING);
						alert2.setTitle("WARNING");
						alert2.setHeaderText("No album selected.");
						alert2.setContentText("Please select an album by clicking on it.");
						alert2.showAndWait();
					}
				}
	}
	
	/**
	 * opens the currently selected album, if any.
	 * @param e
	 * @throws IOException
	 */
	//open currently selected album. albumlist.fxml -> photoscreen.fxml
	public void openalbum(ActionEvent e) throws IOException {
		//warning if there are no albums to select <- need to test this still
		if(currentUser.getAlbums().isEmpty()) {
			Alert alert2 = new Alert(AlertType.WARNING);
			alert2.setTitle("WARNING");
			alert2.setHeaderText("No albums.");
			alert2.setContentText("There is nothing to open.");
			alert2.showAndWait();
		} else {
			//open this album
			Album album = listview.getSelectionModel().getSelectedItem();
			if (album!=null) {
				PhotosController.currentUser = currentUser;
				PhotosController.currentAlbum = album;
				Photos.photoscreen(album);
			}
			else {
				Alert alert2 = new Alert(AlertType.WARNING);
				alert2.setTitle("WARNING");
				alert2.setHeaderText("No album selected.");
				alert2.setContentText("Please select an album by clicking on it.");
				alert2.showAndWait();
			}
		}
	}
	
	/**
	 * brings the user to the search screen
	 * @param e
	 * @throws IOException
	 */
	//goes to search screen
	public void searchscreen(ActionEvent e) throws IOException {
				SearchController.currentUser = currentUser;
				Photos.searchscreen();

	}

	/** 
	 * logs the user out and returns to the log in screen
	 * @param e
	 * @throws IOException
	 */
	//returns to loginscreen
	public void logout(ActionEvent e) throws IOException {
		currentAdmin.setCurrentUser(null);
		Photos.loginscreen();
	}


}
