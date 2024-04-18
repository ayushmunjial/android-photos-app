////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package com.example.android20.model;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

import java.io.Serializable; import java.util.ArrayList;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/** This class represents an album in the Photos application. It stores the album name & manages collection of Photo objects associated
 * with the album. This class implements Serializable to enable the object serialization for persistent storage. It includes methods for
 * managing the album's properties and accessing its content.
 *
 * @author Ayush Munjial
 * @author Hein Min Thu */
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class Album implements Serializable { private static final long serialVersionUID = -4143935150417416554L; private String albumName;
    public Album(String albumName) { this.albumName = albumName; } private ArrayList<Photo> photos = new ArrayList<Photo>();

    //----------------------------------------------------------------------------------------------------------------------------------

    /** @return ArrayList<Photo> The list of photos. */ public ArrayList<Photo> getPhotos() { return photos; }

    //----------------------------------------------------------------------------------------------------------------------------------

    /** @param albumName The new name for the album. */ public void setAlbumName(String albumName) { this.albumName = albumName; }

    //----------------------------------------------------------------------------------------------------------------------------------

    /** @return String The name of the selected album. */ public String getAlbumName() { return albumName; }

    //----------------------------------------------------------------------------------------------------------------------------------

    /** @return int The total number of photos in album. */ public int totalItems() { return photos.size(); }

    //----------------------------------------------------------------------------------------------------------------------------------

    /** @return String The representation of Album object. */ @Override public String toString() { return "Name: " + albumName + " | Items: " + totalItems(); }
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////