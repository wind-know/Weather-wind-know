package com.example.weatherwindknow.mymain;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.weatherwindknow.Presenter.BasePresenter;
import com.example.weatherwindknow.View.MainActivity;
import com.example.weatherwindknow.mymain.today.myFragment;
import com.example.weatherwindknow.myservice.Root;
import com.example.weatherwindknow.myservice.WeatherDataCallback;
import com.qweather.sdk.bean.weather.WeatherDailyBean;
import com.qweather.sdk.bean.weather.WeatherHourlyBean;
import com.qweather.sdk.view.QWeather;

import java.util.List;

public class mainPresenter extends BasePresenter<mainModel, MainActivity,Imain.Vp> {
//    public mainPresenter() {
//        super();
//    }
    myFragment myfragment;
    public void bindingfragment(myFragment myfragment){
        this.myfragment = myfragment;
    }

    @Override
    public mainModel getModelInstance() {
        return new mainModel(this);
    }


    @Override
    public Imain.Vp getContract() {
        return new Imain.Vp() {
            @Override
            public void getweatherafterclick(String adcode) {
                mModel.getweatherafterclick(adcode, new WeatherDataCallback() {
                    @Override
                    public void onSuccess(Root weatherData) {
                        myfragment.mv.newchange(weatherData.getForecasts().get(0).getCasts().get(0).getDaytemp());
                    }
                    @Override
                    public void onError(Throwable error) {
                        Log.e("error",error.toString());
                    }
                });
            }

            @Override
            public List<Fragment> getDatalist(Context context) {
                return mModel.getContract().getDatalist(context);
            }

            @Override
            public List<Fragment> getDatalistfuture(Context context) {

                return mModel.getContract().getDatalistfuture(context);
            }

            @Override
            public void hefengweather(Context context, String location, toweathercallback toweathercallback) {
                Log.e("hefengp",location);
                mModel.hefengweather(context, location, new QWeather.OnResultWeatherHourlyListener() {
                    @Override
                    public void onError(Throwable throwable) {
                        Log.e("onError",location);
                    }

                    @Override
                    public void onSuccess(WeatherHourlyBean weatherHourlyBean) {
                        Log.e("onSuccess",location);
                        toweathercallback.onWeatherRetrieved(weatherHourlyBean);
                    }
                });
            }

            @Override
            public void getweather7D(Context context, String location, toweathercallback.callback7d callback) {
                Log.e("getweather7D",location);
                mModel.getweather7D(context, location, new QWeather.OnResultWeatherDailyListener() {
                    @Override
                    public void onError(Throwable throwable) {
                        Log.e("7donError",location);
                    }

                    @Override
                    public void onSuccess(WeatherDailyBean weatherDailyBean) {
                        Log.e("onSuccess",location);
                        callback.getweather7D(weatherDailyBean);
                    }
                });
            }


        };
    }
}
