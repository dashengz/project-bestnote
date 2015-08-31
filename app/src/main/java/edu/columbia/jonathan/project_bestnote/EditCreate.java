package edu.columbia.jonathan.project_bestnote;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.Date;


public class EditCreate extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_create);

        // Font path
        String fontPath = "fonts/indieFlower.ttf";

        // text view label
        TextView titleIns = (TextView) findViewById(R.id.textView2);
        EditText titleEdit = (EditText) findViewById(R.id.titleEdit);
        EditText contentEdit = (EditText) findViewById(R.id.noteEdit);

        // Loading Font Face
        Typeface indieFlower = Typeface.createFromAsset(getAssets(), fontPath);

        // Applying font
        titleIns.setTypeface(indieFlower);
        titleEdit.setTypeface(indieFlower);
        contentEdit.setTypeface(indieFlower);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Toast t = Toast.makeText(this, getString(R.string.returnToView), Toast.LENGTH_SHORT);
        t.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_create, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
//            try {
//                OutputStreamWriter outNote = new OutputStreamWriter
//                        (openFileOutput("note.txt",MODE_APPEND));
//                TextView titleEdit = (TextView) findViewById(R.id.titleEdit);
//                Date date = new Date();
//                EditText noteEdit = (EditText) findViewById(R.id.noteEdit);
//                String entry;
//                String title = titleEdit.getText().toString();
//                String newDate = new SimpleDateFormat("h:mm a '|' EEE, MMM d, ''yy").format(date);
//                String note = noteEdit.getText().toString();
//                entry = title + "\n" + newDate + "\n\n" + note;
//                outNote.write(entry);
//                outNote.write("\n\n***\n\n");
//                outNote.close();
//
//                Toast.makeText(this,"Saved!" , Toast.LENGTH_SHORT).show();
//            }
//            catch (java.io.IOException e) {
//                Toast.makeText(this,"Failed to save!",Toast.LENGTH_SHORT).show();
//            }
            final EditText titleEdit = (EditText) findViewById(R.id.titleEdit);
            Date date = new Date();
            final EditText noteEdit = (EditText) findViewById(R.id.noteEdit);
            String title = titleEdit.getText().toString();
            String newDate = new SimpleDateFormat("h:mm a '|' EEE, MMM d, ''yy").format(date);
            String note = noteEdit.getText().toString();

            ParseObject noteObject = new ParseObject("Note");
            ParseUser currentUser = ParseUser.getCurrentUser();
            noteObject.put("user", currentUser);
            noteObject.put("title", title);
            noteObject.put("createTime", newDate);
            noteObject.put("content", note);
            noteObject.saveEventually();
            //Toast.makeText(this,"Saved!" , Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getString(R.string.savedAddNew));
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
                            titleEdit.setText("");
                            noteEdit.setText("");
                        }
                    });

            AlertDialog backToList = builder.create();
            backToList.show();

            return true;
        } else if (id == android.R.id.home) {
            super.onBackPressed();
            Toast t = Toast.makeText(this, getString(R.string.returnToView), Toast.LENGTH_SHORT);
            t.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
