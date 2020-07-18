package controller;

import model.*;

import java.io.IOException;

import app.Photos;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * Controls the log in page
 * 
 * @author Vilina Ong
 * @author Michael Wang
 *
 */
public class LoginController {
	
    /**
     * Username input field
     */
	@FXML TextField loginuser;
	
	 /**
     * Admin / Data for users
     */
    public static Admin currentAdmin = Photos.admin;
    
    /**
     * starts the screen as is.
     * @param mainStage
     */
	public void start(Stage mainStage) {
		
	}

	/**
	 * logs the user in. If the user is the admin, brings them to the user list display. Otherwise it brings them to 
	 * the album view of all that users album. Already contains one stock user with stock photos
	 * @param e
	 * @throws IOException
	 */
	//handles when the log in button is clicked
	public void login(ActionEvent e) throws IOException {
		
    	String username = loginuser.getText().trim();
    	//if user is admin, change to userlist screen
    	if (username.equals("admin")) {
        	Photos.userlistscreen();
    	} else {
    		User user = currentAdmin.findUser(username);
    		//if user is found, log into that user and show the album view for that user specifically
    		if (user!=null) {
    			AlbumController.currentUser = (NonAdmin) user;
    			CopyController.currentUser = (NonAdmin) user;
    			MoveController.currentUser = (NonAdmin) user;
    			Photos.albumscreen(user);
    		}
    		else {
    			Alert alert = new Alert(Alert.AlertType.ERROR);
    			alert.setTitle("Error");
    			alert.setHeaderText("Invalid username");
    			alert.setContentText("Please try again");
    			alert.showAndWait();
    			loginuser.setText("");
    		}
    	}
	}
	
    

}
