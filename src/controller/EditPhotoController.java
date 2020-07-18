package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import app.Photos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Admin;
import model.Album;
import model.NonAdmin;
import model.Photo;
import model.Tag;

/**
 * Controls page for editing photo caption and tags
 * 
 * @author Vilina Ong
 * @author Michael Wang
 *
 */
public class EditPhotoController {
	
	/**
     * Admin / Data for users
     */
    public static Admin currentAdmin = Photos.admin;
    
    /**
     * NonAdmin that is currently logged in
     */
    public static NonAdmin currentUser = (NonAdmin) currentAdmin.getCurrentUser();
    
    /**
     * Album that is currently being accessed
     */
    public static Album currentAlbum;
    
    /**
     * Photo that is currently being accessed
     */
    public static Photo currentPhoto;

    /**
     * JavaFX item that contains the caption of the photo, can be edited
     */
	@FXML TextArea caption;
	
	/**
	 * JavaFX item that contains the tags of the photo, can be edited
	 */
	@FXML TextField tags;
	/**
	 * starts the screen by displaying the photo caption and tag information
	 * @param mainStage
	 * @param album
	 * @param index
	 */
	public void start(Stage mainStage, Album album, int index) {
		//set photo
		currentAlbum = album;
		currentPhoto = album.getPhotos().get(index);
		//display tags delimited by commas
		List<Tag> t = currentPhoto.getTags();
		String tagstring = "";
		for(int i = 0; i < t.size(); i++) {
			tagstring += t.get(i).getName() + ": ";
			tagstring += t.get(i).getValue() + ". ";
		}
		tags.setText(tagstring);
		
		caption.setText(currentPhoto.getCaption());
	}
	
	/** 
	 * brings the user back to the previous screen with all the photos in an album
	 * @param e
	 * @throws IOException
	 */
    //go back to album selection screen
	public void back(ActionEvent e) throws IOException {
		Photos.photoscreen(currentAlbum);
	}
	
	/**
	 * saves all the edited info to the photo 
	 * @param e
	 * @throws IOException
	 */
    //save the picture infor and return to photoscreen
	public void save(ActionEvent e) throws IOException {
		currentPhoto.setCaption(caption.getText()); // edits caption
		// edits tags
		currentPhoto.setTags(new ArrayList<Tag>());
		if(tags.getText().equals("") || tags.getText().equals(" ")) {
			currentPhoto.clearTags();
		} else {
			String str = tags.getText();
			String[] listOfTags = str.split("\\.");
			for (int i = 0; i < listOfTags.length; i++) {
				if (!listOfTags[i].contains(":")) {
					continue;
				}
				String[] splitStr = listOfTags[i].strip().split(":");
				String updatedName = splitStr[0].strip();
				String updatedValue = splitStr[1].strip();
				boolean added = currentPhoto.addTag(updatedName, updatedValue);
				if(!added) {
					Alert alert2 = new Alert(AlertType.WARNING);
					alert2.setTitle("Note");
					alert2.setHeaderText("A tag you tried adding was invalid");
					alert2.setContentText("You cannot have duplicate tags or two locations");
					alert2.showAndWait();
					Photos.photoscreen(currentAlbum);
				}
			}
		}
		Photo.write(currentPhoto);
		Alert alert2 = new Alert(AlertType.WARNING);
		alert2.setTitle("Success");
		alert2.setHeaderText("Photo caption and tags saved");
		alert2.setContentText("You will return to the photo screen.");
		alert2.showAndWait();
		Photos.photoscreen(currentAlbum);
		}
		
	
	
}
