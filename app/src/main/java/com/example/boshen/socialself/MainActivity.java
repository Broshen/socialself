package com.example.boshen.socialself;

import com.facebook.FacebookSdk;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private Button scannerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        scannerButton = (Button) findViewById(R.id.scanQR);

        scannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Intent intent = new Intent(v.getContext(), qr_scanner.class);
                //for debugging
                Intent intent = new Intent(v.getContext(), view_medias.class);
                startActivity(intent);
            }
        });
    }
}
