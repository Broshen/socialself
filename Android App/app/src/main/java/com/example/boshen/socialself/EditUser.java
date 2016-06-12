package com.example.boshen.socialself;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

//This activity is for the user to edit the information associated with their account (facebook account, instagram account, etc.)
public class EditUser extends AppCompatActivity {
    //variable declaration
    private Button sendButton;
    private EditText fb_field, insta_field, linkedin_field, twitter_field, email_field, phone_field;
    private TextView Instructions;
    SharedPreferences userPref;
    SharedPreferences.Editor userPrefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the layout
        setContentView(R.layout.activity_new_user);

        //initialize local user data database and editor
        userPref = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        userPrefEditor = userPref.edit();

        //set the layout text
        Instructions = (TextView)findViewById(R.id.instructions);
        Instructions.setText("Here is your account info: ");

        //assign UI fields
        sendButton = (Button)findViewById(R.id.send);
        sendButton.setText("Save");
        fb_field = (EditText) findViewById(R.id.fb_name);
        insta_field = (EditText) findViewById(R.id.insta_name);
        twitter_field = (EditText) findViewById(R.id.twitter_name);
        linkedin_field = (EditText) findViewById(R.id.linkedin_name);
        email_field = (EditText) findViewById(R.id.email);
        phone_field = (EditText) findViewById(R.id.phone);

        //assign the user's information to the appropriate UI field
        fb_field.setText(userPref.getString("fb_name", " "));
        insta_field.setText(userPref.getString("insta_name", " "));
        twitter_field.setText(userPref.getString("twitter_name", " "));
        linkedin_field.setText(userPref.getString("linkedin_name", " "));
        email_field.setText(userPref.getString("email", " "));
        phone_field.setText(userPref.getString("phone", " "));

        //button listener to edit user info
        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View V) {
                createUser newUser = new createUser();
                newUser.execute();
            }
        });
    }

    //extends databaseFunction postuser async class
    class createUser extends databaseFunctions.postuser{

        //helper function to write the POST request
        String POSTappend(String key, String val){
            if(val.length()!=0)
                return key+val;
            else
                return key+" ";
        }

        protected void onPreExecute(){

            //initialize the sharedpreference variable and editor, so that the updated fields can be saved
            userPref = getSharedPreferences("user_info", Context.MODE_PRIVATE);
            userPrefEditor = userPref.edit();

            //append the user's new information as the data to send to the POST request
            sendData+="id="+userPref.getString("id", " ");

            sendData+=POSTappend("&fb=",fb_field.getText().toString());
            sendData+=POSTappend("&insta=",insta_field.getText().toString());
            sendData+=POSTappend("&twitter=",twitter_field.getText().toString());
            sendData+=POSTappend("&linkedin=",linkedin_field.getText().toString());
            sendData+=POSTappend("&email=",email_field.getText().toString());
            sendData+=POSTappend("&phone=",phone_field.getText().toString());

            Log.d("debug-sendData values", sendData);
        }

        protected void onPostExecute(String s) {

            try {
                //convert the response string back to a JSON object
                JSONObject result = new JSONObject(s);

                //if the mySQL query was successful, update the user's info locally (on the device) in the sharedpreference editor
                if(result.getInt("success") > 0 ) {
                    String fb_name = fb_field.getText().toString();
                    String insta_name = insta_field.getText().toString();
                    String twitter_name = twitter_field.getText().toString();
                    String linkedin_name = linkedin_field.getText().toString();
                    String email = email_field.getText().toString();
                    String phone = phone_field.getText().toString();

                    userPrefEditor.putString("fb_name", fb_name);
                    userPrefEditor.putString("insta_name", insta_name);
                    userPrefEditor.putString("twitter_name", twitter_name);
                    userPrefEditor.putString("linkedin_name", linkedin_name);
                    userPrefEditor.putString("email", email);
                    userPrefEditor.putString("phone", phone);

                    boolean success = userPrefEditor.commit();

                    Log.d("Debug -updated user:", success+"");

                    //send the user back to the main screen
                    Intent intent = new Intent(EditUser.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    //otherwise, display the error message, and send the user back to the main screen
                    Toast.makeText(getApplicationContext(), "Error! Unable to edit user!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(EditUser.this, MainActivity.class);
                    startActivity(intent);
                }
            }
            catch(Exception e){
                //hopefully never reaches here, lol
                Log.d("Debug-newUserException", e.getMessage());
            }


        }
    }
}
