package edu.columbia.jonathan.project_bestnote;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class TextNotes extends ActionBarActivity {

//    public final static String TITLE = "title";
//    public final static String TIME = "time";
//    public final static String CONTENT = "content";
    public final static String OBJECT_ID = "object_id";
    ListView list;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_notes);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.viewNotesNav));

        // Font path
        String fontPath = "fonts/indieFlower.ttf";

        // text view label
        TextView textView = (TextView) findViewById(R.id.textView);

        // Loading Font Face
        Typeface indieFlower = Typeface.createFromAsset(getAssets(), fontPath);

        // Applying font
        textView.setTypeface(indieFlower);


//        StringBuilder note = new StringBuilder();
//        try {
//            InputStream inStreamNote = openFileInput("note.txt");
//            if (inStreamNote != null) {
//                InputStreamReader inputReader = new InputStreamReader(inStreamNote);
//                BufferedReader buffReader = new BufferedReader(inputReader);
//                String line = null;
//                while (( line = buffReader.readLine()) != null) {
//                    note.append(line);
//                    note.append('\n');
//                }
//            }
//        }
//        catch (IOException e) {
//            Toast.makeText(this, "Go on and Create your First Note",
//                    Toast.LENGTH_SHORT).show();
//        }
//        TextView noteShow = (TextView) findViewById (R.id.note);
//        noteShow.setText(note);

        WebView translate = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = translate.getSettings();
        webSettings.setJavaScriptEnabled(true);
        translate.loadUrl("https://translate.google.com/m/translate");


        //build arraylist

        ParseUser currentUser = ParseUser.getCurrentUser();
        ParseQuery<ParseObject> notesQ = ParseQuery.getQuery("Note");
        //notesQ.whereEqualTo("creatorId", currentUser.getUsername());
        notesQ.whereEqualTo("user", currentUser);
        notesQ.orderByDescending("updatedAt");

        notesQ.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    // object will be your game score
                    final ArrayList<NoteObjects> notes = new ArrayList<>();
                    for (ParseObject note : objects) {
                        notes.add(new NoteObjects(note.getString("title"),
                                note.getString("createTime"),note.getString("content"),
                                note.getObjectId()));
                    }
                    //custom listView
                    NoteAdapter adapter = new NoteAdapter(notes);
                    list = (ListView) findViewById(R.id.listView);
                    list.setAdapter(adapter);

                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            //toast msgs giving users information
                            Toast.makeText(TextNotes.this, getString(R.string.editNoteToast),
                                    Toast.LENGTH_LONG).show();

                            //intent to next activity
                            Intent prompt = new Intent(TextNotes.this, NoteDetail.class);
                            //sending extras to next activity
//                            prompt.putExtra(TITLE, notes.get(position).title);
//                            prompt.putExtra(TIME, notes.get(position).createTime);
//                            prompt.putExtra(CONTENT, notes.get(position).content);
                            prompt.putExtra(OBJECT_ID, notes.get(position).objectId);
                            startActivity(prompt);
                        }
                    });
                } else {
                    // something went wrong
                    Toast.makeText(TextNotes.this, "Error! " + e.getMessage(),
                            Toast.LENGTH_LONG).show();

                    //ParseUser currentUser = ParseUser.getCurrentUser();
                    ParseQuery<ParseObject> notesQ = ParseQuery.getQuery("Note");
                    notesQ.fromLocalDatastore();
                    notesQ.orderByDescending("updatedAt");

                    //notesQ.whereEqualTo("creatorId", currentUser.getUsername());
                    //notesQ.whereEqualTo("user", currentUser);
                    notesQ.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e == null) {
                                // object will be your game score
                                ArrayList<NoteObjects> notes = new ArrayList<>();
                                for (ParseObject note : objects) {
                                    notes.add(new NoteObjects(note.getString("title"),
                                            note.getString("createTime"),note.getString("content"),
                                            note.getObjectId()));
                                }
                                //custom listView
                                NoteAdapter adapter = new NoteAdapter(notes);
                                list = (ListView) findViewById(R.id.listView);
                                list.setAdapter(adapter);

                                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view,
                                                            int position, long id) {
                                        //toast msgs giving users information
                                        Toast.makeText(TextNotes.this, getString(R.string.limited),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                // something went wrong
                                Toast.makeText(TextNotes.this, "Error! " + e.getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

//        inputSearch = (EditText) findViewById(R.id.inputSearch);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        //Refresh
        //build arraylist

        ParseUser currentUser = ParseUser.getCurrentUser();
        ParseQuery<ParseObject> notesQ = ParseQuery.getQuery("Note");
        //notesQ.whereEqualTo("creatorId", currentUser.getUsername());
        notesQ.whereEqualTo("user", currentUser);
        notesQ.orderByDescending("updatedAt");


        notesQ.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    // object will be your game score
                    final ArrayList<NoteObjects> notes = new ArrayList<>();
                    for (ParseObject note : objects) {
                        notes.add(new NoteObjects(note.getString("title"),
                                note.getString("createTime"),note.getString("content"),
                                note.getObjectId()));
                    }
                    //custom listView
                    NoteAdapter adapter = new NoteAdapter(notes);
                    list = (ListView) findViewById(R.id.listView);
                    list.setAdapter(adapter);

                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            //toast msgs giving users information
                            Toast.makeText(TextNotes.this, getString(R.string.editNoteToast),
                                    Toast.LENGTH_LONG).show();

                            //intent to next activity
                            Intent prompt = new Intent(TextNotes.this, NoteDetail.class);
                            //sending extras to next activity
//                            prompt.putExtra(TITLE, notes.get(position).title);
//                            prompt.putExtra(TIME, notes.get(position).createTime);
//                            prompt.putExtra(CONTENT, notes.get(position).content);
                            prompt.putExtra(OBJECT_ID, notes.get(position).objectId);
                            startActivity(prompt);
                        }
                    });
                } else {
                    // something went wrong
                    Toast.makeText(TextNotes.this, "Error! " + e.getMessage(),
                            Toast.LENGTH_LONG).show();

                    //ParseUser currentUser = ParseUser.getCurrentUser();
                    ParseQuery<ParseObject> notesQ = ParseQuery.getQuery("Note");
                    notesQ.fromLocalDatastore();
                    notesQ.orderByDescending("updatedAt");

                    //notesQ.whereEqualTo("creatorId", currentUser.getUsername());
                    //notesQ.whereEqualTo("user", currentUser);
                    notesQ.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e == null) {
                                // object will be your game score
                                ArrayList<NoteObjects> notes = new ArrayList<>();
                                for (ParseObject note : objects) {
                                    notes.add(new NoteObjects(note.getString("title"),
                                            note.getString("createTime"),note.getString("content"),
                                            note.getObjectId()));
                                }
                                //custom listView
                                NoteAdapter adapter = new NoteAdapter(notes);
                                list = (ListView) findViewById(R.id.listView);
                                list.setAdapter(adapter);

                                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view,
                                                            int position, long id) {
                                        //toast msgs giving users information
                                        Toast.makeText(TextNotes.this, getString(R.string.limited),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                // something went wrong
                                Toast.makeText(TextNotes.this, "Error! " + e.getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
//        StringBuilder note = new StringBuilder();
//        try {
//            InputStream inStreamNote = openFileInput("note.txt");
//            if (inStreamNote != null) {
//                InputStreamReader inputReader = new InputStreamReader(inStreamNote);
//                BufferedReader buffReader = new BufferedReader(inputReader);
//                String line = null;
//                while (( line = buffReader.readLine()) != null) {
//                    note.append(line);
//                    note.append('\n');
//                }
//            }
//        }
//        catch (IOException e) {
//            Toast.makeText(this,"Go on and Create your First Note",
//                    Toast.LENGTH_SHORT).show();
//        }
//        TextView noteShow = (TextView) findViewById (R.id.note);
//        noteShow.setText(note);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_text_notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_new) {
            Intent i = new Intent(this,EditCreate.class);
            startActivity(i);
            Toast t = Toast.makeText(this, getString(R.string.enterCreate), Toast.LENGTH_SHORT);
            t.show();
            return true;
        }
//        else if (id == R.id.action_search) {
//
//
//            return true;
//        }
        else if (id == android.R.id.home) {
            super.onBackPressed();
            Toast t = Toast.makeText(this, getString(R.string.returnToMain), Toast.LENGTH_SHORT);
            t.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //custom listView
    private class NoteAdapter extends ArrayAdapter<NoteObjects> {

        public NoteAdapter(List<NoteObjects> notes) {
            super(TextNotes.this, android.R.layout.simple_list_item_1, notes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView =
                        getLayoutInflater().inflate(R.layout.note_list, null);
            }

            NoteObjects note = getItem(position);

            TextView titleView = (TextView)
                    convertView.findViewById(R.id.title);
            titleView.setText(note.title);
            TextView timeView = (TextView)
                    convertView.findViewById(R.id.time);
            timeView.setText(note.createTime);
            TextView contentView = (TextView)
                    convertView.findViewById(R.id.content);
            contentView.setText(note.content);

            // Font path
            String fontPath = "fonts/indieFlower.ttf";

            // Loading Font Face
            Typeface indieFlower = Typeface.createFromAsset(getAssets(), fontPath);

            // Applying font
            titleView.setTypeface(indieFlower);
            timeView.setTypeface(indieFlower);
            contentView.setTypeface(indieFlower);

            return convertView;
        }
    }
}
