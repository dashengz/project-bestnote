package edu.columbia.jonathan.project_bestnote;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


public class EditExisting extends ActionBarActivity {

    String objectId;
    EditText titleEdit;
    EditText noteEdit;
    Intent getEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_existing);

        titleEdit = (EditText) findViewById(R.id.titleEdit);
        noteEdit = (EditText) findViewById(R.id.noteEdit);

        // Font path
        String fontPath = "fonts/indieFlower.ttf";

        // text view label
        TextView textView2 = (TextView) findViewById(R.id.textView2);

        // Loading Font Face
        Typeface indieFlower = Typeface.createFromAsset(getAssets(), fontPath);

        // Applying font
        textView2.setTypeface(indieFlower);
        titleEdit.setTypeface(indieFlower);
        noteEdit.setTypeface(indieFlower);



        getEdit = getIntent();
//        titleEdit.setText(getEdit.getStringExtra(TextNotes.TITLE));
//        noteEdit.setText(getEdit.getStringExtra(TextNotes.CONTENT));
//        timeCreated = getEdit.getStringExtra(TextNotes.TIME);
        objectId = getEdit.getStringExtra(TextNotes.OBJECT_ID);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Note");
        // Retrieve the object by id
        query.getInBackground(objectId, new GetCallback<ParseObject>() {
            public void done(ParseObject note, ParseException e) {
                if (e == null) {
                    String title = note.getString("title");
                    String content = note.getString("content");
                    titleEdit.setText(title);
                    noteEdit.setText(content);
                } else {
                    // something went wrong
                    Toast.makeText(EditExisting.this, "Error! " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //Refresh
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Note");
        // Retrieve the object by id
        query.getInBackground(objectId, new GetCallback<ParseObject>() {
            public void done(ParseObject note, ParseException e) {
                if (e == null) {
                    String title = note.getString("title");
                    String content = note.getString("content");
                    titleEdit.setText(title);
                    noteEdit.setText(content);
                } else {
                    // something went wrong
                    Toast.makeText(EditExisting.this, "Error! " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_existing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            titleEdit = (EditText) findViewById(R.id.titleEdit);
            noteEdit = (EditText) findViewById(R.id.noteEdit);
            final String titleAfter = titleEdit.getText().toString();
            final String noteAfter = noteEdit.getText().toString();

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Note");
            // Retrieve the object by id
            query.getInBackground(objectId, new GetCallback<ParseObject>() {
                public void done(ParseObject note, ParseException e) {
                    if (e == null) {
                        // Now let's update it with some new data. In this case, only cheatMode and score
                        // will get sent to the Parse Cloud. playerName hasn't changed.
                        note.put("title", titleAfter);
                        note.put("content", noteAfter);
                        note.saveEventually();

                        AlertDialog.Builder builder = new AlertDialog.Builder(EditExisting.this);
                        builder.setMessage(getString(R.string.updateContinue));
                        builder.setCancelable(true);
                        builder.setPositiveButton(getString(R.string.no),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                    }
                                });
                        builder.setNegativeButton(getString(R.string.yes),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog backToNote = builder.create();
                        backToNote.show();
                    } else {
                        // something went wrong
                        Toast.makeText(EditExisting.this, "Error! " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
            return true;
        } else if (id == android.R.id.home) {
            super.onBackPressed();
            Toast t = Toast.makeText(this, getString(R.string.backToNote), Toast.LENGTH_SHORT);
            t.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
