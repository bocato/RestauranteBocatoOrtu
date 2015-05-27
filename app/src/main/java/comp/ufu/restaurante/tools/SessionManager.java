package comp.ufu.restaurante.tools;

/**
 * Created by bocato on 5/26/15.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

import comp.ufu.restaurante.activities.LoginActivity;

public class SessionManager {
    // Shared Preferences
    private SharedPreferences sharedPreferences;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared sharedPreferences mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "restaurant_pref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_TABLE = "table";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        sharedPreferences = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String name, String table){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in sharedPreferences
        editor.putString(KEY_NAME, name);

        // Storing email in sharedPreferences
        editor.putString(KEY_TABLE, table);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, sharedPreferences.getString(KEY_NAME, null));

        // user email id
        user.put(KEY_TABLE, sharedPreferences.getString(KEY_TABLE, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}