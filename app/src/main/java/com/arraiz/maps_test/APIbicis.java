package com.arraiz.maps_test;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface APIbicis {

    public static final String BASE_URL = "api.arraiz.eus/";
    @Headers("x-api-key: thisisbicisapi")
    @GET("getBicis")
    Call<List<StationModel>> getStations();

}
