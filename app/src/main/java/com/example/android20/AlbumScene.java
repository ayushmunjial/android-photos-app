////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package com.example.android20; import com.example.android20.model.Album; import com.example.android20.model.Photo;
import com.example.android20.model.Save; import com.example.android20.model.User;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

import android.app.AlertDialog; import android.content.Context; import android.content.Intent; import android.os.Bundle; import android.util.Log;
import android.view.LayoutInflater; import android.view.View; import android.view.Window; import android.widget.Button;
import android.widget.EditText; import android.widget.GridView; import android.widget.TextView; import android.widget.Toast;
import androidx.activity.EdgeToEdge; import androidx.activity.result.ActivityResultLauncher; import androidx.appcompat.widget.Toolbar;
import androidx.activity.result.contract.ActivityResultContracts; import androidx.appcompat.app.AppCompatActivity;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/**
 * @author Ayush Munjial
 * @author Hein Min Thu */
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class AlbumScene extends AppCompatActivity { Context context = this; Album album; GridView photosView; int curr_pos = -1;
    private ActivityResultLauncher<String[]> mGetContent;
    @Override protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); setContentView(R.layout.album_viewer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) { getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Bundle bundle = getIntent().getExtras(); if (bundle != null) { String albumName = bundle.getString("Album");
            for (Album a: User.getInstance().getAlbums()) { if (a.getAlbumName().equals(albumName)) { album = a; break; }}

            TextView album_title = findViewById(R.id.album_title);
            String title = "Android Photos Application > \n🎞️ " + albumName; album_title.setText(title);
        }

        photosView = findViewById(R.id.photosView);
        photosView.setAdapter(new ImageAdapter(this, R.layout.imageview_plug, album.getPhotos()));

        photosView.setOnItemClickListener((parent, view, position, id) -> curr_pos = position);

        // Initialize the ActivityResultLauncher
        mGetContent = registerForActivityResult(new ActivityResultContracts.OpenDocument(), uri -> {

            if (uri != null) { getContentResolver().takePersistableUriPermission(uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                Photo newPhoto = new Photo(uri);

                for (Album a: User.getInstance().getAlbums()) {
                    for (Photo p: a.getPhotos()) { if (p.getPathToPhoto().equals(newPhoto.getPathToPhoto())) {
                            Toast.makeText(context, "Photo in other album.", Toast.LENGTH_SHORT).show(); return; }
                    }
                }

                for (Photo p: album.getPhotos()) { if (p.getPathToPhoto().equals(newPhoto.getPathToPhoto())) {
                    Toast.makeText(context, "Photo already exists.", Toast.LENGTH_SHORT).show(); return; }}

                album.getPhotos().add(newPhoto); photosView.invalidateViews();

                try { Save.saveToFile(); } catch (Exception e) { Log.i("FILEF", "Error writing to file."); }

                Toast.makeText(context, "Successfully uploaded.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //----------------------------------------------------------------------------------------------------------------------------------

    public void uploadPhoto(View view) { mGetContent.launch(new String[] {"image/*"}); } // Correct way to pass MIME types


    //----------------------------------------------------------------------------------------------------------------------------------

    public void deletePhoto(View view) { if (curr_pos == -1 || curr_pos >= album.getPhotos().size()) { return; }
        Photo photo = album.getPhotos().get(curr_pos); LayoutInflater inflater = LayoutInflater.from(context);
        final View customLayout = inflater.inflate(R.layout.custom_popups, null);

        TextView alertMessage = customLayout.findViewById(R.id.alertMessage); alertMessage.setText("**Delete this Photo**");
        EditText textEditView = customLayout.findViewById(R.id.textEditView); textEditView.setText("Are you sure to delete?");
        textEditView.setFocusable(false); textEditView.setClickable(true); textEditView.setLongClickable(false);

        Button positiveButton = customLayout.findViewById(R.id.positiveButton); positiveButton.setText("Done");
        Button negativeButton = customLayout.findViewById(R.id.negativeButton); negativeButton.setText("Cancel");

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context); dialogBuilder.setView(customLayout);
        AlertDialog alertDialog = dialogBuilder.create();

        positiveButton.setOnClickListener(v -> {

            album.getPhotos().remove(photo); photosView.invalidateViews();

            try { Save.saveToFile(); } catch (Exception e) { Log.i("FILEF", "Error writing to file."); }

            Toast.makeText(context, "Successfully deleted.", Toast.LENGTH_SHORT).show(); alertDialog.dismiss();
        });

        negativeButton.setOnClickListener(v -> alertDialog.dismiss()); alertDialog.show(); Window window = alertDialog.getWindow();
        if (window != null) { window.setBackgroundDrawableResource(R.drawable.al_rounded_background); }
    }

    //----------------------------------------------------------------------------------------------------------------------------------

    public void openPhoto(View view) { if (curr_pos == -1 || curr_pos >= album.getPhotos().size()) { return; }
        String photo = album.getPhotos().get(curr_pos).toString(); Bundle bundle = new Bundle();

        bundle.putString("Album", album.getAlbumName()); bundle.putString("Photo", photo); bundle.putInt("Index", curr_pos);
        Intent intent = new Intent(this, PhotoScene.class); intent.putExtras(bundle); startActivity(intent);
    }

    //----------------------------------------------------------------------------------------------------------------------------------

    public void movePhoto(View view) { if (curr_pos == -1 || curr_pos >= album.getPhotos().size()) { return; }
        Photo photo = album.getPhotos().get(curr_pos); LayoutInflater inflater = LayoutInflater.from(context);
        final View customLayout = inflater.inflate(R.layout.custom_popups, null);

        TextView alertMessage = customLayout.findViewById(R.id.alertMessage); alertMessage.setText("**Move photo to Album**");
        EditText textEditView = customLayout.findViewById(R.id.textEditView); textEditView.setHint("Enter dest album's name.");

        Button positiveButton = customLayout.findViewById(R.id.positiveButton); positiveButton.setText("Done");
        Button negativeButton = customLayout.findViewById(R.id.negativeButton); negativeButton.setText("Cancel");

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context); dialogBuilder.setView(customLayout);
        AlertDialog alertDialog = dialogBuilder.create();

        positiveButton.setOnClickListener(v -> { String albumName = textEditView.getText().toString().trim().toLowerCase();
            boolean contains = false;

            if (albumName.isEmpty()) { Toast.makeText(context, "Album name is invalid.", Toast.LENGTH_SHORT).show(); return; }

            Album destAlbum = new Album(albumName);
            for (Album a: User.getInstance().getAlbums()) { if (a.getAlbumName().equals(destAlbum.getAlbumName())) { contains = true; }}

            if (!contains) { Toast.makeText(context, "Album name is invalid.", Toast.LENGTH_SHORT).show(); return; }

            for (Album a: User.getInstance().getAlbums()) { if (a.getAlbumName().equals(albumName)) { destAlbum = a; break; }}

            for (Photo p: destAlbum.getPhotos()) { if (p.getPathToPhoto().equals(photo.getPathToPhoto())) {
                Toast.makeText(context, "Photo already exists.", Toast.LENGTH_SHORT).show(); return; }}

            destAlbum.getPhotos().add(photo); album.getPhotos().remove(photo); photosView.invalidateViews();

            try { Save.saveToFile(); } catch (Exception e) { Log.i("FILEF", "Error writing to file."); }

            Toast.makeText(context, "Successfully to move.", Toast.LENGTH_SHORT).show(); alertDialog.dismiss();
        });

        negativeButton.setOnClickListener(v -> alertDialog.dismiss()); alertDialog.show(); Window window = alertDialog.getWindow();
        if (window != null) { window.setBackgroundDrawableResource(R.drawable.al_rounded_background); }
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////