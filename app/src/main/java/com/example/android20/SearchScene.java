////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package com.example.android20; import com.example.android20.model.Album; import com.example.android20.model.Photo;
import com.example.android20.model.User;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

import android.app.AlertDialog; import android.content.Context; import android.os.Bundle; import android.view.LayoutInflater;
import android.view.View; import android.view.Window; import android.widget.ArrayAdapter; import android.widget.AutoCompleteTextView;
import android.widget.Button; import android.widget.EditText; import android.widget.GridView; import android.widget.ListView;
import android.widget.RadioGroup; import android.widget.TextView; import android.widget.Toast; import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity; import androidx.appcompat.widget.Toolbar; import java.util.ArrayList;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/**
 * @author Ayush Munjial
 * @author Hein Min Thu */
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class SearchScene extends AppCompatActivity { Context context = this; ListView labelsView; GridView photosView; int curr_pos = -1;
    AutoCompleteTextView searchTags; ArrayList<Photo> photos = new ArrayList<Photo>(); ArrayList<String> tags = new ArrayList<String>();
    @Override protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); setContentView(R.layout.search_viewer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) { getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        labelsView = findViewById(R.id.labelsView); photosView = findViewById(R.id.photosView);

        labelsView.setAdapter(new ArrayAdapter<String>(this, R.layout.lists_textview, tags));
        labelsView.setOnItemClickListener((parent, view, position, id) -> curr_pos = position);

        photosView.setAdapter(new ImageAdapter(this, R.layout.imageview_plug, photos));

        searchTags = findViewById(R.id.searchTags); ArrayList<String> allTags = new ArrayList<>();

        for (Album a : User.getInstance().getAlbums()) { for (Photo p : a.getPhotos()) {
                    for (String t : p.getPersonTags()) { if (!allTags.contains(t.toLowerCase())) { allTags.add(t.toLowerCase()); }}
                    if (!p.getLocation().isEmpty()) { if (!allTags.contains(p.getLocation())) { allTags.add(p.getLocation()); }}
            }
        }

        searchTags.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, allTags));
    }

    //----------------------------------------------------------------------------------------------------------------------------------

    public void insertTag(View view) { EditText entry = findViewById(R.id.searchTags);
        AutoCompleteTextView searchTags = findViewById(R.id.searchTags); String label = entry.getText().toString().trim().toLowerCase();

        if (label.isEmpty()) { Toast.makeText(context, "Tag entered is invalid.", Toast.LENGTH_SHORT).show(); return; }

        for (String t: tags) { if (t.equals(label)) { entry.setText("");
            Toast.makeText(context, "This tag already exists.", Toast.LENGTH_SHORT).show(); return; }}

        if (tags.size() <= 1) { tags.add(label); labelsView.invalidateViews(); searchTags.setText("");
            Toast.makeText(context, "Successfully inserted.", Toast.LENGTH_SHORT).show();
        }
        else {Toast.makeText(context, "You have exceeded limit.", Toast.LENGTH_SHORT).show();  }
    }

    //----------------------------------------------------------------------------------------------------------------------------------

    public void deleteTag(View view) { if (curr_pos == -1 || curr_pos >= tags.size()) { return; }
        String label = tags.get(curr_pos); LayoutInflater inflater = LayoutInflater.from(context);
        final View customLayout = inflater.inflate(R.layout.custom_popups, null);

        TextView alertMessage = customLayout.findViewById(R.id.alertMessage); alertMessage.setText("**Remove this label**");
        EditText textEditView = customLayout.findViewById(R.id.textEditView); textEditView.setText("Are you sure to delete?");
        textEditView.setFocusable(false); textEditView.setClickable(true); textEditView.setLongClickable(false);

        Button positiveButton = customLayout.findViewById(R.id.positiveButton); positiveButton.setText("Done");
        Button negativeButton = customLayout.findViewById(R.id.negativeButton); negativeButton.setText("Cancel");

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context); dialogBuilder.setView(customLayout);
        AlertDialog alertDialog = dialogBuilder.create();

        positiveButton.setOnClickListener(v -> { tags.remove(label); labelsView.invalidateViews();
            Toast.makeText(context, "Successfully removed.", Toast.LENGTH_SHORT).show(); alertDialog.dismiss();
        });

        negativeButton.setOnClickListener(v -> alertDialog.dismiss()); alertDialog.show(); Window window = alertDialog.getWindow();
        if (window != null) { window.setBackgroundDrawableResource(R.drawable.al_rounded_background); }
    }

    //----------------------------------------------------------------------------------------------------------------------------------

    public void searchAll(View view) { photos.clear(); RadioGroup radioGroup = findViewById(R.id.radio_group);
        int selectedId = radioGroup.getCheckedRadioButtonId();

        if (tags.isEmpty()) { Toast.makeText(context, "Add tags to search.", Toast.LENGTH_SHORT).show(); }

        if (!(selectedId == R.id.radio_and) && !(selectedId == R.id.radio_or) && !(selectedId == R.id.radio_none)) {
            Toast.makeText(context, "Select AND/OR/NONE.", Toast.LENGTH_SHORT).show();
        }

        if (tags.size() == 1 && selectedId == R.id.radio_none) { String label1 = tags.get(0);
            for (Album a : User.getInstance().getAlbums()) { for (Photo p : a.getPhotos()) {

                    for (String t : p.getPersonTags()) { if (t.equals(label1)) { if (!photos.contains(p)) { photos.add(p); }}}
                    if (p.getLocation().equals(label1)){ if (!photos.contains(p)) { photos.add(p); }}
                }
            }
        }

        else if (tags.size() == 2) { String label1 = tags.get(0); String label2 = tags.get(1);

            if (selectedId == R.id.radio_and) { for (Album a : User.getInstance().getAlbums()) { for (Photo p : a.getPhotos()) {

                        boolean matchesFirstTag = p.getPersonTags().contains(label1) || p.getLocation().equals(label1);
                        boolean matchesSecondTag = p.getPersonTags().contains(label2) || p.getLocation().equals(label2);

                        if (matchesFirstTag && matchesSecondTag) { if (!photos.contains(p)) { photos.add(p); }}
                    }
                }
            }

            else if (selectedId == R.id.radio_or) { for (Album a : User.getInstance().getAlbums()) { for (Photo p : a.getPhotos()) {

                        if (p.getPersonTags().contains(label1) || p.getPersonTags().contains(label2) ||
                                p.getLocation().equals(label1) || p.getLocation().equals(label2)) { if (!photos.contains(p)) { photos.add(p); }
                        }
                    }
                }
            }
        }

        photosView.invalidateViews();
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////