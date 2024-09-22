package com.example.weatherwindknow.myalwaysact;

import com.google.gson.Gson;
import com.qweather.sdk.bean.weather.WeatherDailyBean;
import com.qweather.sdk.bean.weather.WeatherHourlyBean;

public class alcity {
    String adcode;
    String location;
    String map;

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public WeatherDailyBean getmWeatherDailyBean() {
        return mWeatherDailyBean;
    }

    public void setmWeatherDailyBean(WeatherDailyBean mWeatherDailyBean) {
        this.mWeatherDailyBean = mWeatherDailyBean;
    }

    public WeatherHourlyBean getMweatherHourlyBean() {
        return mweatherHourlyBean;
    }

    public void setMweatherHourlyBean(WeatherHourlyBean mweatherHourlyBean) {
        this.mweatherHourlyBean = mweatherHourlyBean;
    }

    WeatherDailyBean mWeatherDailyBean;

    WeatherHourlyBean mweatherHourlyBean;


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public alcity(String adcode, String location,String map, WeatherHourlyBean mweatherHourlyBean, WeatherDailyBean mWeatherDailyBean) {
        this.adcode = adcode;
        this.location = location;
        this.map=map;
        this.mweatherHourlyBean = mweatherHourlyBean;
        this.mWeatherDailyBean = mWeatherDailyBean;
    }

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

}
