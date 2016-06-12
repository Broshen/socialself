package com.example.boshen.socialself;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

//this activity is for the user to log into their account by authenticating with facebook API
public class LoginActivity extends AppCompatActivity {

    //variable declaration
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    SharedPreferences userPref;
    SharedPreferences.Editor userPrefEditor;
    AccessToken accessToken;
    AccessTokenTracker accessTokenTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //initialize facebook API
        callbackManager = CallbackManager.Factory.create();
        FacebookSdk.sdkInitialize(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //assign variable to UI field
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends"); //facebook API

        //initialize local user data database and editor
        userPref = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        userPrefEditor = userPref.edit();

        //logs any user out before logging back in
        LoginManager.getInstance().logOut();

        //facebook API stuff
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                // Set the access token using
                // currentAccessToken when it's loaded or set.
            }
        };
        // If the access token is available already assign it.
        accessToken = AccessToken.getCurrentAccessToken();

        //Login with facebook
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("login", "success");
                String id = Profile.getCurrentProfile().getId();

                //set logged in to true, record name of user and their unique fb id
                userPrefEditor.putBoolean("isloggedin", true);
                userPrefEditor.putString("name", Profile.getCurrentProfile().getName());
                userPrefEditor.putString("id", id);
                userPrefEditor.commit();

                loginAsync login = new loginAsync();
                login.execute();
            }

            //these should be pretty self explanatory...
            @Override
            public void onCancel() {
                Log.d("Debugging - FBlogin", "canceled");
                Toast.makeText(LoginActivity.this, "Login canceled!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("Debugging - FBlogin", "error");
                Toast.makeText(LoginActivity.this, "Error logging in!", Toast.LENGTH_LONG).show();
            }
        });
    }

    //no idea what this does
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    //async class to grab the user's information from our database, or create a new user if needed
    class loginAsync extends databaseFunctions.loaduser{
        protected void onPreExecute(){

            //initialize local user data database and editor
            userPref = getSharedPreferences("user_info", Context.MODE_PRIVATE);
            userPrefEditor = userPref.edit();
        }

        protected void onPostExecute(String s) {

            try {
                //convert response string back to JSON object
                JSONObject result = new JSONObject(s);

                //if user is in the database, save their information locally, and return to the
                //main menu page
                if(result.getInt("success") == 1) {

                    String id = result.getString("id");
                    String fb_name = result.getString("fb_name");
                    String insta_name = result.getString("insta_name");
                    String twitter_name = result.getString("twitter_name");
                    String linkedin_name = result.getString("linkedin_name");
                    String email = result.getString("email");
                    String phone = result.getString("phone");

                    userPrefEditor.putString("id", id);
                    userPrefEditor.putString("fb_name", fb_name);
                    userPrefEditor.putString("insta_name", insta_name);
                    userPrefEditor.putString("twitter_name", twitter_name);
                    userPrefEditor.putString("linkedin_name", linkedin_name);
                    userPrefEditor.putString("email", email);
                    userPrefEditor.putString("phone", phone);
                    userPrefEditor.putBoolean("isloggedin", true);

                    userPrefEditor.commit();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                //if user doesn't exist in our database, go to create new user activity
                else {
                    Intent intent = new Intent(LoginActivity.this, NewUserActivity.class);
                    startActivity(intent);
                }
            }
            catch(Exception e){
                Log.d("Debug - loginException", e.getMessage());
            }


        }
    }


}
