package com.example.weatherwindknow.mymain.future;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.weatherwindknow.R;
import com.example.weatherwindknow.databinding.FragmentFutureitemBinding;
import com.example.weatherwindknow.databinding.FragmentMyBinding;
import com.example.weatherwindknow.myalwaysact.alcity;
import com.example.weatherwindknow.myalwaysact.alcityDbHelper;
import com.example.weatherwindknow.mymain.mainPresenter;
import com.example.weatherwindknow.mymain.today.myFragment;
import com.example.weatherwindknow.mymain.today.weatherhourAdapter;
import com.example.weatherwindknow.mymain.toweathercallback;
import com.example.weatherwindknow.myservice.MyView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.qweather.sdk.bean.weather.WeatherDailyBean;
import com.qweather.sdk.bean.weather.WeatherHourlyBean;

public class futureitemFragment extends Fragment {
    FragmentFutureitemBinding binding;
    public alcity alcity;
    public WeatherDailyBean mWeatherDailyBean;
    public alcityDbHelper dbHelper;
    public futureitemFragment(WeatherDailyBean mWeatherDailyBean,alcity alcity) {
        super();
        this.mWeatherDailyBean = mWeatherDailyBean;
        this.alcity = alcity;
    }
    public futureitemFragment() {
        super();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFutureitemBinding.inflate(inflater, container, false);
        initListeners();
        initViews();
        dbHelper = alcityDbHelper.getInstance(getActivity());
        return binding.getRoot();
    }

    void initViews() {
        RecyclerView recyclerViewMain = binding.futurerecyclerview;
        binding.futurecity.setText(alcity.getMap());
        futureitemAdapter futureitemAdapter = new futureitemAdapter(mWeatherDailyBean.getDaily());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());//添加布局管理器
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMain.setLayoutManager(layoutManager);//设置布局管理器
        recyclerViewMain.setAdapter(futureitemAdapter);
    }
    void initListeners() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}