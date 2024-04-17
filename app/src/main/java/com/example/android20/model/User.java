////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package com.example.android20.model;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

import java.io.Serializable; import java.util.ArrayList;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/** This class represents the user of the Photos application. It stores user details & manages the collection of albums associated
 * with the user. This class implements Serializable to enable object serialization for persistent storage. It includes methods to
 * retrieve user information and albums associated with the user.
 *
 * @author Ayush Munjial
 * @author Hein Min Thu */
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class User implements Serializable { private static final long serialVersionUID = -6777887675960638788L; private User() {}
    private static User user = new User(); private ArrayList<Album> albums = new ArrayList<Album>();

    //----------------------------------------------------------------------------------------------------------------------------------

    /** @return User The user object designated for the current user. */ public static User getInstance() { return user; }

    //----------------------------------------------------------------------------------------------------------------------------------

    /** @return ArrayList<Album> The list of all albums of user. */ public ArrayList<Album> getAlbums() { return albums; }
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////