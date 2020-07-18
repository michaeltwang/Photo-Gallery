package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Admin class, subclass of UserData
 * 
 * @author Vilina Ong
 * @author Michael Wang
 *
 */
public class Admin extends UserData implements User, Serializable {
	
	static final long serialVersionUID = 1L;
	public static final String storeDir = "dat";
	public static final String storeFile = "userdata.dat";
	
	/**
	 * Admin username
	 */
	public String username = "admin";
	
	/**
	 * No-arg constructor
	 */
	public Admin() {
		super();
	}

	/**
	 * String constructor
	 * 
	 * @param username
	 */
	public Admin(String username) {
		this.username = username;
	}
	
	/**
	 * Write state to .dat file
	 * @param app
	 * @throws IOException
	 */
	public static void write(Admin app) throws IOException {
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
	public static Admin read() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
		Admin userData = (Admin) ois.readObject();
		ois.close();
		return userData;
	}
}
