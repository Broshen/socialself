package com.example.boshen.socialself.fragments;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.boshen.socialself.R;

//fragment to display the contact info (phone number, email)
public class ContactFragment extends Fragment {

    View rootview;
    EditText nameField, emailField, phoneField;
    String name, email, phone;
    Button addBtn;

    public ContactFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        //get fields that were passed through
        email = getArguments().getString("email");
        phone = getArguments().getString("phone");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //display fields
        rootview = inflater.inflate(R.layout.fragment_contact, container, false);
        nameField = (EditText)rootview.findViewById(R.id.name);
        emailField = (EditText)rootview.findViewById(R.id.email);
        phoneField = (EditText)rootview.findViewById(R.id.phone);
        addBtn = (Button)rootview.findViewById(R.id.addBtn);

        if(email.length()>6){
            emailField.setText(email);
        }
        if(phone.length()>1){
            phoneField.setText(phone);
        }

        //add listener to add contact info when button is clicked
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameField.getText().toString();
                email = emailField.getText().toString();
                phone = phoneField.getText().toString();

                if(name.length()>0 ){
                    if(email.length()<1 && phone.length()<1){
                        Toast.makeText(v.getContext(), "Both contact fields cannot be empty!", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Intent intent = new Intent(Intent.ACTION_INSERT,
                            ContactsContract.Contacts.CONTENT_URI);

                        intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
                        intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
                        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);

                        startActivity(intent);
                    }
                }
                else{
                    Toast.makeText(v.getContext(), "Name field cannot be empty!", Toast.LENGTH_LONG).show();
                }
            }
        });
        return rootview;
    }

}
