package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * User data that contains data of non-admin users and various functions. Superclass of Admin class. Separate from Admin class to improve extendability of project (e.g. more admins are added).
 *
 * @author Vilina Ong
 * @author Michael Wang
 *
 */
public class UserData implements Serializable{
	
	static final long serialVersionUID = 6L;
	public static final String storeDir = "dat";
	public static final String storeFile = "userdata.dat";
	
    /**
     * Constructor for user data
     */
    public UserData() {
        nonAdmins = new ArrayList<NonAdmin>();
    }
	
	/**
	 * List of non-admin users
	 */
	private List<NonAdmin> nonAdmins;
	
	/**
	 * Current user that is logged in
	 */
	private NonAdmin currentUser;
	
    /**
     * Returns a list of all non-admin users
     * 
     * @return List of non-admin users
     */
    public List<NonAdmin> getNonAdminUsers() {
        return nonAdmins;
    }
    
    /**
     * Get current user
     * 
     * @return User current user
     */
    public NonAdmin getCurrentUser() {
    	return currentUser;
    }
    
    /**
     * Set current user
     * @param user New User that is logged in
     */
    public void setCurrentUser(NonAdmin user) {
    	this.currentUser = user;
    }
    
    /**
     * Searches a non-admin user in the list given an input username
     * 
     * @param username Username to be searched
     * @return NonAdmin
     */
    public NonAdmin findUser(String username) {
    	for (int i=0; i<nonAdmins.size(); i++) { 
    		if (nonAdmins.get(i).getUsername().equals(username)) {
    			return nonAdmins.get(i);
    		}
    	}
    	return null;
    }
    
    /**
     * Adds a new non-admin user
     * 
     * @param username Username of user to be added, null if username already exists or is "admin"
     */
    public User addUser(String username) {
    	if (username.equals("admin")) {
    		return null;
    	}
        NonAdmin user = new NonAdmin(username);
        for (int i=0; i<nonAdmins.size(); i++) { 
    		if (nonAdmins.get(i).getUsername().equals(username)) {
    			return null;
    		}
    	}
        nonAdmins.add(user);
    	return user;
    }
    
    /**
     * Deletes a non-admin user
     * 
     * @param username Username of user to be deleted, null if username not found or is "admin"
     */
    public User deleteUser(String username) {
    	if (username.equals("admin")) {
    		return null;
    	}
        for (int i=0; i<nonAdmins.size(); i++) { 
    		if (nonAdmins.get(i).getUsername().equals(username)) {
    			return nonAdmins.remove(i);
    		}
    	}
        return null;
    }
    
	/**
	 * Write state to .dat file
	 * @param app
	 * @throws IOException
	 */
	public static void write(UserData app) throws IOException {
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
	public static UserData read() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
		UserData userData = (UserData) ois.readObject();
		ois.close();
		return userData;
	}
}
