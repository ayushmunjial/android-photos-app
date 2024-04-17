////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package com.example.android20; import com.example.android20.model.Album; import com.example.android20.model.Save;
import com.example.android20.model.User;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/**
 * @author Ayush Munjial
 * @author Hein Min Thu */
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public class MainActivity extends AppCompatActivity { Context context = this; ListView albumsView; int curr_pos = -1;
    @Override protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); setContentView(R.layout.home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) { getSupportActionBar().setDisplayShowTitleEnabled(false); }

        File pathToScope = context.getFilesDir(); File data = new File(pathToScope + "/archive.dat");

        try { data.createNewFile(); } catch (IOException e) { Log.i("FILEF", "File exists in memory."); }
        try { Save.readFromFile(); } catch (EOFException e) { Log.i("FILEF", "No saved albums found."); }
        catch (ClassNotFoundException e) { e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); }

        albumsView = findViewById(R.id.albumsView);
        albumsView.setAdapter(new ArrayAdapter<Album>(this, R.layout.album_textview, User.getInstance().getAlbums()));
        albumsView.setOnItemClickListener((parent, view, position, id) -> curr_pos = position);
    }

    //----------------------------------------------------------------------------------------------------------------------------------

    public void createAlbum(View view) { }

    //----------------------------------------------------------------------------------------------------------------------------------

    public void deleteAlbum(View view) { }

    //----------------------------------------------------------------------------------------------------------------------------------

    public void openAlbum(View view) { }

    //----------------------------------------------------------------------------------------------------------------------------------

    public void renameAlbum(View view) { }

    //----------------------------------------------------------------------------------------------------------------------------------

    public void searchPhotos(View view) { }
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////