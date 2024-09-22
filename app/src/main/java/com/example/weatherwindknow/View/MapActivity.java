package com.example.weatherwindknow.View;


import static com.example.weatherwindknow.myMap.ThreeDailyAdapter.getWeatherIconResId;
import static com.qweather.sdk.view.HeContext.context;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;

import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.example.weatherwindknow.R;
import com.example.weatherwindknow.databinding.ActivityMainBinding;
import com.example.weatherwindknow.databinding.ActivityMapBinding;
import com.example.weatherwindknow.myMap.ThreeDailyAdapter;
import com.example.weatherwindknow.myMap.TodayAdapter;
import com.example.weatherwindknow.myMap.help.HourlyResponse;
import com.example.weatherwindknow.myMap.horizonview.HourlyForecastView;
import com.example.weatherwindknow.myMap.horizonview.IndexHorizontalScrollView;
import com.example.weatherwindknow.myalwaysact.alcityDbHelper;
import com.example.weatherwindknow.mymain.mainPresenter;
import com.example.weatherwindknow.mymain.toweathercallback;
import com.example.weatherwindknow.myservice.Lives;
import com.example.weatherwindknow.myservice.Root;
import com.example.weatherwindknow.myservice.WeatherDataCallback;
import com.example.weatherwindknow.myservice.WeatherServiceHelper;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.qweather.sdk.bean.base.Lang;
import com.qweather.sdk.bean.geo.GeoBean;
import com.qweather.sdk.bean.weather.WeatherDailyBean;
import com.qweather.sdk.bean.weather.WeatherHourlyBean;
import com.qweather.sdk.view.HeConfig;
import com.qweather.sdk.view.QWeather;

import java.util.ArrayList;
import java.util.List;import android.os.Handler;

public class MapActivity extends AppCompatActivity implements AMapLocationListener, LocationSource, PoiSearch.OnPoiSearchListener, AMap.OnMapLongClickListener, AMap.OnMapClickListener, GeocodeSearch.OnGeocodeSearchListener {
    private ActivityMapBinding binding;
    public alcityDbHelper dbHelper;
    private static final String TAG = "MapActivity";
    // 请求权限意图
    private ActivityResultLauncher<String> requestPermission;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    // 声明地图控制器
    private AMap aMap = null;
    // 声明地图定位监听
    private LocationSource.OnLocationChangedListener mListener = null;

    //POI查询对象
    private PoiSearch.Query query;
    //POI搜索对象
    private PoiSearch poiSearch;
    //城市码
    private String cityCode = null;

    //地理编码
    //地理编码搜索
    private GeocodeSearch geocodeSearch;
    //解析成功标识码
    private static final int PARSE_SUCCESS_CODE = 1000;

    //城市
    private String city;
    private String map;
    //标点列表
    private final List<Marker> markerList = new ArrayList<>();
    private String adcode;
    private String location;
    List<String> addresses;
    WeatherDailyBean mweatherDataDaily;
    WeatherHourlyBean mweatherDataHourly;
    private mainPresenter mPresenter;
    private AutoTransition autoTransition;//过渡动画
    private Animation bigShowAnim;//放大显示
    private Animation smallHideAnim;//缩小隐藏
    private int width;//屏幕宽度
    private boolean isOpen = false;//顶部搜索布局的状态
    private BottomSheetBehavior bottomSheetBehavior = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //
        //权限请求
        //
        requestPermission = registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
            // 权限申请结果
            Log.d(TAG, "权限申请结果: " + result);
            if (result) {
                // 权限申请成功
                Log.d(TAG, "权限申请成功");
                showMsg("权限申请成功");
            } else {
                // 权限申请失败
                Log.d(TAG, "权限申请失败");
                showMsg("权限申请失败");
            }
        });
        HeConfig.init("HE2408270908451726", "8a7e163d5a1d473e89006145e8209d89");
        //切换至免费订阅
        HeConfig.switchToDevService();
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMapBinding.inflate(getLayoutInflater());
        super.setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.map), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //初始化定位
        initLocation();
        binding.mapView.onCreate(savedInstanceState);
        //c初始化地图
        initMap();
        //初始化按钮
        initView();
        //初始化搜索
        initSearch();
    }
    // 当窗口获得或失去焦点时，隐藏系统UI
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
    @Override
    protected void onResume() {
        super.onResume();
        binding.mapView.onResume();
        // 检查是否已经获取到定位权限
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // 获取到权限
            Log.d(TAG, "onResume: 已获取到权限");
            //开始
            startLocation();
        } else {
            // 请求定位权限
            requestPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        // 绑定生命周期 onPause
        binding.mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // 绑定生命周期 onSaveInstanceState
        binding.mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 绑定生命周期 onDestroy
        binding.mapView.onDestroy();
    }
    //
    //1/初始化定位
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
    //
    //2/显示当前定位地图
    private void initMap() {
        //初始化地图
        if (aMap == null) {
            aMap = binding.mapView.getMap();
            // 设置定位监听
            aMap.setLocationSource(this);
            // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
            aMap.setMyLocationEnabled(true);

            //设置最小缩放等级为12 ，缩放级别范围为[3, 20]
            //aMap.setMinZoomLevel(20);
            //aMap.setMaxZoomLevel(3);
            // 设置默认缩放级别为 15
            aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
            // 开启室内地图
            aMap.showIndoorMap(true);

            //
            //4/设置地图点击事件
            // 设置地图点击事件
            aMap.setOnMapClickListener(this);
            // 设置地图长按事件
            aMap.setOnMapLongClickListener(this);

            // 地图控件设置
            UiSettings uiSettings = aMap.getUiSettings();
            // 隐藏缩放按钮
            uiSettings.setZoomControlsEnabled(false);
            // 显示比例尺，默认不显示
            uiSettings.setScaleControlsEnabled(true);

        }

    }
    //---------------------------------------------------------------------------------------------------------------
    //展开关闭
    // dp 转成 px
    private int dip2px(float dpVale) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpVale * scale + 0.5f);
    }
    // px 转成 dp
    private int px2dip(float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void beginDelayedTransition(ViewGroup view) {
        autoTransition = new AutoTransition();
        autoTransition.setDuration(200);
        TransitionManager.beginDelayedTransition(view,autoTransition);
    }
    //关于点击展开
    public void initExpand() {
        if(context!=null) {
            isOpen = true;
            binding.edSearch.setVisibility(View.VISIBLE);//显示输入框
            binding.ivClose.setVisibility(View.VISIBLE);//显示关闭按钮
            LinearLayout.LayoutParams LayoutParams = (LinearLayout.LayoutParams) binding.laySearch.getLayoutParams();
            LayoutParams.width = dip2px(px2dip(width) - 24);//设置展开的宽度
            LayoutParams.setMargins(dip2px(0), dip2px(0), dip2px(0), dip2px(0));
            binding.laySearch.setPadding(14, 0, 14, 0);
            binding.laySearch.setLayoutParams(LayoutParams);

            //开始动画
            beginDelayedTransition(binding.laySearch);
        }
    }
    //关闭
    private void initClose() {
        if(context!=null) {
            isOpen = false;
            binding.edSearch.setVisibility(View.GONE);
            binding.edSearch.setText("");
            binding.ivClose.setVisibility(View.GONE);
            LinearLayout.LayoutParams LayoutParams = (LinearLayout.LayoutParams) binding.laySearch.getLayoutParams();
            LayoutParams.width = dip2px(48);
            LayoutParams.height = dip2px(48);
            LayoutParams.setMargins(dip2px(0), dip2px(0), dip2px(0), dip2px(0));
            binding.laySearch.setLayoutParams(LayoutParams);
            //隐藏键盘
            InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(this.getWindow().getDecorView().getWindowToken(), 0);
            //开始动画
            beginDelayedTransition(binding.laySearch);
        }
    }
    //---------------------------------------------------------------------------------------------------------------
    //模糊搜索
    private void searchGeoLocation(String location) {
        Lang lang = Lang.ZH_HANS;
        QWeather.getGeoCityLookup(context, location, 20, lang, new QWeather.OnResultGeoListener() {
                    @Override
                    public void onError(Throwable throwable) {
                        if (Looper.myLooper() == Looper.getMainLooper()) {
                            Toast.makeText(context, "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("GeoCityLookup", "Error: " + throwable.getMessage());
                            runOnUiThread(() -> Toast.makeText(context,  throwable.getMessage(), Toast.LENGTH_SHORT).show());
                        }
                    }
                    @Override
                    public void onSuccess(GeoBean geoBean) {
                        List<String> addresses = new ArrayList<>();
                        geoBean.getLocationBean().forEach(locationBean -> {
                            String address = locationBean.getAdm1() + " / " + locationBean.getAdm2() + "市 /" + locationBean.getName();
                            addresses.add(address);
                        });
                        runOnUiThread(() -> {
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.search_item, addresses);
                            binding.edSearch.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        });
                    }
                });
    }
    private void performSearch(String address) {
        if (address.isEmpty()) {
            showMsg("请输入地址");
        } else {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            // 隐藏软键盘
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);

            // Geocode 查询
            GeocodeQuery query = new GeocodeQuery(address, null);
            geocodeSearch.getFromLocationNameAsyn(query);
        }
    }
    //---------------------------------------------------------------------------------------------------------------
    private void initView() {
        //获取屏幕宽高
        WindowManager manager = getWindowManager();
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;  //获取屏幕的宽度 像素


        Handler handler = new Handler(Looper.getMainLooper());
        long delayInMillis = 500;
        binding.edSearch.addTextChangedListener(new TextWatcher() {
            private Runnable searchTask;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 取消之前的搜索任务
                if (searchTask != null) {
                    handler.removeCallbacks(searchTask);
                }
                if (s.length() >= 2) {
                    searchTask = () -> searchGeoLocation(s.toString());
                    handler.postDelayed(searchTask, delayInMillis); // 延迟一段时间后执行
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        binding.edSearch.setThreshold(1);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, addresses);
        binding.edSearch.setAdapter(adapter);
        binding.edSearch.setOnItemClickListener((parent, view, position, id) -> {
            String selectedAddress = (String) parent.getItemAtPosition(position);
            binding.edSearch.setText(selectedAddress);
            performSearch(selectedAddress);
        });

        binding.fabPoi.setOnClickListener(v -> {
            showMsg("添加中....");
            mPresenter = new mainPresenter();
            mPresenter.getContract().getweather7D(this, location, new toweathercallback.callback7d() {
                @Override
                public void getweather7D(WeatherDailyBean weatherData) {
                    mweatherDataDaily = weatherData;
                    mPresenter.getContract().hefengweather(MapActivity.this, location, new toweathercallback() {
                        @Override
                        public void onWeatherRetrieved(WeatherHourlyBean weatherData) {
                            mweatherDataHourly = weatherData;
                            dbHelper = alcityDbHelper.getInstance(MapActivity.this);
                            dbHelper.updateByAdcode(adcode, location , map , mweatherDataHourly, mweatherDataDaily);
                            runOnUiThread(() -> {
                                showMsg("添加成功");
                            });
                        }
                    });
                }
            });
        });
        //放大
        bigShowAnim = AnimationUtils.loadAnimation(MapActivity.this, R.anim.scale_big_expand);
        //缩小
        smallHideAnim = AnimationUtils.loadAnimation(MapActivity.this, R.anim.scale_small_close);

        binding.ivClose.setOnClickListener(v -> {
            if (isOpen) {
                initClose();
            }
        });
        binding.ivSearch.setOnClickListener(v -> {
            if (!isOpen) {
                initExpand();
            }
        });

        // 键盘按键监听
        binding.edSearch.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                //获取输入框的值
                String address1 = binding.edSearch.getText().toString().trim();
                performSearch(address1);
                return true;
            }
            return false;
        });
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheetRay);
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        scaleAnimation(binding.laySearch, "show"); // 显示顶部搜索布局
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        if (isOpen) {
                            initClose(); // 先收缩
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    scaleAnimation(binding.laySearch, "hide");
                                }
                            }, 200);
                        } else {
                            scaleAnimation(binding.laySearch, "hide"); // 直接隐藏搜索布局
                        }
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        binding.rvThreeDayDaily.stopNestedScroll();
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        binding.rvThreeDayDaily.startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
                        break;

                }
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
        binding.rvThreeDayDaily.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                binding.netscrollView.requestDisallowInterceptTouchEvent(true);
            }
        });
    }

    //
    //5/地理编码
    /**
     * 初始化搜索
     */
    private void initSearch() {
        // 构造 GeocodeSearch 对象
        try {
            geocodeSearch = new GeocodeSearch(this);
            // 设置监听
            geocodeSearch.setOnGeocodeSearchListener(this);
        } catch ( com.amap.api.services.core.AMapException e) {
            e.printStackTrace();
        }
    }
    private void scaleAnimation(View view,String state) {

        switch (state){
            case "show":
                view.startAnimation(bigShowAnim);
                view.setVisibility(View.VISIBLE);
                break;
            case "hide":
                view.startAnimation(smallHideAnim);
                smallHideAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        view.setVisibility(View.GONE);
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                break;
        }
    }

    //
    //1/初始化定位
    private void startLocation() {
        //开始定位
        if (mLocationClient != null) mLocationClient.startLocation();
    }
    private void stopLocation() {
        //停止定位
        if (mLocationClient != null) mLocationClient.stopLocation();
    }


    private void showMsg(CharSequence llw) {
        Toast.makeText(this, llw, Toast.LENGTH_SHORT).show();
    }

    //
    //1/初始化定位
    //定位回调结果
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation == null) {
            showMsg("定位失败，aMapLocation 为空");
            return;
        }
        // 获取定位结果
        if (aMapLocation.getErrorCode() == 0) {
            // 定位成功
//            showMsg("定位成功");
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
            // 显示地图定位结果
            if (mListener != null) {
                mListener.onLocationChanged(aMapLocation);
            }

            // 显示浮动按钮
            binding.fabPoi.show();
            // 城市编码赋值
            cityCode = aMapLocation.getCityCode();
            adcode = aMapLocation.getAdCode();
            //城市
            city = aMapLocation.getCity();
            double latitude = aMapLocation.getLatitude();
            double longitude = aMapLocation.getLongitude();
            location = String.format("%.2f,%.2f", longitude, latitude);
            map = aMapLocation.getCity()+"\n"+result;

            getweatherafterclick();

        } else {
            // 定位失败
            showMsg("定位失败，错误：" + aMapLocation.getErrorInfo());
            Log.e(TAG,"location Error, ErrCode:"
                    + aMapLocation.getErrorCode() + ", errInfo:"
                    + aMapLocation.getErrorInfo());
        }
    }

    //
    //2/显示当前定位地图
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        /**
         * 激活定位
         * @param onLocationChangedListener
         */
        if (mListener == null) {
            mListener = onLocationChangedListener;
        }
        startLocation();
    }
    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }


    //
    //3/设置poi点击事件
    /**
     * POI搜索返回
     *
     * @param poiResult POI所有数据
     * @param i
     */
    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        //解析result获取POI信息

        //获取POI组数列表
        ArrayList<PoiItem> poiItems = poiResult.getPois();
        for (PoiItem poiItem : poiItems) {
            Log.d("MapActivity", " Title：" + poiItem.getTitle() + " Snippet：" + poiItem.getSnippet());
        }
    }

    /**
     * POI中的项目搜索返回
     *
     * @param poiItem 获取POI item
     * @param i
     */
    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    //
    //5/地理编码
    /**
     * 通过经纬度获取地址
     * @param latLng//地理坐标点
     */
    private void latLonToAddress(LatLng latLng) {
//        通过经纬度构建LatLonPoint对象，
//        然后构建RegeocodeQuery时，传入，并且输入另外两个参数，范围和坐标系。
//        最后通过geocodeSearch发起一个异步的请求，以获取给定经纬度的地址信息。
        //位置点  通过经纬度进行构建
        LatLonPoint latLonPoint = new LatLonPoint(latLng.latitude, latLng.longitude);
        location = latLng.longitude+","+latLng.latitude;
        //逆编码查询  第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 20, GeocodeSearch.AMAP);
        //异步获取地址信息
        geocodeSearch.getFromLocationAsyn(query);
    }

    //
    //4/地图点击事件
    /**
     * 地图点击事件
     * @param latLng
     */
    @Override
    public void onMapClick(LatLng latLng) {
        //showMsg("点击了地图，经度："+latLng.longitude+"，纬度："+latLng.latitude);
        latLonToAddress(latLng);
        updateMapmark(latLng);
    }
    public void getweatherafterclick(){
        //显示底部sheet
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        WeatherServiceHelper helper = new WeatherServiceHelper();
        helper.getWeatherDataAsync("f989802809dde6b0aa0d39c66075ba6c", adcode,"all", new WeatherDataCallback() {
            @Override
            public void onSuccess(Root weatherData) {
                MapActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        String temperature =weatherData.getForecasts().get(0).getCasts().get(0).getDayweather();
//                        Toast.makeText(MapActivity.this, temperature, Toast.LENGTH_SHORT).show();
                        updateAdapter(weatherData);
                        hefengweather(getApplicationContext());
                    }
                });
            }

            @Override
            public void onError(Throwable error) {
                // 使用 runOnUiThread 确保在主线程上处理错误
                MapActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 显示错误信息，例如通过 Toast 或 Dialog
                        Toast.makeText(MapActivity.this, "请求失败: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private List<HourlyResponse.HourlyBean> convertHourlyBeans(List<WeatherHourlyBean.HourlyBean> sourceList) {
        List<HourlyResponse.HourlyBean> resultList = new ArrayList<>();
        for (WeatherHourlyBean.HourlyBean sourceBean : sourceList) {
            HourlyResponse.HourlyBean targetBean = new HourlyResponse.HourlyBean();
            // 复制属性
            targetBean.setFxTime(sourceBean.getFxTime());
            targetBean.setTemp(sourceBean.getTemp());
            targetBean.setIcon(sourceBean.getIcon());
            targetBean.setText(sourceBean.getText());
            targetBean.setWindSpeed(sourceBean.getWindSpeed());
            targetBean.setWindDir(sourceBean.getWindDir());
            targetBean.setWindScale(sourceBean.getWindScale());
            targetBean.setWind360(sourceBean.getWind360());
            targetBean.setPrecip(sourceBean.getPrecip());
            targetBean.setHumidity(sourceBean.getHumidity());
            targetBean.setPressure(sourceBean.getPressure());
            targetBean.setPop(sourceBean.getPop());
            targetBean.setCloud(sourceBean.getCloud());
            targetBean.setDew(sourceBean.getDew());
            resultList.add(targetBean);
        }
        return resultList;
    }

    public void hefengweather(Context context) {
        QWeather.getWeather24Hourly(context, location, new QWeather.OnResultWeatherHourlyListener() {
            @Override
            public void onError(Throwable throwable) {
                String errorMessage = throwable.getMessage();

                Log.d("TAGerr", String.valueOf(throwable));
                Log.d("TAGerr", "Error: " + errorMessage);
            }

            @Override
            public void onSuccess(WeatherHourlyBean weatherHourlyBean) {
                Log.d("TAGok", "ok");
                TextView tvLineMaxTmp = binding.tvLineMaxTmp;//今日最高温
                TextView tvLineMinTmp = binding.tvLineMinTmp;//今日最低温
                HourlyForecastView hourly = binding.hourly;//和风自定义逐小时天气渲染控件
                IndexHorizontalScrollView hsv = binding.hsv;//和风自定义滚动条
                List<HourlyResponse.HourlyBean> hourlyWeatherList = convertHourlyBeans(weatherHourlyBean.getHourly());
                List<HourlyResponse.HourlyBean> data = new ArrayList<>();
                if (hourlyWeatherList.size() > 23) {
                    for (int i = 0; i < 24; i++) {
                        data.add(hourlyWeatherList.get(i));
                        String condCode = data.get(i).getIcon();
                        String time = data.get(i).getFxTime();
                        time = time.substring(time.length() - 11, time.length() - 9);
                        int hourNow = Integer.parseInt(time);
                        if (hourNow >= 6 && hourNow <= 19) {
                            data.get(i).setIcon(condCode + "d");
                        } else {
                            data.get(i).setIcon(condCode + "n");
                        }
                    }
                }else {
                    for (int i = 0; i < hourlyWeatherList.size(); i++) {
                        data.add(hourlyWeatherList.get(i));
                        String condCode = data.get(i).getIcon();
                        String time = data.get(i).getFxTime();
                        time = time.substring(time.length() - 11, time.length() - 9);
                        int hourNow = Integer.parseInt(time);
                        if (hourNow >= 6 && hourNow <= 19) {
                            data.get(i).setIcon(condCode + "d");
                        } else {
                            data.get(i).setIcon(condCode + "n");
                        }
                    }
                }

                int minTmp = Integer.parseInt(data.get(0).getTemp());
                int maxTmp = minTmp;
                for (int i = 0; i < data.size(); i++) {
                    int tmp = Integer.parseInt(data.get(i).getTemp());
                    minTmp = Math.min(tmp, minTmp);
                    maxTmp = Math.max(tmp, maxTmp);
                }
                //设置当天的最高最低温度
                hourly.setHighestTemp(maxTmp);
                hourly.setLowestTemp(minTmp);
                if (maxTmp == minTmp) {
                    hourly.setLowestTemp(minTmp - 1);
                }
                hourly.initData(data);
                int finalMinTmp = minTmp;
                int finalMaxTmp = maxTmp;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvLineMaxTmp.setText(finalMaxTmp + "°");
                        tvLineMinTmp.setText(finalMinTmp + "°");
                    }
                });
            }
        });
    }
    void updateAdapter(Root weatherData) {
        updatecord(weatherData);

        RecyclerView recyclerViewTodayDetail = binding.rvTodayDetail;
        RecyclerView recyclerViewSevenDayDaily = binding.rvThreeDayDaily;

        recyclerViewTodayDetail.setLayoutManager(new GridLayoutManager(this, 2)); // 2 表示每行显示2个项目
        recyclerViewSevenDayDaily.setLayoutManager(new LinearLayoutManager(this));

        TodayAdapter todayAdapter = new TodayAdapter(weatherData.getLives());
        recyclerViewTodayDetail.setAdapter(todayAdapter);

        ThreeDailyAdapter ThreeDailyAdapter = new ThreeDailyAdapter(weatherData.getForecasts().get(0).getCasts());
        recyclerViewSevenDayDaily.setNestedScrollingEnabled(true);
        recyclerViewSevenDayDaily.setAdapter(ThreeDailyAdapter);
    }
    void updatecord(Root weatherDataall){
        Lives weatherData = weatherDataall.getLives().get(0);
        // 更新城市名称
        binding.tvCity.setText(weatherData.getProvince()+" "+weatherData.getCity());

        // 更新温度
//        binding.tvTemperature.setText(weatherData.getTemperature_float()+"℃");
        binding.tvTemperature.setText(weatherData.getTemperature()+"℃");
        // 更新天气状态
        binding.tvWeatherStateTv.setText(weatherData.getWeather());

        // 更新天气状态图片，您可能需要根据天气状态来设置图片资源
        int weatherImageResId = getWeatherIconResId(weatherData.getWeather());
        binding.ivWeather.setImageResource(weatherImageResId);

        // 更新空气质量
        binding.tvAir.setText("空气湿度: " + weatherData.getHumidity_float());

        // 更新风力信息
        binding.tvWindInfo.setText("风向: " + weatherData.getWinddirection());
        binding.tvWindInfopower.setText("风力: " + weatherData.getWindpower());

        // 更新更新时间
        binding.tvUpdateTime.setText("更新时间: " + weatherData.getReporttime());
    }
    /**
     * 地图长按事件
     * @param latLng
     */
    @Override
    public void onMapLongClick(LatLng latLng) {
        //showMsg("长按了地图，经度："+latLng.longitude+"，纬度："+latLng.latitude);
        latLonToAddress(latLng);
        updateMapmark(latLng);
    }
    /**
     * 改变地图中心位置
     * @param latLng 位置
     */
    private void updateMapCenter(LatLng latLng) {
        // CameraPosition 第一个参数： 目标位置的屏幕中心点经纬度坐标。
        // CameraPosition 第二个参数： 目标可视区域的缩放级别
        // CameraPosition 第三个参数： 目标可视区域的倾斜度，以角度为单位。
        // CameraPosition 第四个参数： 可视区域指向的方向，以角度为单位，从正北向顺时针方向计算，从0度到360度
        CameraPosition cameraPosition = new CameraPosition(latLng, 16, 30, 0);
        //位置变更
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        //改变位置（使用动画）
        aMap.animateCamera(cameraUpdate);
    }
    //综合移动标点
    private void updateMapmark(LatLng latLng) {
        // 清除地图上的所有标记
        aMap.clear();
        // 添加一个新的标记
        aMap.addMarker(new MarkerOptions()
                .position(latLng)  // 设置标记的位置
                .snippet("DefaultMarker")  // 设置标记的摘要信息
        );
        // 移动或动画到标记的位置
        updateMapCenter(latLng);
    }

    //
    //5/地理编码
    //坐标转地址
    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int rCode) {
        //响应逆地理编码请求的结果
        //解析result获取地址描述信息
        if(rCode == PARSE_SUCCESS_CODE){
            //逆地理编码查询的结果
            RegeocodeAddress regeocodeAddress = regeocodeResult.getRegeocodeAddress();
            adcode = regeocodeAddress.getAdCode();
            map = regeocodeAddress.getCity()+"\n"+regeocodeAddress.getDistrict();
            //显示解析后的地址
            showMsg("地址："+regeocodeAddress.getFormatAddress());
            getweatherafterclick();
        }else {
            showMsg("获取地址失败");
        }
    }

    //地址转坐标
    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int rCode) {
        if (rCode != PARSE_SUCCESS_CODE) {
            showMsg("获取坐标失败");
            return;
        }
        List<GeocodeAddress> geocodeAddressList = geocodeResult.getGeocodeAddressList();
        if (geocodeAddressList != null && !geocodeAddressList.isEmpty()) {
            LatLonPoint latLonPoint = geocodeAddressList.get(0).getLatLonPoint();
//            //显示解析后的坐标
            location = latLonPoint.getLongitude() + "," + latLonPoint.getLatitude();
            adcode = geocodeAddressList.get(0).getAdcode();
            getweatherafterclick();
            updateMapmark(new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude()));
            latLonToAddress(new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude()));
            if(isOpen){
                initClose();
            }
        }
    }
}