package edu.columbia.jonathan.project_bestnote;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


public class NoteDetail extends ActionBarActivity {

    Intent getNote;
    String objectId;
    String title;
    String time;
    String content;
    TextView titleView;
    TextView timeView;
    TextView contentView;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        titleView = (TextView) findViewById(R.id.title);
        timeView = (TextView) findViewById(R.id.time);
        contentView = (TextView) findViewById(R.id.content);

        // Font path
        String fontPath = "fonts/indieFlower.ttf";

        // Loading Font Face
        Typeface indieFlower = Typeface.createFromAsset(getAssets(), fontPath);

        // Applying font
        titleView.setTypeface(indieFlower);
        timeView.setTypeface(indieFlower);
        contentView.setTypeface(indieFlower);

        getNote = getIntent();

        objectId = getNote.getStringExtra(TextNotes.OBJECT_ID);
//        title = getNote.getStringExtra(TextNotes.TITLE);
//        time = getNote.getStringExtra(TextNotes.TIME);
//        content = getNote.getStringExtra(TextNotes.CONTENT);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Note");
        // Retrieve the object by id
        query.getInBackground(objectId, new GetCallback<ParseObject>() {
            public void done(ParseObject note, ParseException e) {
                if (e == null) {
                    title = note.getString("title");
                    time = note.getString("createTime");
                    content = note.getString("content");
                    titleView.setText(title);
                    timeView.setText(time);
                    contentView.setText(content);
                } else {
                    // something went wrong
                    Toast.makeText(NoteDetail.this, "Error! " + e.getMessage(),
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
                    title = note.getString("title");
                    time = note.getString("createTime");
                    content = note.getString("content");
                    titleView.setText(title);
                    timeView.setText(time);
                    contentView.setText(content);
                } else {
                    // something went wrong
                    Toast.makeText(NoteDetail.this, "Error! " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {

//            InApp Share not done yet;

//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setMessage(getString(R.string.logOut));
//            builder.setCancelable(true);
//            builder.setNegativeButton(getString(R.string.otherApp),
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            String noteShare = title + "\n" + time + "\n" + content;
//                            Intent share = new Intent(Intent.ACTION_SEND);
//                            share.putExtra(Intent.EXTRA_TEXT, noteShare);
//                            share.setType("text/plain");
//                            startActivity(share);
//                        }
//                    });
//            builder.setPositiveButton(getString(R.string.inAppShare),
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            Intent inAppShare = new Intent(NoteDetail.this, InAppShare.class);
//                            inAppShare.putExtra(TextNotes.OBJECT_ID, objectId);
//                            startActivity(inAppShare);
//                        }
//                    });
//
//            AlertDialog shareHow = builder.create();
//            shareHow.show();

            String noteShare = title + "\n" + time + "\n" + content;
            Intent share = new Intent(Intent.ACTION_SEND);
            share.putExtra(Intent.EXTRA_TEXT, noteShare);
            share.setType("text/plain");
            startActivity(share);


//            Toast t = Toast.makeText(this, "Not Yet Developed", Toast.LENGTH_SHORT);
//            t.show();
            return true;
        } else if (id == R.id.action_edit) {
            Intent edit = new Intent(NoteDetail.this, EditExisting.class);
            edit.putExtra(TextNotes.OBJECT_ID, objectId);
//            edit.putExtra(TextNotes.TITLE, title);
//            edit.putExtra(TextNotes.TIME, time);
//            edit.putExtra(TextNotes.CONTENT, content);
            startActivity(edit);
            return true;
        } else if (id == R.id.action_discard) {
            AlertDialog.Builder builder = new AlertDialog.Builder(NoteDetail.this);
            builder.setMessage(getString(R.string.deleteOrNot));
            builder.setCancelable(true);
            builder.setPositiveButton(getString(R.string.no),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            builder.setNegativeButton(getString(R.string.yes),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ParseQuery<ParseObject> query = ParseQuery.getQuery("Note");
                            // Retrieve the object by id
                            query.getInBackground(objectId, new GetCallback<ParseObject>() {
                                public void done(ParseObject note, ParseException e) {
                                    if (e == null) {
                                        // Now let's update it with some new data. In this case, only cheatMode and score
                                        // will get sent to the Parse Cloud. playerName hasn't changed.
                                        note.deleteEventually();
                                        mHandler.postDelayed(new Runnable() {
                                            public void run() {
                                                finish();
                                            }
                                        }, 1000);
                                    } else {
                                        // something went wrong
                                        Toast.makeText(NoteDetail.this, "Error! " + e.getMessage(),
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    });

            AlertDialog delete = builder.create();
            delete.show();
            return true;
        } else if (id == android.R.id.home) {
            super.onBackPressed();
            Toast t = Toast.makeText(this, getString(R.string.backToList), Toast.LENGTH_SHORT);
            t.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
