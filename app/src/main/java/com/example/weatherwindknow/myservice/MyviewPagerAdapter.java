package com.example.weatherwindknow.myservice;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class MyviewPagerAdapter extends FragmentStateAdapter {
    List<Fragment> List;
    public MyviewPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> fragmentList) {
        super(fragmentActivity);
        this.List = fragmentList;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return List.get(position);
    }
    @Override
    public int getItemCount() {
        return List != null ? List.size() : 0;
    }
}
