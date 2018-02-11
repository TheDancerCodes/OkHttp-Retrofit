package com.example.android.restful.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.android.restful.model.DataItem;

import java.io.IOException;

import retrofit2.Call;

public class MyService extends IntentService {

    public static final String TAG = "MyService";
    public static final String MY_SERVICE_MESSAGE = "myServiceMessage";
    public static final String MY_SERVICE_PAYLOAD = "myServicePayload";

    public MyService() {
        super("MyService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

//        Make a service request

        // Instantiate the Web Service
        MyWebService webService =
                MyWebService.retrofit.create(MyWebService.class);

        // Instance of the Call class
        Call<DataItem[]> call = webService.dataItems();

        // Declare the array of data items that is gotten back
        DataItem[] dataItems;

        // Trigger call to Web Service Synchronously
        try {
            dataItems = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(TAG, "onHandleIntent: " + e.getMessage());
            return;
        }

//        Return the data to MainActivity

        // Create Intent Object
        Intent messageIntent = new Intent(MY_SERVICE_MESSAGE);

        // Add data that was returned from the service to the Intent Object
        messageIntent.putExtra(MY_SERVICE_PAYLOAD, dataItems);

        // Broadcast the message
        LocalBroadcastManager manager =
                LocalBroadcastManager.getInstance(getApplicationContext());
        manager.sendBroadcast(messageIntent);
    }

}
