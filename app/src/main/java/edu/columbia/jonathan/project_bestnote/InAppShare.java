package edu.columbia.jonathan.project_bestnote;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class InAppShare extends ActionBarActivity {

    //This activity is not done yet.

    String objectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_app_share);

        TextView title = (TextView) findViewById(R.id.titleInApp);
        EditText user = (EditText) findViewById(R.id.usernameShare);
        Button button = (Button) findViewById(R.id.button);

        // Font path
        String fontPath = "fonts/indieFlower.ttf";

        // Loading Font Face
        Typeface indieFlower = Typeface.createFromAsset(getAssets(), fontPath);

        // Applying font
        title.setTypeface(indieFlower);
        user.setTypeface(indieFlower);
        button.setTypeface(indieFlower);

        Intent getId = getIntent();
        objectId = getId.getStringExtra(TextNotes.OBJECT_ID);
    }

    public void share(View view) {

    }
}
