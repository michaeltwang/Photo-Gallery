package model;

import java.io.*;
import java.util.*;

/**
 * NonAdmin class for non-admin users
 * 
 * @author Vilina Ong
 * @author Michael Wang
 *
 */
public class NonAdmin implements User, Serializable  {
	
	static final long serialVersionUID = 3L;
	public static final String storeDir = "dat";
	public static final String storeFile = "userdata.dat";
	
	/**
	 * NonAdmin username
	 */
	private String username;
	
    /**
     * List of albums
     */
    private List<Album> albums;
    
    /**
     * Current Album that this user is interacting with
     */
    private Album currentAlbum;
    
    /**
     * Get username
     * 
     * @return username
     */
    public String getUsername() {
    	return username;
    }
    
    /**
     * Get current album
     * 
     * @return Album current Album
     */
    public Album getCurrentAlbum() {
    	return currentAlbum;
    }
    
    /**
     * Set current album
     * @param album New Album that the user interacts with
     */
    public void setCurrentAlbum(Album album) {
    	this.currentAlbum = album;
    }
	
    /**
     * Constructor for non-admin user
     * 
     * @param username Username of non-admin user
     */
	public NonAdmin(String username) {
		this.username = username;
		this.albums = new ArrayList<Album>();
	}
	
    /**
     * Get albums
     * 
     * @return List<Album> list of albums
     */
    public List<Album> getAlbums() {
    	return albums;
    }
	
    /**
     * Adds an album with a unique name
     * 
     * @param name Name of new album
     * @param photos List of photos
     * @return The new album added, else null
     */
    public Album addAlbum(String name, List<Photo> photos) {
		Album album = new Album(name);
		if (albums.size()>0) {
			for (int i=0; i<albums.size(); i++) {
				if (albums.get(i).getName().equals(name)) {
					return null;
				}
			}
		}
		album.setPhotos(photos);
        albums.add(album);
        return album;
	}
    
    /**
     * Deletes an album based on its unique name
     * 
     * @param name Name of album to be deleted
     * @return Album that was deleted
     */
    public Album deleteAlbum(String name) {
    	for (int i=0; i<albums.size(); i++) {
			if (albums.get(i).getName().equals(name)) {
				Album deletedAlbum = albums.get(i);
				albums.remove(i);
				return deletedAlbum;
			}
		}
    	return null;
    }
    
    /**
     * Finds album given an album
     * 
     * @param album Album to be searched
     * @return Album album found
     */
    public Album findAlbum(Album album) {
    	for (int i=0; i<albums.size(); i++) {
			if (albums.get(i).getName().equals(album.getName())) {
				return albums.get(i);
			}
		}
    	return null;
    }
    
    /**
	 * Write state to .dat file
	 * @param app
	 * @throws IOException
	 */
	public static void write(NonAdmin app) throws IOException {
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
	
	/**
	 * Returns as String
	 */
	public String toString() {
		return username;
	}
	
}
