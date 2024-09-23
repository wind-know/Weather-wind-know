package com.example.weatherwindknow.mymain.today;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.weatherwindknow.View.MainActivity;
import com.example.weatherwindknow.databinding.FragmentForcastBinding;
import com.example.weatherwindknow.myservice.MyviewPagerAdapter;

import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class forcastFragment extends Fragment {
    public FragmentForcastBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentForcastBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    public void onViewCreated(View view, Bundle state) {
        super.onViewCreated(view, state);
        List<Fragment> fragmentList = ((MainActivity)getActivity()).getContract().getDatalist(getActivity());
        Log.e("fragmentList",fragmentList.size()+"");
        MyviewPagerAdapter adapter = new MyviewPagerAdapter((MainActivity)getActivity(),fragmentList);
        //禁用预加载
        binding.viewPager.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);
        binding.viewPager.setAdapter(adapter);
        RecyclerView recyclerViewMain = binding.mainrecyclerView;

        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                myFragment f = (myFragment) fragmentList.get(position);
                weatherhourAdapter weatherhourAdapter = new weatherhourAdapter(f.alcity.getMweatherHourlyBean().getHourly(),getActivity());
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());//添加布局管理器
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerViewMain.setLayoutManager(layoutManager);//设置布局管理器
                recyclerViewMain.setAdapter(weatherhourAdapter);
                f.updateRecyclerView(f.alcity.getMweatherHourlyBean());
            }
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 页面滑动时调用
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                // 页面滑动状态改变时调用
            }
        });
        //指示器
        CircleIndicator3 indicator = binding.indicator;
        indicator.setViewPager(binding.viewPager);
        adapter.registerAdapterDataObserver(indicator.getAdapterDataObserver());
    }
}