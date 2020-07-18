package model;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

//import javafx.scene.image.Image;
/**
 * Photo class for photos
 * 
 * @author Vilina Ong
 * @author Michael Wang
 *
 */
public class Photo implements Serializable {
	static final long serialVersionUID = 4L;
	public static final String storeDir = "dat";
	public static final String storeFile = "userdata.dat";
	
	/**
	 * Name of the photo
	 */
	private String name;
	
	/**
	 * List of tags
	 */
	private List<Tag> tags;
	
	/**
	 * Caption of the photo
	 */
	private String caption;
	
	/**
	 * Date last modified
	 */
	private Calendar date;
	
	/**
	 * Thumbnail
	 */
	//private Image thumbnail;

    /**
     * @return Caption of the photo
     */
    public String getCaption() {
        return caption;
    }

    /**
     * @param caption Caption of the photo
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }
    
    /**
     * @return Date of the photo
     */
    public Calendar getDate() {
        return date;
    }
    
    /**
     * converts the date of the photo to a string format
     * @return
     */
    
    public String dateString() {
    	SimpleDateFormat formatted = new SimpleDateFormat("yyyy-MM-dd 'T' HH:mm:ss.SSSXXX");
    	return formatted.format(date.getTime());
    }
    
    
    /**
     * Constructor for Photo given file path and file
     * 
     * @param filepath
     * @param file
     */
    public Photo(String filepath, File file) {
    	// set date/time
    	this.date = Calendar.getInstance();
    	this.date.setTimeInMillis(file.lastModified());
    	this.setName(filepath);
        this.caption = "<sample caption>";
        this.tags = new ArrayList<Tag>();
    }
    
    /**
     * Constructor for copying a Photo
     * 
     * @param photo
     */
    public Photo(Photo photo) {
    	this.setName(photo.getName());
    	this.caption = photo.caption;
    	this.date = photo.date;
    	this.tags = photo.tags;
    }
    
    /**
     * Add a new tag to the photo
     * 
     * @param name Name of tag
     * @param value Value of tag
     * @return True if tag was successfully added, False if tag already exists
     */
    public boolean addTag(String name, String value) {
        Tag tag = new Tag(name.trim(), value.trim());
        if (tag.getName().equalsIgnoreCase("location")) {
        	//System.out.println("We made it boiz");
        	for (int i=0; i<tags.size(); i++) {
            	if (tags.get(i).getName().equalsIgnoreCase("location")) {
            		//System.out.println("O O F");
            		return false;
            	}
            }
        	tags.add(tag);
            
            return true;
        }
        for (int i=0; i<tags.size(); i++) {
        	if (tags.get(i).getName().equalsIgnoreCase(tag.getName()) && tags.get(i).getValue().equalsIgnoreCase(tag.getValue())) {
        		return false;
        	}
        }
        tags.add(tag);
        
        return true;
    }

    /**
     * Delete tag from the photo
     * 
     * @param name Name of tag
     * @param value Value of tag
     * @return Tag that was removed, null if tag was not found
     */
    public Tag deleteTag(String name, String value) {
        Tag tag = new Tag(name, value);
        for (int i=0; i<tags.size(); i++) {
        	if (tags.get(i).getName().equalsIgnoreCase(tag.getName()) && tags.get(i).getValue().equalsIgnoreCase(tag.getValue())) {
        		Tag removedTag = tags.remove(i);
        		return removedTag;
        	}
        }
        return null;
    }
    
    /**
     * Searches for a tag in the photo
     * 
     * @param tag Tag to be searched
     * @return boolean True if tag found, false if tag not found
     */
    public boolean searchTag(Tag tag) {
        for (int i=0; i<tags.size(); i++) {
        	if (tags.get(i).getName().equalsIgnoreCase(tag.getName()) && tags.get(i).getValue().equalsIgnoreCase(tag.getValue())) {
        		return true;
        	}
        }
        return false;
    }
    
    /**
     * Get tags
     * 
     * @return List<Tag> list of tags
     */
    public List<Tag> getTags() {
    	return this.tags;
    }
    
    /**
     * Clear tags
     */
    public void clearTags() {
    	this.tags.clear();
    }
    
    /**
     * Set tags
     * 
     * @param List<Tag> list of tags
     */
    public void setTags(List<Tag> tags) {
    	this.tags = tags;
    }
    
	/**
	 * Write state to .dat file
	 * @param app
	 * @throws IOException
	 */
	public static void write(Photo app) throws IOException {
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
	 * return name of the photo file
	 * @return name of photo file
	 */
	public String getName() {
		return name;
	}
	/**
	 * sets the name of the photo file
	 * @param name of new photo file name
	 */
	public void setName(String name) {
		this.name = name;
	}
}
