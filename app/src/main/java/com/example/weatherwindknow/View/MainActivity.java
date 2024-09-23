package com.example.weatherwindknow.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.weatherwindknow.R;
import com.example.weatherwindknow.databinding.ActivityMainBinding;
import com.example.weatherwindknow.myalwaysact.myalcityFragment;
import com.example.weatherwindknow.mymain.Imain;
import com.example.weatherwindknow.mymain.future.futureFragment;
import com.example.weatherwindknow.mymain.today.forcastFragment;
import com.example.weatherwindknow.mymain.mainPresenter;
import com.example.weatherwindknow.mymain.toweathercallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends BaseActivity<mainPresenter, Imain.Vp> {

    private BottomNavigationView bottomNavigation;
    private ActivityMainBinding binding;

    @Override
    public Imain.Vp getContract() {
        return new Imain.Vp() {
            @Override
            public void getweatherafterclick(String adcode) {
                mPresenter.getContract().getweatherafterclick(adcode);
            }

            @Override
            public List<Fragment> getDatalist(Context context) {
                return mPresenter.getContract().getDatalist(MainActivity.this);
            }

            @Override
            public List<Fragment> getDatalistfuture(Context context) {
                return mPresenter.getContract().getDatalistfuture(context);
            }

            @Override
            public void hefengweather(Context context, String location, toweathercallback toweathercallback) {
                Log.e("hefeng",location);
                mPresenter.getContract().hefengweather(context, location,toweathercallback);
            }
            @Override
            public void getweather7D(Context context, String location, toweathercallback.callback7d callback) {
                mPresenter.getContract().getweather7D(context, location,callback);
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
        initListener();
    }

    @Override
    protected void initDate() {

    }

    @Override
    public void initView() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragmentContainerView, new forcastFragment());
        ft.commit();
        bottomNavigation = binding.bottomNavigation;
    }

    @Override
    public void initListener() {
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.item1) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragmentContainerView, new forcastFragment());
                ft.commit();
            } else if (item.getItemId() == R.id.item2) {
                FragmentManager fm2 = getSupportFragmentManager();
                FragmentTransaction ft2 = fm2.beginTransaction();
                ft2.replace(R.id.fragmentContainerView, new myalcityFragment());
                ft2.commit();
            } else if (item.getItemId() == R.id.item3) {
                FragmentManager fm3 = getSupportFragmentManager();
                FragmentTransaction ft3 = fm3.beginTransaction();
                ft3.replace(R.id.fragmentContainerView, new futureFragment());
                ft3.commit();

            }
            return true;
        });
    }

    @Override
    public int getContentViewID() {
        return R.layout.activity_main;
    }

    @Override
    public mainPresenter getPresenterInstance() {
        return new mainPresenter();
    }

}