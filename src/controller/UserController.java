package controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import app.Photos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.*;

/**
 * Controls screen with all the users listed (admin view)
 * 
 * @author Vilina Ong
 * @author Michael Wang
 *
 */
public class UserController {
	
	@FXML TextField newusername;
	@FXML ListView<NonAdmin> listview;
	
	/**
	 * starts up the display with all the users showing
	 * @param mainStage
	 */
	public void start(Stage mainStage) {
		//puts list into the listview for UI
		nonAdmins = currentAdmin.getNonAdminUsers();
		ObservableList<NonAdmin> convertedList = FXCollections.observableArrayList(nonAdmins);
		listview.setItems(convertedList);
	}
	
	/**
	 * Current admin (in this case, only admin)
	 */
	public static Admin currentAdmin = Photos.admin;
	
	/**
	 * creates a list of all the users who are not the admin
	 */
	public List<NonAdmin> nonAdmins;
	
	/**
	 * adds a new user to the list of users
	 * @param e
	 * @throws IOException
	 */
	//use TextField newusername to create new user from string and add to list of users when add user button is clicked
	public void adduser(ActionEvent e) throws IOException {
		Alert alert2 = new Alert(AlertType.CONFIRMATION);
		alert2.setTitle("WARNING");
		alert2.setHeaderText("NEW USER WILL BE ADDED");
		alert2.setContentText("Are you sure?");
		
		Optional<ButtonType> result = alert2.showAndWait();
		if (result.get() == ButtonType.OK) {
		
			if(newusername.getText().trim().isEmpty()) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("WARNING");
				alert.setHeaderText("Inputs Incomplete");
				alert.setContentText("New username is required.");
				alert.showAndWait();
			}
				
			else if(currentAdmin.addUser(newusername.getText()) != null) {
				ObservableList<NonAdmin> convertedList = FXCollections.observableArrayList(currentAdmin.getNonAdminUsers());
				listview.setItems(convertedList);
				Admin.write(currentAdmin);
				newusername.setText("");
			} 
			else {//duplicate username or "admin"
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("WARNING");
				alert.setHeaderText("Duplicate Username");
				alert.setContentText("This username already exists in the application");
				alert.showAndWait();
			}
				
		} else { return; }
	}
	
	/**
	 * removes the selected use and all its data
	 * @param e
	 * @throws IOException
	 */
	//remove user from list of users when delete user button is clicked
	public void deleteuser(ActionEvent e) throws IOException {
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("WARNING");
			alert.setHeaderText("THIS USER WILL BE DELETED");
			alert.setContentText("Are you sure?");
			
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				
				//warning if there are no users to delete <- need to test this still
				if(currentAdmin.getNonAdminUsers().isEmpty()) {
					Alert alert2 = new Alert(AlertType.WARNING);
					alert2.setTitle("WARNING");
					alert2.setHeaderText("No non-admin users.");
					alert2.setContentText("There is nothing to delete.");
					alert2.showAndWait();
				} else {
					//remove user from list
					NonAdmin deletedUser = listview.getSelectionModel().getSelectedItem();
					if (deletedUser==null) {
						Alert alert1 = new Alert(AlertType.WARNING);
						alert1.setTitle("WARNING");
						alert1.setHeaderText("No user selected");
						alert1.setContentText("Please select a user");
						alert1.showAndWait();
					}
					else if (currentAdmin.deleteUser(deletedUser.getUsername()) != null) {
						ObservableList<NonAdmin> convertedList = FXCollections.observableArrayList(currentAdmin.getNonAdminUsers());
						listview.setItems(convertedList);
						Admin.write(currentAdmin);
					}
					else {
						//username not found or "admin"
						Alert alert1 = new Alert(AlertType.WARNING);
						alert1.setTitle("WARNING");
						alert1.setHeaderText("Username not found");
						alert1.setContentText("This username was not found or is admin");
						alert1.showAndWait();
					}
				}
			} else { return; }	
	}
	
	/**
	 * logs the admin out and returns to the log in screen
	 * @param e
	 * @throws IOException
	 */
	//logs out if clicked
	public void logout(ActionEvent e) throws IOException {
		Photos.loginscreen();
	}

}
