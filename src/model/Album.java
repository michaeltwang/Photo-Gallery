package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

//import javafx.scene.image.Image;
import model.Photo;

/**
 * Album class
 * 
 * @author Vilina Ong
 * @author Michael Wang
 *
 */
public class Album implements Serializable {
	
	static final long serialVersionUID = 2L;
	public static final String storeDir = "dat";
	public static final String storeFile = "userdata.dat";
	
	/**
	 * index of the photo with the earliest date
	 */
	private int earliest = 0;
	/**
	 * index of the photo with the latest date
	 */
	private int latest = 0;
	
	/**
	 * stores the name of the album
	 */
	private String name;
	
	/**
	 * instantiates and album with the correct name
	 * @param name
	 */
	public Album(String name) {
		this.name = name;
	}
	
	/**
	 * returns the name of the album
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * sets the name of the album to something else
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * contains a list of all the photos in the album
	 */
	private List<Photo> photos = new ArrayList<Photo>();
	
	/**
	 * Get list of photos from this album
	 * 
	 * @return List of photos
	 */
	public List<Photo> getPhotos() {
		return photos;
	}
	
	/**
	 * Get list of captions of photos from this album
	 * 
	 * @return List of captions
	 */
	public List<String> getPhotoCaptions() {
		List<String> captions = new ArrayList<>();
		for (Photo photo: photos) {
			captions.add(photo.getCaption());
		}
		return captions;
	}
	
	/**
	 * updates the date ranges of the album
	 */
	public void updateRange() {
		for(int i = 0; i < photos.size(); i++) {
			if(photos.get(i).getDate().before(photos.get(earliest))){
				earliest = i;
			} 
			if(photos.get(i).getDate().after(photos.get(latest))) {
				latest = i;
			}
		}
	}
	
	/**
	 * changes all the photos in the album to a set of photos
	 * @param photos
	 */
	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
		updateRange();
	}
	
	/**
	 * Returns as String
	 */
	public String toString() {
		if( photos.size() == 0) {
			return name;
		} else {
			return name + ", " + photos.size() + " photos | " + photos.get(earliest).dateString() + " - " + photos.get(latest).dateString();	
		}

	}
	
	/**
	 * Add a photo to the album
	 * 
	 * @param photo to be added
	 */
	public void addPhoto(Photo photo) {
		photos.add(photo);
		updateRange();
	}
	
    /**
     * Delete photo from the album
     * 
     * @param photo Photo to be deleted
     * @return Photo that was removed, null if photo was not found
     */
	public Photo deletePhoto(Photo photo) {
    	for (int i=0; i<photos.size(); i++) {
    		if (photo==photos.get(i)) {
    			Photo removedPhoto = photos.remove(i);
    			updateRange();
    			return removedPhoto;
    		}
    	}
    	return null;
    }
	
	/**
	 * Copy photo to another album
	 * 
	 * @param photo Photo to be copied
	 * @param album Album that the photo is to be copied to
	 */
	public void copyPhoto(Photo photo, Album album) {
		Photo photoCopy = new Photo(photo);
		album.addPhoto(photoCopy);
	}
	
	/**
	 * Move photo to another album
	 * 
	 * @param photo Photo to be moved
	 * @param album Album from which the photo was originally in
	 */
	public void movePhoto(Photo photo, Album album) {
		Photo photoCopy = new Photo(photo);
		album.addPhoto(photoCopy);
		deletePhoto(photo);
	}
	
	/**
	 * Searches all photos between a date range
	 * 
	 * @param startDate Earliest date
	 * @param endDate Latest date
	 * @return List<Photo> List of photos with dates that fall within the range
	 */
	public List<Photo> searchByDate(Calendar startDate, Calendar endDate) {
		List<Photo> output = new ArrayList<>();
		for (int i=0; i<photos.size(); i++) {
    		if (!photos.get(i).getDate().before(startDate) && !photos.get(i).getDate().after(endDate)) {
    			output.add(photos.get(i));
    		}
    	}
    	return output;
	}
	
	/**
	 * Searches all photos with the tags
	 * 
	 * @param tag1 Required tag
	 * @param tag2 Optional tag
	 * @param conjunction AND or OR or null
	 * @return List<Photo> List of photos with dates that fall within the range
	 */
	public List<Photo> searchByTag(Tag tag1, Tag tag2, String conjunction) {
		List<Photo> output = new ArrayList<>();
		if (conjunction==null || tag2==null || tag2.getName().isBlank() || tag2.getValue().isBlank()) {
			for (int i=0; i<photos.size(); i++) {
    			if (photos.get(i).searchTag(tag1)) {
    				output.add(photos.get(i));
    			}
    		}
		}
		else if (conjunction.equalsIgnoreCase("AND")){
			for (int i=0; i<photos.size(); i++) {
    			if (photos.get(i).searchTag(tag1) && photos.get(i).searchTag(tag2)) {
    				output.add(photos.get(i));
    			}
    		}
		}
		else {
			for (int i=0; i<photos.size(); i++) {
    			if (photos.get(i).searchTag(tag1) || photos.get(i).searchTag(tag2)) {
    				output.add(photos.get(i));
    			}
    		}
		}
    	return output;
	}
	
	
	/**
	 * Write state to .dat file
	 * @param app
	 * @throws IOException
	 */
	public static void write(Album app) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
		oos.writeObject(app);
		oos.close();
	}
	
	/**
	 * Read state from .dat file
	 * @return user data
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static User read() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
		User user = (User) ois.readObject();
		ois.close();
		return user;
	}
}
