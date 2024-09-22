package com.example.weatherwindknow.mymain;

import android.content.Context;

import com.qweather.sdk.bean.weather.WeatherDailyBean;
import com.qweather.sdk.bean.weather.WeatherHourlyBean;
import com.qweather.sdk.view.QWeather;

public interface toweathercallback {
    void onWeatherRetrieved(WeatherHourlyBean weatherData);
    interface callback7d{
        public void getweather7D(WeatherDailyBean weatherData);
    }
}
