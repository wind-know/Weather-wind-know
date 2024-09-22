package com.example.weatherwindknow.mymain.future;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.weatherwindknow.R;
import com.example.weatherwindknow.View.MainActivity;
import com.example.weatherwindknow.databinding.FragmentFutureBinding;
import com.example.weatherwindknow.databinding.FragmentMyBinding;
import com.example.weatherwindknow.myalwaysact.alcity;
import com.example.weatherwindknow.myalwaysact.alcityDbHelper;
import com.example.weatherwindknow.mymain.mainPresenter;
import com.example.weatherwindknow.mymain.today.myFragment;
import com.example.weatherwindknow.mymain.today.weatherhourAdapter;
import com.example.weatherwindknow.mymain.toweathercallback;
import com.example.weatherwindknow.myservice.MyView;
import com.example.weatherwindknow.myservice.MyviewPagerAdapter;
import com.qweather.sdk.bean.weather.WeatherDailyBean;
import com.qweather.sdk.bean.weather.WeatherHourlyBean;

import java.util.List;

import me.relex.circleindicator.CircleIndicator3;


public class futureFragment extends Fragment {
    FragmentFutureBinding binding;

    public alcity alcity;
    public alcityDbHelper dbHelper;

    public futureFragment(alcity alcity) {
        super();
        this.alcity = alcity;
    }

    public futureFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFutureBinding.inflate(inflater, container, false);
        initListeners();
//        initViews();
        dbHelper = alcityDbHelper.getInstance(getActivity());
        return binding.getRoot();
    }

    public void onViewCreated(View view, Bundle state) {
        super.onViewCreated(view, state);
        List<Fragment> fragmentList = ((MainActivity) getActivity()).getContract().getDatalistfuture(getActivity());
        MyviewPagerAdapter adapter = new MyviewPagerAdapter(getActivity(), fragmentList);
        //禁用预加载
        binding.futureviewPager.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);
        binding.futureviewPager.setAdapter(adapter);
        //指示器
        CircleIndicator3 indicator = binding.indicator;
        indicator.setViewPager(binding.futureviewPager);
        adapter.registerAdapterDataObserver(indicator.getAdapterDataObserver());
    }

    void initViews() {

    }

    void initListeners() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}