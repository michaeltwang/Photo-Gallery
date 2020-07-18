package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import app.Photos;
import model.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * controls photoscreen.fxml with all the photos in a specific album showing
 * 
 * @author Vilina Ong
 * @author Michael
 */
public class PhotosController {
	/**
	 * JavaFX item that refers to the imageview of the photos
	 */
	@FXML ImageView imageView;
	
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
     * JavaFX item that provides a scroll function for the photo display
     */
    @FXML
	public ScrollPane scroll;
    
    /**
     * JavaFX item that contains all the photos
     */
    @FXML
	public TilePane tile;
    
    //@FXML ListView<Photo> listview;
    
    /**
     * stores the index of the last clicked on photo, is -1 if nothing is clicked on.
     */
    public int currentindex = -1;
	
	/**
	 * Setup
	 * @param primaryStage
	 * @param album
	 * @throws FileNotFoundException
	 */
	public void start(Stage primaryStage, Album album) throws FileNotFoundException {

		currentAlbum = album;
		List<Photo> photos = currentAlbum.getPhotos();
		
		
		for(int i = 0; i < photos.size(); i++) {
			Image img = new Image(photos.get(i).getName());
			ImageView imgview = new ImageView(img);
			imgview.setFitHeight(200);
			imgview.setFitWidth(200);
			Button lbl = new Button(photos.get(i).getCaption());
			lbl.setWrapText(true);
			lbl.setGraphic(imgview);
			lbl.setContentDisplay(ContentDisplay.TOP);
			
			int index = i;

			lbl.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					currentindex = index;
					
				}
			     
			});     
			
			tile.getChildren().add(lbl);
			
		}
	
        scroll.setContent(tile);
	}

	/**
	 * Displays photo and Slideshow
	 * @param e
	 * @throws IOException
	 */
	public void viewphoto(ActionEvent e) throws IOException {
		if(currentindex == -1) {
			Alert alert2 = new Alert(AlertType.WARNING);
			alert2.setTitle("Error");
			alert2.setHeaderText("No photo selected.");
			alert2.setContentText("Please click on a photo and try again.");
			alert2.showAndWait();
		} else {
			Photos.slideshowscreen(currentAlbum, currentindex);
	
		}
	}
    
	/**
	 * Adds photo
	 * @param e Event
	 * @throws IOException
	 */
    public void addphoto(ActionEvent e) throws IOException {
		 FileChooser fileChooser = new FileChooser();
		 fileChooser.setTitle("Open Image File");
		 fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files","*.png","*.jpg","*.gif"));
		 File selectedFile = fileChooser.showOpenDialog(new Stage());
		 if (selectedFile != null) {
			 Photo photo = new Photo("file:"+selectedFile.getAbsolutePath(), selectedFile);
			 currentAlbum.addPhoto(photo);
			 Alert alert2 = new Alert(AlertType.WARNING);
				alert2.setTitle("Success");
				alert2.setHeaderText("Photo added.");
				alert2.setContentText("You will remain on this screen.");
				alert2.showAndWait();
				refresh();
			 refresh();
			 Album.write(currentAlbum);
		 }
		 Photos.photoscreen(currentAlbum);
	}
    
    /**
     * returns the user back to the screen that displays all the albums
     * @param e
     * @throws IOException
     */
    //go back to album selection screen
	public void back(ActionEvent e) throws IOException {
		Photos.albumscreen(currentUser);
	}
	
	/**
	 * brings the user to the edit photo screen for a specific photo
	 * @param e
	 * @throws IOException
	 */
	//opens the edit photo screen for a specific photo
	public void editphoto(ActionEvent e) throws IOException {
		if(currentindex == -1) {
			Alert alert2 = new Alert(AlertType.WARNING);
			alert2.setTitle("WARNING");
			alert2.setHeaderText("No photo selected.");
			alert2.setContentText("Please select a photo by clicking on it.");
			alert2.showAndWait();
		} else {
			Photos.editscreen(currentAlbum, currentindex);
		}
	}
	
	/**
	 * deletes the last selected photo
	 * @param e
	 * @throws IOException
	 */
	//removes currently selected photo
	public void deletephoto(ActionEvent e) throws IOException {
		if (currentindex != -1) {
			currentAlbum.deletePhoto(currentAlbum.getPhotos().get(currentindex));
			Album.write(currentAlbum);
			Alert alert2 = new Alert(AlertType.WARNING);
			alert2.setTitle("Success");
			alert2.setHeaderText("Photo deleted.");
			alert2.setContentText("You will remain on this screen.");
			alert2.showAndWait();
			refresh();
		}
		else {
			Alert alert2 = new Alert(AlertType.WARNING);
			alert2.setTitle("WARNING");
			alert2.setHeaderText("No photo selected.");
			alert2.setContentText("Please select a photo by clicking on it.");
			alert2.showAndWait();
		}
	}
	
	/**
	 * brings user to a screen that allows the user to select which album to move the last selected photo to
	 * @param e
	 * @throws IOException
	 */
	//opens moveto screen
	public void moveto(ActionEvent e) throws IOException {
		if(currentindex == -1) {
			Alert alert2 = new Alert(AlertType.WARNING);
			alert2.setTitle("WARNING");
			alert2.setHeaderText("No photo selected.");
			alert2.setContentText("Please select a photo by clicking on it.");
			alert2.showAndWait();
		} else {
			Photos.movetoscreen(currentAlbum, currentindex);
		}
	}
	
	/**
	 * brings user to a screen that allows the user to select which album to copy the last selected photo to
	 * @param e
	 * @throws IOException
	 */
	//removes opens copy to screen
	public void copyto(ActionEvent e) throws IOException {
		if(currentindex == -1) {
			Alert alert2 = new Alert(AlertType.WARNING);
			alert2.setTitle("WARNING");
			alert2.setHeaderText("No photo selected.");
			alert2.setContentText("Please select a photo by clicking on it.");
			alert2.showAndWait();
		} else {
			Photos.copytoscreen(currentAlbum, currentindex);
		}
	}
	
	/**
	 * Refreshes photos
	 */
	public void refresh() {
		List<Photo> photos = currentAlbum.getPhotos();
		
		tile.getChildren().clear();
		
		for(int i = 0; i < photos.size(); i++) {
			Image img = new Image(photos.get(i).getName());
			ImageView imgview = new ImageView(img);
			imgview.setFitHeight(200);
			imgview.setFitWidth(200);
			Button lbl = new Button(photos.get(i).getCaption());
			lbl.setWrapText(true);
			lbl.setGraphic(imgview);
			lbl.setContentDisplay(ContentDisplay.TOP);
			tile.getChildren().add(lbl);
		}
		
        scroll.setContent(tile);

	}
   
}
