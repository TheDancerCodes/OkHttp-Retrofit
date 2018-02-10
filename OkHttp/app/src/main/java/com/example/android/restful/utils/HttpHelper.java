package com.example.android.restful.utils;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpHelper {

    public static String downloadFromFeed(RequestPackage requestPackage)
            throws IOException {

        String address = requestPackage.getEndpoint();
        String encodedParams = requestPackage.getEncodedParams();

        if (requestPackage.getMethod().equals("GET") &&
                encodedParams.length() > 0) {
            address = String.format("%s?%s", address, encodedParams);
        }

        // Instantiate OkHttp Client class
        OkHttpClient client = new OkHttpClient();

        // Create an OkHttp Request
        Request.Builder requestBuilder = new Request.Builder()
                .url(address);

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