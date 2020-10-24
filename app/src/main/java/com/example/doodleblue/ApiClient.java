package com.example.doodleblue;


import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiClient {



    @GET("v2/assets")
    Call<PriceJsonResponse> getPriceDetails();

}
