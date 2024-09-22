package com.example.weatherwindknow.myservice;


public interface WeatherDataCallback {
    void onSuccess(Root weatherData);
    void onError(Throwable error);
}