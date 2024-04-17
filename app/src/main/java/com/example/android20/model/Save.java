////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package com.example.android20.model;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

import java.io.IOException; import java.io.ObjectInputStream; import java.io.ObjectOutputStream; import java.nio.file.Files; import java.nio.file.Paths;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/** This class is responsible to maintain storage in file. It handles the serialization and deserialization of the User object to and
 * from the file. This allows the Photos application to persist user data across sessions by storing it in a file. The class provides
 * static methods for reading from and writing to this file.
 *
 * @author Ayush Munjial
 * @author Hein Min Thu */
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class Save {
    //----------------------------------------------------------------------------------------------------------------------------------
    /** Reads and deserializes the User object from a file. This method is used to retrieve user data stored in a file when application
     * starts or when user data needs to be refreshed.
     * @return User A saved User object deserialized from the file archive.dat.
     * @throws IOException If there is any error in reading from the given file.
     * @throws ClassNotFoundException If the class of a serialized object cannot be found. */
    //----------------------------------------------------------------------------------------------------------------------------------

    public static User readFromFile() throws IOException, ClassNotFoundException { // To read from the archive file.
        ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get("archive.dat")));
        User user = (User)ois.readObject(); ois.close(); return user;
    }

    //----------------------------------------------------------------------------------------------------------------------------------
    /** Serializes and writes the User object to a file. This method is used to save user data to a file, allowing persistence of data
     * across application sessions.
     * @param user User object to be serialized, saved to file archive.dat.
     * @throws IOException If there is any error writing to the given file. */
    //----------------------------------------------------------------------------------------------------------------------------------

    public static void saveToFile(User user) throws IOException, ClassNotFoundException { // To save user to archive.
        ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get("archive.dat"))); oos.writeObject(user); oos.close();
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////