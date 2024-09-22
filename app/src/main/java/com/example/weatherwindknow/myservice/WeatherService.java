package com.example.weatherwindknow.myservice;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("v3/weather/weatherInfo")
    Call<Root> getWeatherData(
            @Query("key") String apiKey,
            @Query("city") String cityCode,
            @Query("extensions") String extensions // "base" 或 "all"
    );
}