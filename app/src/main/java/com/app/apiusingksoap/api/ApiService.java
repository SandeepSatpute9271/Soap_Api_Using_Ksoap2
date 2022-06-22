package com.app.apiusingksoap.api;/*
 * Created by Sandeep(Techno Learning) on 20,June,2022
 */

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.app.apiusingksoap.BaseActivity;
import com.app.apiusingksoap.CallFor;
import com.app.apiusingksoap.utils.Constants;

import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;

public class ApiService extends AsyncTask {
    String soapAction;
    String methodName;
    String result = "";
    String callFor = "";
    String data = "";
    BaseActivity activity;
    ProgressDialog progressDialog;

    public ApiService(BaseActivity activity,
                      String data,
                      String callFor){
        this.activity = activity;
        this.callFor = callFor;
        this.data = data;
        soapAction = getSoapAction(callFor);
    }

    private String getSoapAction(String callFor) {
        if(callFor== CallFor.LOGIN){
            methodName = Constants.METHOD_NAME;
            return soapAction = Constants.NAMESPACE+ Constants.METHOD_NAME;
        }else {
            return "";
        }
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(activity,"","Loading...");
    }

    @Override
    protected Object doInBackground(Object[] params) {
        Log.e("POST URL ===>",soapAction);
        Log.e("POST Data ===>",data);
        SoapObject request = new SoapObject(Constants.NAMESPACE, methodName);
        request.addProperty("sCountryISOCode", data); // Message to be converted

        SoapSerializationEnvelope envelope = SoapHelper.getSoapSerializationEnvelope(request);

        HttpTransportSE httpTransportSE = new HttpTransportSE(Constants.API_BASE_URL);
        httpTransportSE.debug = true;

        try {
            httpTransportSE.call(soapAction, envelope);
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }

        Object result = null;
        try {
            result = envelope.getResponse();
            String.valueOf(result);
            Log.i("POST_DATA", String.valueOf(result));
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }

        return result != null ? String.valueOf(result) : "";
    }

    @Override
    protected void onPostExecute(Object o) {
        try
        {
            super.onPostExecute(o);
            progressDialog.dismiss();
            activity.onGetResponse(result, callFor);
        }catch (Exception ex) {
            Log.e("EX "," "+ex);
        }

    }
}
