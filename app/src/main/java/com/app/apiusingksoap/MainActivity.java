package com.app.apiusingksoap;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.apiusingksoap.api.ApiService;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MainActivity extends BaseActivity {

    private TextView tvCapital;
    private EditText etCountry;
    private String TAG = MainActivity.class.getSimpleName();
    long start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        tvCapital = findViewById(R.id.tvCapital);
        etCountry = findViewById(R.id.etCountry);
    }

    public void CallApi(View view) {
        if(etCountry.getText().toString().trim().equals("")){
            Toast.makeText(this, "Please enter Country name", Toast.LENGTH_SHORT).show();
        }else{
            start = System.currentTimeMillis();
            new ApiService(MainActivity.this, etCountry.getText().toString().trim(), CallFor.LOGIN).execute();
            tvCapital.setText("");
        }
    }

    @Override
    public void onGetResponse(String response, String callFor) {
        super.onGetResponse(response, callFor);
        long end = System.currentTimeMillis();
        long elapsedTime = end - start;
        Log.e(TAG,"Retrofit: Time Taken In milli sec:"+elapsedTime+" for Input "+etCountry.getText().toString().trim());
        tvCapital.setText("Capital of "+etCountry.getText().toString().trim()+" is "+String.valueOf(response));

    }
}