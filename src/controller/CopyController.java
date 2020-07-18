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
 * Controls the page where you copy an image to another album
 * 
 * @author Vilina Ong
 * @author Michael Wang
 *
 */
public class CopyController {
	/**
	 * JavaFX item that refers to the listview to display albums
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
     * starts up the display by showing all the albums of the given user
     * @param mainStage
     * @param album
     * @param index
     */
	public void start(Stage mainStage, Album album, int index) {;
		ObservableList<Album> convertedList = FXCollections.observableArrayList(currentUser.getAlbums());
		listview.setItems(convertedList);
		currentAlbum = album;
		currentPhoto = album.getPhotos().get(index);
	}
	
	/**
	 * copies the photo to the selected album and brings the user to that album page
	 * @param e
	 * @throws IOException
	 */
	//copy image to selected album
	public void copyto(ActionEvent e) throws IOException {
		//open this album
		Album album = listview.getSelectionModel().getSelectedItem();
		if (album!=null) {
			currentAlbum.copyPhoto(currentPhoto, album);
			Album.write(currentAlbum);
			Alert alert2 = new Alert(AlertType.WARNING);
			alert2.setTitle("Success");
			alert2.setHeaderText("Photo successfully copied");
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
	 * brings the user back to the original album page without making any changes
	 * @param e
	 * @throws IOException
	 */
	//go back to the photos page
	public void back(ActionEvent e) throws IOException {
		Photos.photoscreen(currentAlbum);
	}

}
