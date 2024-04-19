////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package com.example.android20; import com.example.android20.model.Photo;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

import android.content.Context; import android.view.View; import android.view.ViewGroup; import android.widget.ArrayAdapter;
import android.widget.GridView; import android.widget.ImageView; import java.util.ArrayList; import java.util.Objects;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/**
 * @author Ayush Munjial
 * @author Hein Min Thu */
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class ImageAdapter extends ArrayAdapter<Photo> { public ImageAdapter(Context context, int resource, ArrayList<Photo> photos) {
    super(context, resource, photos); } // create a new ImageView for each item referenced by the Adapter
    @Override public View getView(int curr_pos, View convertView, ViewGroup parent) { ImageView imageView;

        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(getContext()); imageView.setLayoutParams(new GridView.LayoutParams(235, 235));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP); imageView.setPadding(10, 10, 10, 10);
        }

        else { imageView = (ImageView) convertView; }
        imageView.setImageBitmap(Objects.requireNonNull(getItem(curr_pos)).getImageBitmap(getContext())); return imageView;
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////