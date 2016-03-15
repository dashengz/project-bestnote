package edu.columbia.jonathan.project_bestnote;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Jonathan on 4/20/15.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "applicationId", "clientId");
    }}
