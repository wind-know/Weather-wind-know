package com.example.weatherwindknow.mymain;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.qweather.sdk.bean.weather.WeatherHourlyBean;
import com.qweather.sdk.view.QWeather;

import java.util.List;

public interface Imain {
    public interface M{
        List<Fragment> getDatalist(Context context);
        List<Fragment> getDatalistfuture(Context context);
    }
    public interface Vp{
        public void getweatherafterclick(String adcode);
        List<Fragment> getDatalist(Context context);
        List<Fragment> getDatalistfuture(Context context);
        public void hefengweather(Context context, String location,toweathercallback toweathercallback);
        public void getweather7D(Context context,String location, toweathercallback.callback7d callback) ;
    }
}
