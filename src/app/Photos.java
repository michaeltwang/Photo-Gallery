package app;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import controller.AlbumController;
import controller.CopyController;
import controller.EditPhotoController;
import controller.LoginController;
import controller.MoveController;
import controller.PhotosController;
import controller.SearchController;
import controller.UserController;
import controller.slideshowController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.*;

public class Photos extends Application{
	
	public static Stage stage;
	
	/**
	 * Admin for this application
	 */
	public static Admin admin = new Admin("admin");

	/**
	 * starts the application by opening the login screen
	 */
	//starts application with login screen
	public void start(Stage primaryStage) throws IOException {
		stage = primaryStage;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/LoginScreen.fxml"));

		Pane root = (Pane)loader.load();
		
		LoginController controller = loader.getController();
		controller.start(stage);

		Scene scene = new Scene(root, 931, 511);
		stage.setScene(scene);
		stage.show();
		
		getAdmin();
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
			public void handle(WindowEvent we) {
				try {
					Admin.write(admin);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * launches the program
	 * @param args
	 */
	//launches application
	public static void main(String[] args) {
		try {
			admin = Admin.read();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		launch(args);
	}
	
	/** 
	 * switches to the screen to the view that displays all the users (admin view)
	 * @throws IOException
	 */
	//switches to userlist screen
	public static void userlistscreen() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Photos.class.getResource("/view/userlist.fxml"));
		
		Pane root = (Pane)loader.load();
		UserController controller = loader.getController();
		controller.start(stage);
		
		Scene scene = new Scene (root, 931, 511);
		stage.setScene(scene);
		stage.show();
		
	}
	
	/**
	 * switches the screen to display the log in screen
	 * @throws IOException
	 */
	//switches to loginscreeen
	public static void loginscreen() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Photos.class.getResource("/view/LoginScreen.fxml"));
		
		Parent root = loader.load();
		LoginController controller = loader.getController();
		controller.start(stage);
		
		Scene scene = new Scene (root, 931, 511);
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * switches the screen to display all the albums of a user
	 * @param user is the user whose albums we want to display
	 * @throws IOException
	 */
	//switches to albumscreen
	public static void albumscreen(User user) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Photos.class.getResource("/view/albumlist.fxml"));
		
		Parent root = loader.load();
		AlbumController controller = loader.getController();
		controller.start(stage);
		
		Scene scene = new Scene (root, 931, 511);
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * switches the screen to display all the photos of a certain album. 
	 * @param album is the album that is being displayed
	 * @throws IOException
	 */
	//switches to photoscreen
	public static void photoscreen(Album album) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Photos.class.getResource("/view/photoscreen.fxml"));
		
		Parent root = loader.load();
		PhotosController controller = loader.getController();
		controller.start(stage, album);
		
		Scene scene = new Scene (root, 931, 511);
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * switches the display to the slideshow screen that displays one photo and it's information. 
	 * Can travel to the next and previous photo in this view
	 * @param album is the album the photo is from
	 * @param index is the index of the photo in the album
	 * @throws IOException
	 */
	//go to slideshow screen - change param later
	public static void slideshowscreen(Album album, int index) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Photos.class.getResource("/view/slideshow.fxml"));
		
		Parent root = loader.load();
		slideshowController controller = loader.getController();
		controller.start(stage, album, index);
		
		Scene scene = new Scene (root, 931, 511);
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * switches the screen to a display that will allow the user to edit the information of a photo
	 * @param album is the album the photo is from
	 * @param index is the index number of the photo in the album
	 * @throws IOException
	 */
	//go to edit photo screen - change param need to specific pohto
	public static void editscreen(Album album, int index) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Photos.class.getResource("/view/editphoto.fxml"));
		
		Parent root = loader.load();
		EditPhotoController controller = loader.getController();
		controller.start(stage, album, index);
		
		Scene scene = new Scene (root, 931, 511);
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * switches the screen to a page that allows you to select which album to move the current photo to
	 * @param album is the album the original photo is from
	 * @param index is the index of the photo in the original album
	 * @throws IOException
	 */
	//go to edit photo screen
	public static void movetoscreen(Album album, int index) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Photos.class.getResource("/view/movephoto.fxml"));
		
		Parent root = loader.load();
		MoveController controller = loader.getController();
		controller.start(stage, album, index);
		
		Scene scene = new Scene (root, 931, 511);
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * switches the screen to a page that allows you to select which album to copy the current photo to
	 * @param album is the album the original photo is from
	 * @param index is the index of the photo in the original album
	 * @throws IOException
	 */
	public static void copytoscreen(Album album, int index) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Photos.class.getResource("/view/copyphoto.fxml"));
		
		Parent root = loader.load();
		CopyController controller = loader.getController();
		controller.start(stage, album, index);
		
		Scene scene = new Scene (root, 931, 511);
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * switches to the screen that allows you to perform search functions on the photos of a certain user
	 * @throws IOException
	 */
	//go to edit search screen
	public static void searchscreen() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Photos.class.getResource("/view/searchscreen.fxml"));
		
		Parent root = loader.load();
		SearchController controller = loader.getController();
		controller.start(stage);
		
		Scene scene = new Scene (root, 931, 511);
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * Initializes admin by putting in user "stock"
	 */
	public static void getAdmin() {
		if (admin.getNonAdminUsers().isEmpty()) {
			NonAdmin nonAdminUser = (NonAdmin) admin.addUser("stock");
			Album album1 = nonAdminUser.addAlbum("stock", new ArrayList<Photo>());
			Photo cat_1 = new Photo("file:stockphotos/cat_1.jpg", new File("file:stockphotos/cat_1.jpg"));
			cat_1.addTag("Name", "Max");
			cat_1.setCaption("Max is a scaredy-cat");
			album1.addPhoto(cat_1);
			album1.addPhoto(new Photo("file:stockphotos/cat_2.jpg", new File("file:stockphotos/cat_2.jpg")));
			album1.addPhoto(new Photo("file:stockphotos/cat_3.jpg", new File("file:stockphotos/cat_3.jpg")));
			album1.addPhoto(new Photo("file:stockphotos/dog_1.jpg", new File("file:stockphotos/dog_1.jpg")));
			album1.addPhoto(new Photo("file:stockphotos/dog_2.jpg", new File("file:stockphotos/dog_2.jpg")));
			album1.addPhoto(new Photo("file:stockphotos/dog_3.jpg", new File("file:stockphotos/dog_3.jpg")));
		}
	}
}
