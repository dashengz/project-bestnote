package edu.columbia.jonathan.project_bestnote;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Jonathan on 4/19/15.
 */
public class About extends Fragment {
    View aboutView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        aboutView = inflater.inflate(R.layout.about, container, false);
        TextView logo = (TextView) aboutView.findViewById(R.id.logo);
        TextView about = (TextView) aboutView.findViewById(R.id.about);
        // Font path
        String fontPath = "fonts/indieFlower.ttf";
        // Loading Font Face
        Typeface indieFlower = Typeface.createFromAsset(getActivity().getAssets(), fontPath);
        // Applying font
        logo.setTypeface(indieFlower);
        about.setTypeface(indieFlower);
        return aboutView;
    }
}
