package com.example.weatherwindknow.Model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.weatherwindknow.Presenter.BasePresenter;
import com.example.weatherwindknow.View.MapActivity;
import com.example.weatherwindknow.myservice.Root;
import com.example.weatherwindknow.myservice.WeatherDataCallback;
import com.example.weatherwindknow.myservice.WeatherServiceHelper;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.qweather.sdk.view.QWeather;

public abstract class BaseModel<P extends BasePresenter, CONTRACT> extends superBase<CONTRACT> {
    public P mPresenter;

    public BaseModel(P presenter) {
        this.mPresenter = presenter;
    }

    public void getweatherafterclick(String adcode, WeatherDataCallback callback) {
        WeatherServiceHelper helper = new WeatherServiceHelper();
        helper.getWeatherDataAsync("f989802809dde6b0aa0d39c66075ba6c", adcode, "all", callback);
    }

    public void hefengweather(Context context, String location, QWeather.OnResultWeatherHourlyListener listener) {
        QWeather.getWeather24Hourly(context, location, listener);
    }

    public void getweather7D(Context context, String location, QWeather.OnResultWeatherDailyListener listener) {
        QWeather.getWeather7D(context, location, listener);
    }
}