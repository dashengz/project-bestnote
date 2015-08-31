package edu.columbia.jonathan.project_bestnote;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends ActionBarActivity {

    TextView logo;
    Button signUp;
    Button logIn;
    EditText username;
    EditText password;
    CheckBox show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // hide action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // Font path
        String fontPath = "fonts/indieFlower.ttf";

        // text view label
        logo = (TextView) findViewById(R.id.logo);
        signUp = (Button) findViewById(R.id.signUp);
        logIn = (Button) findViewById(R.id.logIn);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        show = (CheckBox) findViewById(R.id.show);

        // Loading Font Face
        Typeface indieFlower = Typeface.createFromAsset(getAssets(), fontPath);

        // Applying font
        logo.setTypeface(indieFlower);
        signUp.setTypeface(indieFlower);
        logIn.setTypeface(indieFlower);
        username.setTypeface(indieFlower);
        password.setTypeface(indieFlower);
        show.setTypeface(indieFlower);

        // show password function
        show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // checkbox status is changed from unchecked to checked.
                if (!isChecked) {
                    // show password
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
    }

    public void signUp(View view) {
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        ParseUser user = new ParseUser();
        user.setUsername(username.getText().toString());
        user.setPassword(password.getText().toString());

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                    ParseUser currentUser = ParseUser.getCurrentUser();
                    String name = currentUser.getUsername();
                    Date date = new Date();
                    String title = getString(R.string.welcome);
                    String newDate = new SimpleDateFormat("h:mm a '|' EEE, MMM d, ''yy").format(date);
                    String note = getString(R.string.greetings) + name + getString(R.string.goAndCreate);

                    ParseObject noteObject = new ParseObject("Note");

                    //noteObject.put("creatorId", name);
                    noteObject.put("user", currentUser);
                    noteObject.put("title", title);
                    noteObject.put("createTime", newDate);
                    noteObject.put("content", note);
                    //noteObject.saveInBackground();
                    noteObject.saveEventually();

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage(getString(R.string.signUpSuccess));
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
                                    ParseUser.logInInBackground(username.getText().toString(),
                                            password.getText().toString(), new LogInCallback() {
                                                public void done(ParseUser user, ParseException e) {
                                                    if (user != null) {
                                                        // Hooray! The user is logged in.
                                                        Toast.makeText(MainActivity.this, getString(R.string.logInSuccess),
                                                                Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(MainActivity.this, NavDrawer.class);

                                                        startActivity(intent);
                                                    } else {
                                                        // Login failed. Look at the ParseException to see what happened.
                                                        Toast.makeText(MainActivity.this, "Error! " + e.getMessage(),
                                                                Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }
                            });

                    AlertDialog logIn = builder.create();
                    logIn.show();

                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                    Toast.makeText(MainActivity.this, "Error! " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void logIn(View view) {
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        ParseUser.logInInBackground(username.getText().toString(),
                password.getText().toString(), new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    // Hooray! The user is logged in.
                    Toast.makeText(MainActivity.this, getString(R.string.logInSuccess),
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, NavDrawer.class);

                    startActivity(intent);
                } else {
                    // Login failed. Look at the ParseException to see what happened.
                    Toast.makeText(MainActivity.this, "Error! " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
