package com.example.boshen.socialself;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.json.JSONObject;

import me.dm7.barcodescanner.zbar.BarcodeFormat;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

import java.util.ArrayList;
import java.util.List;

//this activity uses the ZBar scanning library to scan a QR code
public class qr_scanner extends AppCompatActivity implements ZBarScannerView.ResultHandler  {
    private ZBarScannerView mScannerView;
    private ArrayList<Integer> mSelectedIndices;
    String scanData;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZBarScannerView(this);    // Programmatically initialize the scanner view
        setupFormats();
        setContentView(mScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    //handle the result from the scanned data
    public void handleResult(Result rawResult) {

        //gets a user's data based on the ID that was scanned from the QR code
        // from the server using an async task
        scanData = rawResult.getContents();
        getUser addUser = new getUser();

        addUser.execute();
    }

    //set up the formats that the QR scanner can scan
    public void setupFormats() {
        List<BarcodeFormat> formats = new ArrayList<BarcodeFormat>();
        if(mSelectedIndices == null || mSelectedIndices.isEmpty()) {
            mSelectedIndices = new ArrayList<Integer>();
            for(int i = 0; i < BarcodeFormat.ALL_FORMATS.size(); i++) {
                mSelectedIndices.add(i);
            }
        }

        for(int index : mSelectedIndices) {
            formats.add(BarcodeFormat.ALL_FORMATS.get(index));
        }
        if(mScannerView != null) {
            mScannerView.setFormats(formats);
        }
    }

    //async task that gets the user data from the mySQL server based on the ID that was scanned
    class getUser extends databaseFunctions.getuser {

        protected void onPreExecute(){
            this.sendData=scanData;
        }

        protected void onPostExecute(String s) {

            try {
                //convert string back to JSON object
                JSONObject result = new JSONObject(s);

                //if a user was successfully retrieved, start the activity to show their info
                if(result.getInt("success") == 1) {
                    Intent intent = new Intent(qr_scanner.this, view_medias.class);
                    intent.putExtra("data", result.toString());
                    startActivity(intent);

                }
          //otherwise, display the error message
                else {
                    Toast.makeText(getApplicationContext(), result.getString("message"), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(qr_scanner.this, MainActivity.class);
                    startActivity(intent);
                }
            }
            catch(Exception e){
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}