package madiyarzhenis.kz.universityguide;

import android.app.Application;
import android.content.Context;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

/**
 * Created by Zhenis Madiyar on 8/12/2015.
 */
public class MyApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, getString(R.string.application_id), getString(R.string.client_key));

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        MyApplication.context = getApplicationContext();

        // If you would like all objects to be private by default, remove this
        // line.
        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);
    }
    public static Context getAppContext() {
        return MyApplication.context;
    }
}
