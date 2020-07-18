package model;

import java.io.*;

/**
 * Tag class for tags
 * 
 * @author Vilina Ong
 * @author Michael Wang
 *
 */
public class Tag implements Serializable {
	
	static final long serialVersionUID = 5L;
	public static final String storeDir = "dat";
	public static final String storeFile = "userdata.dat";
	
    /**
     * Tag name
     */
    private String name;

    /**
     * Tag value
     */
    private String value;
    
    /**
     * Gets tag name
     * @return String name of tag
     */
    public String getName() {
    	return this.name;
    }
    
    /**
     * Gets tag value
     * @return String value of tag
     */
    public String getValue() {
    	return this.value;
    }
    
    /**
     * Constructor for Tag
     * 
     * @param tagName Name of tag
     * @param tagValue Value of tag
     */
    public Tag(String name, String value) {
        this.name = name;
        this.value = value;
    }
   
	/**
	 * Write state to .dat file
	 * @param app
	 * @throws IOException
	 */
	public static void write(Tag app) throws IOException {
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
	 * sets the name of the tag to a certain name
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}
}
