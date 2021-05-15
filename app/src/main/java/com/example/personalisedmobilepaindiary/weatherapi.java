package com.example.personalisedmobilepaindiary;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface weatherapi {
    @GET("weather")
    Call<weatherfetch> getweather(@Query("q") String cityname,
                                  @Query("appid") String apikey);
}
