package com.example.weatherwindknow.myalwaysact;

import android.content.Intent;
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

import com.example.weatherwindknow.View.MainActivity;
import com.example.weatherwindknow.View.MapActivity;
import com.example.weatherwindknow.databinding.FragmentMyalcityBinding;

import java.util.List;


public class myalcityFragment extends Fragment {
    FragmentMyalcityBinding binding;
    List<alcity> malcityList;
    private alcityDbHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMyalcityBinding.inflate(inflater, container, false);
        dbHelper = alcityDbHelper.getInstance(getActivity());
        initListeners();
        initViews();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbHelper = alcityDbHelper.getInstance(getActivity());
        RecyclerView recyclerView = binding.recyclerView;

        malcityList = dbHelper.queryRegisterListData();
        if (malcityList.size() == 0) {
            malcityList.add(null);
        }
        alcityAdapter alcityAdapter = new alcityAdapter(malcityList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());//添加布局管理器
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);//设置布局管理器
        recyclerView.setAdapter(alcityAdapter);
    }
    void initViews() {

    }
    @Override
    public void onStart() {
        super.onStart();
        dbHelper = alcityDbHelper.getInstance(getActivity());
        RecyclerView recyclerView = binding.recyclerView;

        malcityList = dbHelper.queryRegisterListData();
        if (malcityList.size() == 0) {
            malcityList.add(null);
        }

        alcityAdapter alcityAdapter = new alcityAdapter(malcityList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());//添加布局管理器
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);//设置布局管理器
        recyclerView.setAdapter(alcityAdapter);
    }
    void initListeners() {
        binding.cityadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}