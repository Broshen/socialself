package com.example.boshen.socialself;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

//This activity is to create a new user when someone signs up (authenticate with facebook, but ID does not exist in our system)
public class NewUserActivity extends AppCompatActivity {

    //variable declaration
    private Button sendButton;
    private EditText fb_field, insta_field, linkedin_field, twitter_field, email_field, phone_field;
    private TextView Instructions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set layout
        setContentView(R.layout.activity_new_user);

        //set up instructions
        Instructions = (TextView)findViewById(R.id.instructions);
        Instructions.setText("Welcome! To finish setting up your account, enter the info for the accounts you want to add.");

        //add listener to button
        sendButton = (Button)findViewById(R.id.send);
        sendButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View V){
                fb_field = (EditText)findViewById(R.id.fb_name);
                insta_field = (EditText)findViewById(R.id.insta_name);
                twitter_field = (EditText)findViewById(R.id.twitter_name);
                linkedin_field = (EditText)findViewById(R.id.linkedin_name);
                email_field = (EditText)findViewById(R.id.email);
                phone_field = (EditText)findViewById(R.id.phone);

                //class to create a new user via sending a POST request to server
                createUser newUser = new createUser();
                newUser.execute();

            }
        });
    }

    class createUser extends databaseFunctions.postuser{

        //helper function to write POST request
        String POSTappend(String key, String val){
            if(val.length()!=0)
                return key+val;
            else
                return key+" ";
        }


        protected void onPreExecute(){

            //initialize local user data database and editor
            userPref = getSharedPreferences("user_info", Context.MODE_PRIVATE);
            userPrefEditor = userPref.edit();

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
                //convert response string back to JSON object
                JSONObject result = new JSONObject(s);

                //if user is successfully created, then set the local data values to their info
                // and send them back to the main menu
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
                    userPrefEditor.putBoolean("isloggedin", true);

                    boolean success = userPrefEditor.commit();

                    Log.d("Debug -created user:", success+"");
                    Intent intent = new Intent(NewUserActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    //otherwise, giveem an error message
                    Toast.makeText(getApplicationContext(), "Error! Unable to create user!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(NewUserActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
            catch(Exception e){
                Log.d("Debug-newUserException", e.getMessage());
            }


        }
    }
}
