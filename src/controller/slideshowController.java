package controller;

import java.io.IOException;
import java.util.List;

import app.Photos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.Tag;

/**
 * Controls the screen that DISPLAYS photo in a separate view AND allows user to view photos as a SLIDESHOW
 * 
 * @author Vilina Ong
 * @author Michael Wang
 *
 */
public class slideshowController {
	@FXML Label caption;
	@FXML Label date;
	@FXML Label tags;
	@FXML Pane pane;
	
	/**
     * Album that is currently being accessed
     */
    public static Album currentAlbum;
    
    /**
     * stores the index of the currently displayed photo in the album
     */
    public int index;
	
    /**
     * displayes the photo and all its proper information
     * @param mainStage
     * @param album
     * @param index
     */
	public void start(Stage mainStage, Album album, int index) {
		currentAlbum = album;
		this.index = index;
		List<Photo> photos = currentAlbum.getPhotos();
		
		Image img = new Image(photos.get(index).getName());
		ImageView image = new ImageView(img);
		image.fitWidthProperty().bind(pane.widthProperty());
		image.fitHeightProperty().bind(pane.heightProperty());
		image.setPreserveRatio(true);
		pane.getChildren().add(image);
		caption.setText("Caption: " + photos.get(index).getCaption());
		date.setText(photos.get(index).dateString());
		List<Tag> t = photos.get(index).getTags();
		String tagstring = "Tags- ";
		for(int i = 0; i < t.size(); i++) {
			tagstring += t.get(i).getName() + ": ";
			tagstring += t.get(i).getValue() + ". ";
		}
		tags.setText(tagstring);
		
	}
	
	/**
	 * goes to the previous photo in the album
	 * @param e
	 * @throws IOException
	 */
	//change image and info to the prev image
	public void prev(ActionEvent e) throws IOException {
		int newdex = index;
		
		if(index == 0) {
			newdex = currentAlbum.getPhotos().size()-1;
		} else {
			newdex = index - 1;
		}
		Photos.slideshowscreen(currentAlbum, newdex);
		
	}
	
	/**
	 * goes to the next photo in the album
	 * @param e
	 * @throws IOException
	 */
	//change image and info to the next image
	public void next(ActionEvent e) throws IOException {
		int newdex = index;
		
		if (index == currentAlbum.getPhotos().size()-1) {
			newdex = 0;
		} else {
			newdex = index + 1;
		}
		Photos.slideshowscreen(currentAlbum, newdex);
	}
	
	/**
	 * brings the user back to the view of all the photos in a certain album
	 * @param e
	 * @throws IOException
	 */
	//go back to the photos page
	public void back(ActionEvent e) throws IOException {
		Photos.photoscreen(currentAlbum);
	}
}
