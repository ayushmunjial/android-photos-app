////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package com.example.android20; import com.example.android20.model.Album; import com.example.android20.model.Save;
import com.example.android20.model.User;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

import android.app.AlertDialog; import android.content.Context; import android.content.Intent; import android.os.Bundle; import android.util.Log;
import android.view.LayoutInflater; import android.view.View; import android.view.Window; import android.widget.ArrayAdapter;
import android.widget.Button; import android.widget.EditText; import android.widget.ListView; import android.widget.TextView;
import android.widget.Toast; import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity; import androidx.appcompat.widget.Toolbar; import java.io.File; import java.io.IOException;

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
        User.path = data.getAbsolutePath();

        try { data.createNewFile(); } catch (IOException e) { Log.i("FILEF", "File exists in memory."); }
        try { Save.readFromFile(); } catch (Exception e) { Log.i("FILEF", "Error reading the file."); }

        albumsView = findViewById(R.id.albumsView);
        albumsView.setAdapter(new ArrayAdapter<Album>(this, R.layout.lists_textview, User.getInstance().getAlbums()));

        albumsView.setOnItemClickListener((parent, view, position, id) -> curr_pos = position);
    }

    //----------------------------------------------------------------------------------------------------------------------------------

    public void createAlbum(View view) { LayoutInflater inflater = LayoutInflater.from(context);
        final View customLayout = inflater.inflate(R.layout.custom_popups, null);

        TextView alertMessage = customLayout.findViewById(R.id.alertMessage); alertMessage.setText("**Adding a new Album**");
        EditText textEditView = customLayout.findViewById(R.id.textEditView); textEditView.setHint("Enter new album's name.");

        Button positiveButton = customLayout.findViewById(R.id.positiveButton); positiveButton.setText("Save");
        Button negativeButton = customLayout.findViewById(R.id.negativeButton); negativeButton.setText("Cancel");

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context); dialogBuilder.setView(customLayout);
        AlertDialog alertDialog = dialogBuilder.create();

        positiveButton.setOnClickListener(v -> { String albumName = textEditView.getText().toString().trim();

            if (albumName.isEmpty()) { Toast.makeText(context, "Album name is invalid.", Toast.LENGTH_SHORT).show(); return; }
            Album newAlbum = new Album(albumName);

            for (Album a: User.getInstance().getAlbums()) { if (a.getAlbumName().equals(newAlbum.getAlbumName())) {
                    Toast.makeText(context, "Album already exists.", Toast.LENGTH_SHORT).show(); return; }}

            User.getInstance().getAlbums().add(newAlbum); albumsView.invalidateViews();

            try { Save.saveToFile(); } catch (Exception e) { Log.i("FILEF", "Error writing to file."); }

            Toast.makeText(context, "Successfully created.", Toast.LENGTH_SHORT).show(); alertDialog.dismiss();
        });

        negativeButton.setOnClickListener(v -> alertDialog.dismiss()); alertDialog.show(); Window window = alertDialog.getWindow();
        if (window != null) { window.setBackgroundDrawableResource(R.drawable.al_rounded_background); }
    }

    //----------------------------------------------------------------------------------------------------------------------------------

    public void deleteAlbum(View view) { if (curr_pos == -1 || curr_pos >= User.getInstance().getAlbums().size()) { return; }

        Album album = User.getInstance().getAlbums().get(curr_pos); LayoutInflater inflater = LayoutInflater.from(context);
        final View customLayout = inflater.inflate(R.layout.custom_popups, null);

        TextView alertMessage = customLayout.findViewById(R.id.alertMessage); alertMessage.setText("**Delete this Album**");
        EditText textEditView = customLayout.findViewById(R.id.textEditView); textEditView.setText("Are you sure to delete?");
        textEditView.setFocusable(false); textEditView.setClickable(true); textEditView.setLongClickable(false);

        Button positiveButton = customLayout.findViewById(R.id.positiveButton); positiveButton.setText("Done");
        Button negativeButton = customLayout.findViewById(R.id.negativeButton); negativeButton.setText("Cancel");

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context); dialogBuilder.setView(customLayout);
        AlertDialog alertDialog = dialogBuilder.create();

        positiveButton.setOnClickListener(v -> { String albumName = textEditView.getText().toString().trim();

            User.getInstance().getAlbums().remove(album); albumsView.invalidateViews();

            try { Save.saveToFile(); } catch (Exception e) { Log.i("FILEF", "Error writing to file."); }

            Toast.makeText(context, "Successfully deleted.", Toast.LENGTH_SHORT).show(); alertDialog.dismiss();
        });

        negativeButton.setOnClickListener(v -> alertDialog.dismiss()); alertDialog.show(); Window window = alertDialog.getWindow();
        if (window != null) { window.setBackgroundDrawableResource(R.drawable.al_rounded_background); }
    }

    //----------------------------------------------------------------------------------------------------------------------------------

    public void openAlbum(View view) { if (curr_pos == -1 || curr_pos >= User.getInstance().getAlbums().size()) { return; }
        Album album = User.getInstance().getAlbums().get(curr_pos); Bundle bundle = new Bundle();

        bundle.putString("Album", album.getAlbumName()); Intent intent = new Intent(this, AlbumScene.class);
        intent.putExtras(bundle); startActivity(intent);
    }

    //----------------------------------------------------------------------------------------------------------------------------------

    public void renameAlbum(View view) { if (curr_pos == -1 || curr_pos >= User.getInstance().getAlbums().size()) { return; }

        Album album = User.getInstance().getAlbums().get(curr_pos); LayoutInflater inflater = LayoutInflater.from(context);
        final View customLayout = inflater.inflate(R.layout.custom_popups, null);

        TextView alertMessage = customLayout.findViewById(R.id.alertMessage); alertMessage.setText("**Rename this Album**");
        EditText textEditView = customLayout.findViewById(R.id.textEditView); textEditView.setText(album.getAlbumName());
        textEditView.setHint("Enter album's new name.");

        Button positiveButton = customLayout.findViewById(R.id.positiveButton); positiveButton.setText("Save");
        Button negativeButton = customLayout.findViewById(R.id.negativeButton); negativeButton.setText("Cancel");

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context); dialogBuilder.setView(customLayout);
        AlertDialog alertDialog = dialogBuilder.create();

        positiveButton.setOnClickListener(v -> { String albumName = textEditView.getText().toString().trim();

            if (albumName.isEmpty()) { Toast.makeText(context, "Album name is invalid.", Toast.LENGTH_SHORT).show(); return; }

            for (Album a: User.getInstance().getAlbums()) { if (a.getAlbumName().equals(albumName)) {
                Toast.makeText(context, "Album already exists.", Toast.LENGTH_SHORT).show(); return; }}

            album.setAlbumName(albumName); albumsView.invalidateViews();

            try { Save.saveToFile(); } catch (Exception e) { Log.i("FILEF", "Error writing to file."); }

            Toast.makeText(context, "Successfully renamed.", Toast.LENGTH_SHORT).show(); alertDialog.dismiss();
        });

        negativeButton.setOnClickListener(v -> alertDialog.dismiss()); alertDialog.show(); Window window = alertDialog.getWindow();
        if (window != null) { window.setBackgroundDrawableResource(R.drawable.al_rounded_background); }
    }

    //----------------------------------------------------------------------------------------------------------------------------------

    public void searchPhotos(View view) { Intent intent = new Intent(this, SearchScene.class); startActivity(intent); }
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////