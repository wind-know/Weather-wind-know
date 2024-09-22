package com.example.weatherwindknow.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.example.weatherwindknow.Presenter.BasePresenter;
import com.example.weatherwindknow.mymain.Imain;
import com.example.weatherwindknow.mymain.mainPresenter;

import java.util.List;

public abstract class Basefragment<P extends BasePresenter,CONTRACT>  extends Fragment{
    ViewBinding binding;
    public abstract CONTRACT getContract();
    public P mPresenter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        binding = FragmentBaseBinding.inflate(inflater,container,false);
//        binding.mv.moveWaterLine();
//        initListeners();
        return binding.getRoot();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
