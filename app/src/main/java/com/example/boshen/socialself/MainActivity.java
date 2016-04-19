package com.example.boshen.socialself;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MainActivity extends AppCompatActivity {

    private Button scannerButton, managerButton, logoutButton;
    TextView welcomeText;

    SharedPreferences loginPref;
    SharedPreferences.Editor loginEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_main);

        //stores whether user is logged in or not
        loginPref = getSharedPreferences("login_info", Context.MODE_PRIVATE);
        loginEditor = loginPref.edit();

        //assign variables to XML counterparts
        scannerButton = (Button) findViewById(R.id.scanQR);
        logoutButton = (Button) findViewById(R.id.logoutbutton);
        welcomeText = (TextView) findViewById(R.id.welcomeText);

        //checks to see if user is logged in, if not, start log in activity
        boolean isloggedin = (loginPref.getBoolean("isloggedin", false)|| AccessToken.getCurrentAccessToken() != null);

        if(!isloggedin){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        //display welcome message to user:
        String name = loginPref.getString("name", null);

        //if user's name doesnt exist, log them in again to get their name, otherwise, simply display the welcome message
        if(name == null){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        else {
            welcomeText.setText("Welcome, " + name + ", to your social media manager!");
        }

        //button listeners
        scannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: make my own image recognition instead of using shitty qr scanner
                // Intent intent = new Intent(v.getContext(), qr_scanner.class);
                //for debugging
                Intent intent = new Intent(v.getContext(), view_medias.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();

                loginEditor.putBoolean("isloggedin", false);
                loginEditor.commit();


                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });



    }
}
