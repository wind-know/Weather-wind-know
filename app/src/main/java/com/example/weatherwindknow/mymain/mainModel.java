package com.example.weatherwindknow.mymain;


import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.weatherwindknow.Model.BaseModel;
import com.example.weatherwindknow.myalwaysact.alcity;
import com.example.weatherwindknow.myalwaysact.alcityDbHelper;
import com.example.weatherwindknow.mymain.future.futureitemFragment;
import com.example.weatherwindknow.mymain.today.myFragment;
import com.qweather.sdk.bean.weather.WeatherDailyBean;
import com.qweather.sdk.bean.weather.WeatherHourlyBean;
import com.qweather.sdk.view.QWeather;

import java.util.ArrayList;
import java.util.List;

public class mainModel extends BaseModel<mainPresenter, Imain.M> {

    public mainModel(mainPresenter presenter) {
        super(presenter);
    }

    public alcityDbHelper dbHelper;
    WeatherHourlyBean mweatherHourlyBean;
    WeatherDailyBean mweatherDailyBean;

    @Override
    public Imain.M getContract() {
        return new Imain.M() {

            @Override
            public List<Fragment> getDatalist(Context context) {
                dbHelper = alcityDbHelper.getInstance(context);
                List<Fragment> fragmentList = new ArrayList<>();
                List<alcity> a = dbHelper.queryRegisterListData();
                if (a.size() == 0) {
                    Log.d("TAGdate", "getDatalist: " + a.size());
                    Log.d("TAGdate", "数据库没");
                    hefengweather(context, "108.9,34.15", new QWeather.OnResultWeatherHourlyListener() {
                        @Override
                        public void onError(Throwable throwable) {
                        }
                        @Override
                        public void onSuccess(WeatherHourlyBean weatherHourlyBean) {
                            mweatherHourlyBean = weatherHourlyBean;
                            getweather7D(context, "108.9,34.15", new QWeather.OnResultWeatherDailyListener() {
                                @Override
                                public void onError(Throwable throwable) {
                                }

                                @Override
                                public void onSuccess(WeatherDailyBean weatherDailyBean) {
                                    mweatherDailyBean = weatherDailyBean;
                                    fragmentList.add(new myFragment(new alcity("610112", "108.9,34.15", "西安市长安区兰台路", mweatherHourlyBean, mweatherDailyBean)));
                                }
                            });
                        }
                    });
                }
                for (int i = 0; i < a.size(); i++) {
                    Log.d("TAGdate", "数据库有");
                    Fragment weatherFragment = new myFragment(a.get(i));
                    fragmentList.add(weatherFragment);
                }
                return fragmentList;
            }

            @Override
            public List<Fragment> getDatalistfuture(Context context) {
                dbHelper = alcityDbHelper.getInstance(context);
                List<Fragment> fragmentList = new ArrayList<>();
                List<alcity> a = dbHelper.queryRegisterListData();
                if (a.size() == 0) {
                    Log.d("TAGdate", "getDatalist: " + a.size());
                    Log.d("TAGdate", "数据库没");
                    hefengweather(context, "108.9,34.15", new QWeather.OnResultWeatherHourlyListener() {
                        @Override
                        public void onError(Throwable throwable) {
                        }
                        @Override
                        public void onSuccess(WeatherHourlyBean weatherHourlyBean) {
                            mweatherHourlyBean = weatherHourlyBean;
                            getweather7D(context, "108.9,34.15", new QWeather.OnResultWeatherDailyListener() {
                                @Override
                                public void onError(Throwable throwable) {
                                }

                                @Override
                                public void onSuccess(WeatherDailyBean weatherDailyBean) {
                                    mweatherDailyBean = weatherDailyBean;
                                    fragmentList.add(new futureitemFragment(mweatherDailyBean, new alcity("610112", "108.9,34.15", "西安市长安区兰台路", mweatherHourlyBean, mweatherDailyBean)));
                                }
                            });
                        }
                    });
                }
                for (int i = 0; i < a.size(); i++) {
                    Log.d("TAGdate", "数据库有");
                    Fragment weatherFragment = new futureitemFragment(a.get(i).getmWeatherDailyBean(),a.get(i));
                    fragmentList.add(weatherFragment);
                }
                return fragmentList;
            }
        };
    }

}
