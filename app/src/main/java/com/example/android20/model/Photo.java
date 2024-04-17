////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package com.example.android20.model;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

import android.content.Context; import android.graphics.Bitmap; import android.graphics.drawable.BitmapDrawable; import android.net.Uri;
import android.widget.ImageView; import java.io.Serializable; import java.util.ArrayList;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/** This class represents a photo in the Photos application. It stores the photo's metadata such as name and associated tags, as well as
 * the image data itself. This class implements Serializable to enable object serialization for persistent storage of photo information.
 *
 * @author Ayush Munjial
 * @author Hein Min Thu */
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class Photo implements Serializable { private static final long serialVersionUID = 6955723612371190680L;
    private String pathToPhoto; public Photo(Uri pathToPhoto) {  this.pathToPhoto = pathToPhoto.toString(); }
    private ArrayList<String> personTags = new ArrayList<String>();  private String location = ""; // Empty initially.

    //----------------------------------------------------------------------------------------------------------------------------------

    /** @return ArrayList<String> The list of person tags. */ public ArrayList<String> getPersonTags() { return personTags; }

    //----------------------------------------------------------------------------------------------------------------------------------

    /** @return Uri The path to the photo. */ public Uri getPathToPhoto() { return Uri.parse(pathToPhoto); }

    //----------------------------------------------------------------------------------------------------------------------------------

    /** @param location The new location for the photo. */ public void setLocation(String location) { this.location = location; }

    //----------------------------------------------------------------------------------------------------------------------------------

    /** @return String The location of the current photo. */ public String getLocation() { return location; }

    //----------------------------------------------------------------------------------------------------------------------------------

    /** @return ImageView A new imageView for the photo. */ public ImageView newImageView(Context c){ ImageView img =  new ImageView(c);
        img.setImageURI(Uri.parse(pathToPhoto)); return img; }

    //----------------------------------------------------------------------------------------------------------------------------------

    /** @return Bitmap The bitmap for photo's ImageView. */ public Bitmap getImageBitmap(Context c){ ImageView img = newImageView(c);
        BitmapDrawable bit = (BitmapDrawable) img.getDrawable(); return bit.getBitmap(); }

    //----------------------------------------------------------------------------------------------------------------------------------

    /** @return String The representation of Photo object. */ @Override public String toString() { return pathToPhoto; }

}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////