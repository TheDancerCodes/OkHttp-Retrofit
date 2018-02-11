package com.example.android.restful.services;

import com.example.android.restful.model.DataItem;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by TheDancerCodes on 10/02/2018.
 */

public interface MyWebService {

    // Define your Constants
    String BASE_URL = "http://560057.youcanlearnit.net/"; // BASE_URL for entire service
    String FEED = "services/json/itemsfeed.php"; // FEED for a specific feed

    // Instance of Retrofit
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    // Define Requests
    @GET(FEED)

    // Define a method to execute the call
    Call<DataItem[]> dataItems();


    /*
       When the user uses this version of the method, the value they pass in as the Java argument
       `category` should be turned into the request parameter "category"
     */
    @GET(FEED)
    Call<DataItem[]> dataItems(@Query("category") String category);


}
