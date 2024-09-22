package com.example.weatherwindknow.View;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.weatherwindknow.Presenter.BasePresenter;
import com.example.weatherwindknow.myalwaysact.alcity;
import com.example.weatherwindknow.myalwaysact.alcityDbHelper;
import com.example.weatherwindknow.mymain.mainPresenter;
import com.example.weatherwindknow.mymain.toweathercallback;
import com.qweather.sdk.bean.weather.WeatherDailyBean;
import com.qweather.sdk.bean.weather.WeatherHourlyBean;
import com.qweather.sdk.view.HeConfig;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class BaseActivity<P extends BasePresenter, CONTRACT> extends AppCompatActivity implements AMapLocationListener {
    public abstract CONTRACT getContract();

    public P mPresenter;
    private String cityCode = null;
    public String adcode;
    public String location;
    WeatherDailyBean mweatherDataDaily;
    WeatherHourlyBean mweatherDataHourly;
    public  alcityDbHelper dbHelper;
    private String city;
    private ActivityResultLauncher<String> requestPermission;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    String map;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewID());
        dbHelper = alcityDbHelper.getInstance(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        initView();
        initDate();
//        initListener();
        init();
        mPresenter = getPresenterInstance();
        mPresenter.bindView(this);
    }
//    private void mustdo() {
//
//    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            );
        }
    }

    protected abstract void initDate();

    public abstract void initView();

    public abstract void initListener();

    private void init() {
        requestPermission = registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
            if (result) {
                showMsg("权限申请成功");
            } else {
                showMsg("权限申请失败");
            }
        });
        HeConfig.init("HE2408270908451726", "8a7e163d5a1d473e89006145e8209d89");
        //切换至免费订阅
        HeConfig.switchToDevService();
        initLocation();
        uppDate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 检查是否已经获取到定位权限
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //开始
            startLocation();
        } else {
            // 请求定位权限
            requestPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    private void initLocation() {
        //初始化定位
        try {
            //初始化定位
            mLocationClient = new AMapLocationClient(getApplicationContext());
            //设置定位回调监听
            mLocationClient.setLocationListener(this);
            //初始化AMapLocationClientOption对象
            mLocationOption = new AMapLocationClientOption();
            //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //获取最近3s内精度最高的一次定位结果
            mLocationOption.setOnceLocationLatest(true);
            //设置是否返回地址信息（默认返回地址信息）
            mLocationOption.setNeedAddress(true);
            //设置定位超时时间，单位是毫秒
            mLocationOption.setHttpTimeOut(6000);
            //给定位客户端对象设置定位参数
            mLocationClient.setLocationOption(mLocationOption);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void startLocation() {
        //开始定位
        if (mLocationClient != null) mLocationClient.startLocation();
    }

    private void stopLocation() {
        //停止定位
        if (mLocationClient != null) mLocationClient.stopLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation == null) {
            showMsg("定位失败，aMapLocation 为空");
            return;
        }
        // 获取定位结果
        if (aMapLocation.getErrorCode() == 0) {
            // 定位成功
            showMsg("定位成功");
            aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
            aMapLocation.getLatitude();//获取纬度
            aMapLocation.getLongitude();//获取经度
            aMapLocation.getAccuracy();//获取精度信息
            aMapLocation.getAddress();//详细地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
            aMapLocation.getCountry();//国家信息
            aMapLocation.getProvince();//省信息
            aMapLocation.getCity();//城市信息
            String result = aMapLocation.getDistrict();//城区信息
            aMapLocation.getStreet();//街道信息
            aMapLocation.getStreetNum();//街道门牌号信息
            aMapLocation.getCityCode();//城市编码
            aMapLocation.getAdCode();//地区编码
            aMapLocation.getAoiName();//获取当前定位点的AOI信息
            aMapLocation.getBuildingId();//获取当前室内定位的建筑物Id
            aMapLocation.getFloor();//获取当前室内定位的楼层
            aMapLocation.getGpsAccuracyStatus();//获取GPS的当前状态
            // 停止定位
            stopLocation();

            // 城市编码赋值
            cityCode = aMapLocation.getCityCode();
            adcode = aMapLocation.getAdCode();
            //城市
            city = aMapLocation.getCity();
            double latitude = aMapLocation.getLatitude();
            double longitude = aMapLocation.getLongitude();
            location = String.format("%.2f,%.2f", longitude, latitude);
            mainPresenter Presenter = new mainPresenter();
            map = aMapLocation.getCity() +"\n"+result + aMapLocation.getStreet();
            Log.d("map 1 baseactivity", map);
            Presenter.getContract().getweather7D(this, location, new toweathercallback.callback7d() {
                @Override
                public void getweather7D(WeatherDailyBean weatherData) {
                    mweatherDataDaily = weatherData;
                    Presenter.getContract().hefengweather(BaseActivity.this, location, new toweathercallback() {
                        @Override
                        public void onWeatherRetrieved(WeatherHourlyBean weatherData) {
                            mweatherDataHourly = weatherData;
                            dbHelper.updateByAdcode(adcode, location, map, mweatherDataHourly, mweatherDataDaily);
                        }
                    });
                }
            });
        } else {
            // 定位失败
            showMsg("定位失败，错误：" + aMapLocation.getErrorInfo());
        }
    }
    public void uppDate(){
        mainPresenter Presenter = new mainPresenter();
        List<alcity> list = dbHelper.queryRegisterListData();
        ExecutorService executor = Executors.newFixedThreadPool(list.size());
        for (alcity item : list) {
            executor.submit(() -> {
                Presenter.getContract().getweather7D(this, item.getLocation(), new toweathercallback.callback7d() {
                    @Override
                    public void getweather7D(WeatherDailyBean weatherData) {
                        item.setmWeatherDailyBean(weatherData);
                        Presenter.getContract().hefengweather(BaseActivity.this, item.getLocation(), new toweathercallback() {
                            @Override
                            public void onWeatherRetrieved(WeatherHourlyBean weatherData) {
                                item.setMweatherHourlyBean(weatherData);
                                synchronized (dbHelper) {  // 确保线程安全
                                    dbHelper.updateByAlCity(item);
                                }
                            }
                        });
                    }
                });
            });

        }
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }

    private void showMsg(CharSequence llw) {
        Toast.makeText(this, llw, Toast.LENGTH_SHORT).show();
    }


    public abstract int getContentViewID();

    public abstract P getPresenterInstance();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unbindView();
    }


}