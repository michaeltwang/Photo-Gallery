package controller;

import java.io.IOException;

import app.Photos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Admin;
import model.Album;
import model.NonAdmin;
import model.Photo;

/**
 * Controls page to move photo to another album
 * 
 * @author Vilina Ong
 * @author Michael Wang
 *
 */
public class MoveController {
	
	/**
	 * JavaFX item that refers to the listview of all the albums
	 */
	@FXML ListView<Album> listview;
	
	/**
     * Admin / Data for users
     */
    public static Admin currentAdmin = Photos.admin;
    
    /**
     * NonAdmin that is currently logged in
     */
    public static NonAdmin currentUser;
    
    /**
     * Album that is currently being accessed
     */
    public static Album currentAlbum;
    
    /**
     * Photo that is currently being accessed
     */
    public static Photo currentPhoto;
    
    /**
     * starts the display with all the albums of the current user
     * @param mainStage
     * @param album
     * @param index
     */
	public void start(Stage mainStage, Album album, int index) {
		ObservableList<Album> convertedList = FXCollections.observableArrayList(currentUser.getAlbums());
		listview.setItems(convertedList);
		currentAlbum = album;
		currentPhoto = album.getPhotos().get(index);
	}
	
	/**
	 * moves the original photo to the newly selected album and removes the photo from the original album
	 * @param e
	 * @throws IOException
	 */
	//move image to selected album
	public void moveto(ActionEvent e) throws IOException {
		//open this album
				Album album = listview.getSelectionModel().getSelectedItem();
				if (album!=null) {
					currentAlbum.movePhoto(currentPhoto, album);
					Album.write(currentAlbum);
					Alert alert2 = new Alert(AlertType.WARNING);
					alert2.setTitle("Success");
					alert2.setHeaderText("Photo successfully moved");
					alert2.setContentText("You will be returned to photo screen");
					alert2.showAndWait();
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
	
	/**
	 * returns the user back to the view that displays all the photos of a specific album.
	 * @param e
	 * @throws IOException
	 */
	//go back to the photos page
	public void back(ActionEvent e) throws IOException {
		Photos.photoscreen(currentAlbum);
	}
}
