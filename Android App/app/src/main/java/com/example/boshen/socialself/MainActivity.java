package com.example.boshen.socialself;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

// This activity is the menu that the user will see when they log in, and where they will choose all other functions to do
public class MainActivity extends AppCompatActivity {

    //variable declarations
    private Button scannerButton, managerButton, logoutButton, viewQRButton;
    TextView welcomeText;

    SharedPreferences userPref;
    SharedPreferences.Editor userPrefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_main);

        //sharedpreferences that gets user info
        userPref = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        userPrefEditor = userPref.edit();

        //assign variables to XML counterparts
        scannerButton = (Button) findViewById(R.id.scanQR);
        logoutButton = (Button) findViewById(R.id.logoutbutton);
        viewQRButton = (Button) findViewById(R.id.viewCode);
        managerButton = (Button) findViewById(R.id.manageMedia);
        welcomeText = (TextView) findViewById(R.id.welcomeText);

        //checks to see if user is logged in, if not, start log in activity
        boolean isloggedin = (userPref.getBoolean("isloggedin", false)|| AccessToken.getCurrentAccessToken() != null);
        String name = userPref.getString("name", null);

        if(!isloggedin || name == null){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        //display welcome message to user:
        else {
            welcomeText.setText("Welcome, " + name + ", to your social media manager!");
        }

        //button listeners
        scannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), qr_scanner.class);
                //for debugging
                //Intent intent = new Intent(v.getContext(), view_medias.class);
                startActivity(intent);
            }
        });

        viewQRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), myQrCode.class);
                startActivity(intent);
            }
        });

        managerButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                Intent intent = new Intent(v.getContext(), EditUser.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();

                userPrefEditor.putBoolean("isloggedin", false);
                userPrefEditor.commit();


                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}
