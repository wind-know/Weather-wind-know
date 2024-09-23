package com.example.weatherwindknow.mymain.today;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.weatherwindknow.R;
import com.example.weatherwindknow.mymain.Imain;
import com.example.weatherwindknow.mymain.mainPresenter;
import com.example.weatherwindknow.mymain.toweathercallback;
import com.example.weatherwindknow.myservice.MyView;
import com.example.weatherwindknow.databinding.FragmentMyBinding;
import com.example.weatherwindknow.myalwaysact.alcity;
import com.example.weatherwindknow.myalwaysact.alcityDbHelper;
import com.qweather.sdk.bean.weather.WeatherDailyBean;
import com.qweather.sdk.bean.weather.WeatherHourlyBean;

import java.util.List;

public class myFragment extends Fragment implements Imain.Vp {
    FragmentMyBinding binding;
    public MyView mv;
    private mainPresenter mPresenter;
     public alcity alcity;
    public alcityDbHelper dbHelper;
    public myFragment(alcity alcity) {
        super();
        this.alcity = alcity;
    }
    public myFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMyBinding.inflate(inflater, container, false);
        mv = binding.mv;
        binding.mv.moveWaterLine();
        initListeners();
        initViews();
        mPresenter = new mainPresenter();
        mPresenter.bindingfragment(this);
        dbHelper = alcityDbHelper.getInstance(getActivity());
        return binding.getRoot();
    }

    void initViews() {
        Typeface iconfont = Typeface.createFromAsset(getActivity().getAssets(), "iconfont.ttf");
        binding.imageViewWind.setTypeface(iconfont);
        binding.imageViewPressure.setTypeface(iconfont);
        binding.imageViewHumidity.setTypeface(iconfont);
        binding.imageViewPop.setTypeface(iconfont);
        if (alcity != null) {
            WeatherHourlyBean weatherData = alcity.getMweatherHourlyBean();
            if (weatherData != null) {
                mv.newchange(weatherData.getHourly().get(0).getTemp());
                binding.weather.setText(weatherData.getHourly().get(0).getText());
                binding.city.setText(alcity.getMap());
                binding.Humidity.setText("相对湿度:" + "\n" +String.format("%s%%", weatherData.getHourly().get(0).getHumidity()));
                binding.Pop.setText("降水概率:" + "\n" + weatherData.getHourly().get(0).getPop() + "%");
                binding.Wind.setText("风向风力:" + "\n" + weatherData.getHourly().get(0).getWindDir() + " " + weatherData.getHourly().get(0).getWindScale() + "级");
                binding.Pressure.setText("大气压强:" + "\n" +String.format("%shPa", weatherData.getHourly().get(0).getPressure()));
            }
        }
    }
    void initListeners() {
        binding.mv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "点击了", Toast.LENGTH_SHORT).show();
                hefengweather(getActivity(), alcity.getLocation(), new toweathercallback() {
                    @Override
                    public void onWeatherRetrieved(WeatherHourlyBean weatherData) {
                        if (weatherData == null) {
                            Log.e("hefeng", "hefengweather is null");
                        } else {
                            WeatherHourlyBean weatherData1 = weatherData;
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateWeatherInfo(weatherData);
                                    updateRecyclerView(weatherData1);
                                }
                            });
                            getweather7D(getActivity(), alcity.getLocation(), new callback7d() {
                                @Override
                                public void getweather7D(WeatherDailyBean weatherData) {
                                    dbHelper.updateByAdcode(alcity.getAdcode(), alcity.getLocation(), alcity.getMap(), weatherData1, weatherData);
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    private void updateWeatherInfo(WeatherHourlyBean weatherData) {
        mv.newchange(weatherData.getHourly().get(0).getTemp());
        binding.weather.setText(weatherData.getHourly().get(0).getText());
        binding.city.setText(alcity.getMap());
        binding.Humidity.setText("相对湿度:" + "\n" +String.format("%s%%", weatherData.getHourly().get(0).getHumidity()));
        binding.Pop.setText("降水概率:" + "\n" + weatherData.getHourly().get(0).getPop() + "%");
        binding.Wind.setText("风向风力:" + "\n" + weatherData.getHourly().get(0).getWindDir() + " " + weatherData.getHourly().get(0).getWindScale() + "级");
        binding.Pressure.setText("大气压强:" + "\n" +String.format("%shPa", weatherData.getHourly().get(0).getPressure()));
    }

    public void updateRecyclerView(WeatherHourlyBean weatherData) {
        List<WeatherHourlyBean.HourlyBean> hourlyData = weatherData.getHourly();
        RecyclerView recyclerViewMain = getActivity().findViewById(R.id.mainrecyclerView);
        weatherhourAdapter adapter = new weatherhourAdapter(hourlyData, getActivity());
        recyclerViewMain.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void getweatherafterclick(String adcode) {
        mPresenter.getContract().getweatherafterclick(adcode);
    }

    @Override
    public List<Fragment> getDatalist(Context context) {
        return null;
    }

    @Override
    public List<Fragment> getDatalistfuture(Context context) {
        return mPresenter.getContract().getDatalistfuture(context);
    }

    @Override
    public void hefengweather(Context context, String location, toweathercallback toweathercallback) {
        mPresenter.getContract().hefengweather(context, location, toweathercallback);
    }

    @Override
    public void getweather7D(Context context, String location, toweathercallback.callback7d callback) {
        mPresenter.getContract().getweather7D(context, location,callback);
    }
}

