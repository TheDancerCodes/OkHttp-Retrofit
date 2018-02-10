package com.example.android.restful.utils;

import java.io.IOException;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpHelper {

    public static String downloadFromFeed(RequestPackage requestPackage)
            throws IOException {

        String address = requestPackage.getEndpoint();
        String encodedParams = requestPackage.getEncodedParams();

        // Check for a GET Request
        if (requestPackage.getMethod().equals("GET") &&
                encodedParams.length() > 0) {
            address = String.format("%s?%s", address, encodedParams);
        }

        // Instantiate OkHttp Client class
        OkHttpClient client = new OkHttpClient();

        // Create an OkHttp Request
        Request.Builder requestBuilder = new Request.Builder()
                .url(address);

        // Check for a POST Request
        if (requestPackage.getMethod().equals("POST")) {

            // Take parameters being passed in and wrap them into the body of the request
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM); // Simulating a web form

            // Extract parameters from requestPackage
            Map<String, String> params = requestPackage.getParams();

            // Loop through params
            for (String key: params.keySet()) {

                // Pass parameter into the Builder object
                builder.addFormDataPart(key, params.get(key));
            }

            // Create instance of Request Body using the MultipartBody Builder
            RequestBody requestBody = builder.build();

            // Specify to Request object that this is a POST request
            requestBuilder.method("POST", requestBody);
        }

        // Build the request
        Request request = requestBuilder.build();

        // Make the Call
        Response response = client.newCall(request).execute(); // This call is synchronous

        // Check whether Response is successful
        if (response.isSuccessful()) {

            // Return the resulting string
            return response.body().string();
        } else {
            // Throw an Exception when not successful
            throw new IOException("Exception: response code " + response.code());
        }

    }

}