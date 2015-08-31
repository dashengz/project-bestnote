package edu.columbia.jonathan.project_bestnote;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseUser;

/**
 * Created by Jonathan on 4/19/15.
 */
public class Profile extends Fragment {
    View profileView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        profileView = inflater.inflate(R.layout.profile, container, false);
        TextView logo = (TextView) profileView.findViewById(R.id.logo);
        TextView greeting = (TextView) profileView.findViewById(R.id.greeting);
        TextView username = (TextView) profileView.findViewById(R.id.username);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            // do stuff with the user
            username.setText(currentUser.getUsername());
        } else {
            // show the signup or login screen
            getActivity().finish();
        }



        // Font path
        String fontPath = "fonts/indieFlower.ttf";
        // Loading Font Face
        Typeface indieFlower = Typeface.createFromAsset(getActivity().getAssets(), fontPath);
        // Applying font
        logo.setTypeface(indieFlower);
        greeting.setTypeface(indieFlower);
        username.setTypeface(indieFlower);

        return profileView;
    }
}
