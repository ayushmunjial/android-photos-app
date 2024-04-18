////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package com.example.android20;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.android20.model.Album;
import com.example.android20.model.User;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/**
 * @author Ayush Munjial
 * @author Hein Min Thu */
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class AlbumScene extends AppCompatActivity { Album album; GridView photosView; int curr_pos = -1;
    @Override protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); setContentView(R.layout.album_viewer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) { getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();


        if (bundle != null) { String albumName = bundle.getString("Album");
            for (Album a: User.getInstance().getAlbums()) { if (a.getAlbumName().equals(albumName)) { album = a; break; }}

            TextView album_title = findViewById(R.id.album_title);
            String title = "Android Photos Application > \n🎞️ " + albumName; album_title.setText(title);
        }

        photosView = findViewById(R.id.photosView);
        photosView.setAdapter(new ImageAdapter(this, R.layout.imageview_plug, album.getPhotos()));

        photosView.setOnItemClickListener((parent, view, position, id) -> curr_pos = position);
    }

    //----------------------------------------------------------------------------------------------------------------------------------

    public void uploadPhoto(View view) {
    }

    //----------------------------------------------------------------------------------------------------------------------------------

    public void deletePhoto(View view) {
    }

    //----------------------------------------------------------------------------------------------------------------------------------

    public void openPhoto(View view) {
    }

    //----------------------------------------------------------------------------------------------------------------------------------

    public void movePhoto(View view) {
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////