package com.example.boshen.socialself;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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

public class LoginActivity extends AppCompatActivity {


    private LoginButton loginButton;
    private CallbackManager callbackManager;
    SharedPreferences userPref;
    SharedPreferences.Editor userPrefEditor;
    AccessToken accessToken;
    AccessTokenTracker accessTokenTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        callbackManager = CallbackManager.Factory.create();
        FacebookSdk.sdkInitialize(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");

        userPref = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        userPrefEditor = userPref.edit();

        //logs any user out before logging back in
        LoginManager.getInstance().logOut();

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

                //set logged in to true, record name of user
                userPrefEditor.putBoolean("isloggedin", true);
                userPrefEditor.putString("name", Profile.getCurrentProfile().getName());
                userPrefEditor.putString("id", id);
                userPrefEditor.commit();

                loginAsync login = new loginAsync();
                login.execute();
            }

            @Override
            public void onCancel() {
                // App code
                Log.d("Debugging - FBlogin", "canceled");
            }

            @Override
            public void onError(FacebookException exception) {

                Log.d("Debugging - FBlogin", "error");
                // App code
            }
        });
    }

    //no idea what this does
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    class loginAsync extends databaseFunctions.loaduser{
        protected void onPreExecute(){

            userPref = getSharedPreferences("user_info", Context.MODE_PRIVATE);
            userPrefEditor = userPref.edit();
        }

        protected void onPostExecute(String s) {

            try {
                JSONObject result = new JSONObject(s);

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
