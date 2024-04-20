////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package com.example.android20; import com.example.android20.model.Album; import com.example.android20.model.Photo;
import com.example.android20.model.Save; import com.example.android20.model.User;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

import android.app.AlertDialog; import android.content.Context; import android.os.Bundle; import android.util.Log;
import android.view.LayoutInflater; import android.view.View; import android.view.Window;
import android.widget.ArrayAdapter; import android.widget.Button; import android.widget.EditText;
import android.widget.ImageView;import android.widget.ListView; import android.widget.TextView; import android.widget.Toast;
import androidx.activity.EdgeToEdge; import androidx.appcompat.app.AppCompatActivity; import androidx.appcompat.widget.Toolbar;

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

    public boolean onSupportNavigateUp() { getOnBackPressedDispatcher().onBackPressed(); return true; }

    //----------------------------------------------------------------------------------------------------------------------------------

    public void prevPhoto(View view) { index -= 1; if(index == -1) { index = album.getPhotos().size() - 1; }
        photo = album.getPhotos().get(index); curr_pos = -1;

        imageView = findViewById(R.id.select_image); imageView.setImageURI(photo.getPathToPhoto());

        TextView location_text = findViewById(R.id.location_text); location_text.setText(photo.getLocation());

        personTags.setAdapter(new ArrayAdapter<String>(this, R.layout.lists_textview, photo.getPersonTags()));
    }

    //----------------------------------------------------------------------------------------------------------------------------------

    public void nextPhoto(View view) { index += 1; if(index == album.getPhotos().size()) { index = 0; }
        photo = album.getPhotos().get(index); curr_pos = -1;

        imageView = findViewById(R.id.select_image); imageView.setImageURI(photo.getPathToPhoto());

        TextView location_text = findViewById(R.id.location_text); location_text.setText(photo.getLocation());

        personTags.setAdapter(new ArrayAdapter<String>(this, R.layout.lists_textview, photo.getPersonTags()));
    }

    //----------------------------------------------------------------------------------------------------------------------------------

    public void editLocation(View view) { LayoutInflater inflater = LayoutInflater.from(context);
        final View customLayout = inflater.inflate(R.layout.custom_popups, null);

        TextView alertMessage = customLayout.findViewById(R.id.alertMessage); alertMessage.setText("**Change image location**");
        EditText textEditView = customLayout.findViewById(R.id.textEditView); textEditView.setHint("Enter the image's location.");
        textEditView.setText(photo.getLocation());

        Button positiveButton = customLayout.findViewById(R.id.positiveButton); positiveButton.setText("Save");
        Button negativeButton = customLayout.findViewById(R.id.negativeButton); negativeButton.setText("Cancel");

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context); dialogBuilder.setView(customLayout);
        AlertDialog alertDialog = dialogBuilder.create();

        positiveButton.setOnClickListener(v -> { String location = textEditView.getText().toString().trim();

            photo.setLocation(location);
            TextView location_text = findViewById(R.id.location_text); location_text.setText(photo.getLocation());

            try { Save.saveToFile(); } catch (Exception e) { Log.i("FILEF", "Error writing to file."); }

            Toast.makeText(context, "Successfully changed.", Toast.LENGTH_SHORT).show(); alertDialog.dismiss();
        });

        negativeButton.setOnClickListener(v -> alertDialog.dismiss()); alertDialog.show(); Window window = alertDialog.getWindow();
        if (window != null) { window.setBackgroundDrawableResource(R.drawable.al_rounded_background); }
    }

    //----------------------------------------------------------------------------------------------------------------------------------

    public void insertPerson(View view) { LayoutInflater inflater = LayoutInflater.from(context);
        final View customLayout = inflater.inflate(R.layout.custom_popups, null);

        TextView alertMessage = customLayout.findViewById(R.id.alertMessage); alertMessage.setText("**Adding a new person**");
        EditText textEditView = customLayout.findViewById(R.id.textEditView); textEditView.setHint("Enter the person's name.");

        Button positiveButton = customLayout.findViewById(R.id.positiveButton); positiveButton.setText("Save");
        Button negativeButton = customLayout.findViewById(R.id.negativeButton); negativeButton.setText("Cancel");

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context); dialogBuilder.setView(customLayout);
        AlertDialog alertDialog = dialogBuilder.create();

        positiveButton.setOnClickListener(v -> { String person = textEditView.getText().toString().trim();

            if (person.isEmpty()) { Toast.makeText(context, "Person name is invalid.", Toast.LENGTH_SHORT).show(); return; }

            for (String t: photo.getPersonTags()) { if (t.equals(person)) {
                Toast.makeText(context, "Person already exists.", Toast.LENGTH_SHORT).show(); return; }}

            photo.getPersonTags().add(person); personTags.invalidateViews();

            try { Save.saveToFile(); } catch (Exception e) { Log.i("FILEF", "Error writing to file."); }

            Toast.makeText(context, "Successfully inserted.", Toast.LENGTH_SHORT).show(); alertDialog.dismiss();
        });

        negativeButton.setOnClickListener(v -> alertDialog.dismiss()); alertDialog.show(); Window window = alertDialog.getWindow();
        if (window != null) { window.setBackgroundDrawableResource(R.drawable.al_rounded_background); }
    }

    //----------------------------------------------------------------------------------------------------------------------------------

    public void deletePerson(View view) { if (curr_pos == -1 || curr_pos >= photo.getPersonTags().size()) { return; }

        String person = photo.getPersonTags().get(curr_pos); LayoutInflater inflater = LayoutInflater.from(context);
        final View customLayout = inflater.inflate(R.layout.custom_popups, null);

        TextView alertMessage = customLayout.findViewById(R.id.alertMessage); alertMessage.setText("**Remove this person**");
        EditText textEditView = customLayout.findViewById(R.id.textEditView); textEditView.setText("Are you sure to delete?");
        textEditView.setFocusable(false); textEditView.setClickable(true); textEditView.setLongClickable(false);

        Button positiveButton = customLayout.findViewById(R.id.positiveButton); positiveButton.setText("Done");
        Button negativeButton = customLayout.findViewById(R.id.negativeButton); negativeButton.setText("Cancel");

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context); dialogBuilder.setView(customLayout);
        AlertDialog alertDialog = dialogBuilder.create();

        positiveButton.setOnClickListener(v -> {

            photo.getPersonTags().remove(person); personTags.invalidateViews();

            try { Save.saveToFile(); } catch (Exception e) { Log.i("FILEF", "Error writing to file."); }

            Toast.makeText(context, "Successfully removed.", Toast.LENGTH_SHORT).show(); alertDialog.dismiss();
        });

        negativeButton.setOnClickListener(v -> alertDialog.dismiss()); alertDialog.show(); Window window = alertDialog.getWindow();
        if (window != null) { window.setBackgroundDrawableResource(R.drawable.al_rounded_background); }
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////