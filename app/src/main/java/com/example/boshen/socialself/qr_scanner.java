package com.example.boshen.socialself;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONObject;

import me.dm7.barcodescanner.zbar.BarcodeFormat;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

import java.util.ArrayList;
import java.util.List;

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
    public void handleResult(Result rawResult) {
        //alert dialog for debugging
//        new AlertDialog.Builder(this)
//                .setTitle(getResources().getString(R.string.app_name))
//                .setCancelable(false)
//                .setMessage(rawResult.getContents())
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {}
//                })
//                .show();

        scanData = rawResult.getContents();
        getUser addUser = new getUser();

        addUser.execute();
    }

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


    class getUser extends databaseFunctions.getuser {

//        String addUserId;
//
//        public getUser(String userId){
//            this.addUserId=userId;
//        }
        protected void onPreExecute(){
            this.sendData=scanData;
        }

        protected void onPostExecute(String s) {

            try {
                JSONObject result = new JSONObject(s);

                if(result.getInt("success") == 1) {
                    Intent intent = new Intent(qr_scanner.this, view_medias.class);
                    intent.putExtra("data", result.toString());
                    startActivity(intent);

                }
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