////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package com.example.android20;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.android20.model.Album;
import com.example.android20.model.Photo;
import com.example.android20.model.User;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/**
 * @author Ayush Munjial
 * @author Hein Min Thu */
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class PhotoScene extends AppCompatActivity { Context context = this; Album album; Photo photo; int index; ListView personTags;
    ImageView imageView; int curr_pos = -1;
    @Override protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); setContentView(R.layout.photo_viewer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) { getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Bundle bundle = getIntent().getExtras(); if (bundle != null) { String albumName = bundle.getString("Album");
            for (Album a: User.getInstance().getAlbums()) { if (a.getAlbumName().equals(albumName)) { album = a; break; }}

            index = bundle.getInt("Index"); String pathToPhoto = bundle.getString("Photo");

            for (Photo p: album.getPhotos()) { if (p.toString().equals(pathToPhoto)) { photo = p; break; }}
            photo = album.getPhotos().get(index);

            TextView photo_title = findViewById(R.id.photo_title);
            String title = "Android Photos Application > \n🎞️ " + albumName; photo_title.setText(title);
        }

        imageView = findViewById(R.id.select_image); imageView.setImageURI(photo.getPathToPhoto());

        TextView location_text = findViewById(R.id.location_text); location_text.setText(photo.getLocation());

        personTags = findViewById(R.id.personTags);
        personTags.setAdapter(new ArrayAdapter<String>(this, R.layout.lists_textview, photo.getPersonTags()));

        personTags.setOnItemClickListener((parent, view, position, id) -> curr_pos = position);
    }

    //----------------------------------------------------------------------------------------------------------------------------------

    public void prevPhoto(View view) {
    }

    //----------------------------------------------------------------------------------------------------------------------------------

    public void nextPhoto(View view) {
    }

    //----------------------------------------------------------------------------------------------------------------------------------

    public void editLocation(View view) {
    }

    //----------------------------------------------------------------------------------------------------------------------------------

    public void insertPerson(View view) {
    }

    //----------------------------------------------------------------------------------------------------------------------------------

    public void deletePerson(View view) {
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////