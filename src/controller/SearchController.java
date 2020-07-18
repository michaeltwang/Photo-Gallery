package controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import app.Photos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import model.Admin;
import model.Album;
import model.NonAdmin;
import model.Photo;
import model.Tag;
/**
 * Controls page to search an album for photos by tags or date
 * 
 * @author Vilina Ong
 * @author Michael Wang
 *
 */
public class SearchController {

	@FXML TextField type1;
	@FXML TextField value1;
	@FXML TextField type2;
	@FXML TextField value2;
	@FXML TextField conjunction;
	@FXML TextField startdate;
	@FXML TextField enddate;
	@FXML TilePane tile;
	@FXML ScrollPane scroll;
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
     * Album that is currently being accessed
     */
    public static Album currentAlbum;
    
    /**
     * The current photos in the album
     */
    public List<Photo> photos = new ArrayList<Photo>();
    
    /**
     * A list of the search results from the current search
     */
    public List<Photo> searchResults;
	
    /**
     * Navigates back to the previous page
     * @param e
     * @throws IOException
     */
    //go back to album selection screen
	public void back(ActionEvent e) throws IOException {
		Photos.albumscreen(null);
	}

	/**
	 * starts the screen with all the current photos that have been searched
	 * @param stage
	 */
	public void start(Stage stage) {
		for (Album album: currentUser.getAlbums()) {
			//if (album.getPhotos()!=null && !album.getPhotos().isEmpty()) {
				photos.addAll(album.getPhotos());
			//}
			
		}
		searchResults = new ArrayList<Photo>();
	}
	
	/**
	 * performs the search following the given inputs and displays the results that match
	 * @param e
	 * @throws IOException
	 */
    //performs search and shows result in box
	public void search(ActionEvent e) throws IOException {
		// reset tile pane and search results from previous search
		tile.getChildren().clear();
		searchResults.clear();
		if (!type1.getText().trim().isBlank()) {
			if (value1.getText().trim().isBlank()) {
				Alert alert2 = new Alert(AlertType.WARNING);
				alert2.setTitle("WARNING");
				alert2.setHeaderText("Value1 field missing");
				alert2.setContentText("Fill in Value1, or get rid of Type1 field and try Date Search");
				alert2.showAndWait();
				return;
			}
		Tag tag1 = new Tag(type1.getText().trim(), value1.getText().trim());
		Tag tag2 = new Tag(type2.getText().trim(), value2.getText().trim());
		String conj = conjunction.getText().trim();
		if (!conj.isBlank() && !(conj.equalsIgnoreCase("AND") || conj.equalsIgnoreCase("OR"))) {
			Alert alert2 = new Alert(AlertType.WARNING);
			alert2.setTitle("WARNING");
			alert2.setHeaderText("Invalid conjunction");
			alert2.setContentText("Either type in AND, OR, or leave blank");
			alert2.showAndWait();
			return;
		}
		for (Album album: currentUser.getAlbums()) {
			searchResults.addAll(album.searchByTag(tag1, tag2, conj));
		}
		
		for(int i = 0; i < searchResults.size(); i++) {
			Image img = new Image(searchResults.get(i).getName());
			ImageView imgview = new ImageView(img);
			imgview.setFitHeight(200);
			imgview.setFitWidth(200);
			Button lbl = new Button(searchResults.get(i).getCaption());
			lbl.setWrapText(true);
			lbl.setGraphic(imgview);
			lbl.setContentDisplay(ContentDisplay.TOP);
			tile.getChildren().add(lbl);
			
			}
			scroll.setContent(tile);
		}
		// else perform date search
		else {
			if (startdate.getText().isBlank() || enddate.getText().isBlank()) {
				Alert alert2 = new Alert(AlertType.WARNING);
				alert2.setTitle("WARNING");
				alert2.setHeaderText("Start date and/or end date missing");
				alert2.setContentText("Fill in required fields and try again");
				alert2.showAndWait();
				return;
			}
			Calendar cal1 = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
			try {
				cal1.setTime(sdf.parse(startdate.getText().trim()));
			} catch (ParseException e1) {
				Alert alert2 = new Alert(AlertType.WARNING);
				alert2.setTitle("WARNING");
				alert2.setHeaderText("Start date is not properly formatted");
				alert2.setContentText("Format is EEE MMM dd HH:mm:ss z yyyy");
				alert2.showAndWait();
				e1.printStackTrace();
				return;
			}
			Calendar cal2 = Calendar.getInstance();
			try {
				cal2.setTime(sdf.parse(enddate.getText().trim()));
			} catch (ParseException e1) {
				Alert alert2 = new Alert(AlertType.WARNING);
				alert2.setTitle("WARNING");
				alert2.setHeaderText("Start date is not properly formatted");
				alert2.setContentText("Format is EEE MMM dd HH:mm:ss z yyyy");
				alert2.showAndWait();
				e1.printStackTrace();
				return;
			}
			for (Album album: currentUser.getAlbums()) {
				searchResults.addAll(album.searchByDate(cal1, cal2));
			}
			for(int i = 0; i < searchResults.size(); i++) {
				Image img = new Image(searchResults.get(i).getName());
				ImageView imgview = new ImageView(img);
				imgview.setFitHeight(200);
				imgview.setFitWidth(200);
				Button lbl = new Button(searchResults.get(i).getCaption());
				lbl.setWrapText(true);
				lbl.setGraphic(imgview);
				lbl.setContentDisplay(ContentDisplay.TOP);
				tile.getChildren().add(lbl);
				
			}
			scroll.setContent(tile);
		}
	}
	
	/**
	 * allows the user to make a new album from the search results and brings them to that new album.
	 * @param e
	 * @throws IOException
	 */
    //takes photos and makes an album from it
	public void makealbum(ActionEvent e) throws IOException {
		if (!albumname.getText().isBlank()) {
			String name = albumname.getText().trim();
			Album newAlbum = currentUser.addAlbum(name, searchResults);
			//NonAdmin.write(currentUser);
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
	
}
