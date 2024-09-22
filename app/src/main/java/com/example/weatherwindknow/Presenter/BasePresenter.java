package com.example.weatherwindknow.Presenter;

import androidx.fragment.app.Fragment;

import com.example.weatherwindknow.Model.BaseModel;
import com.example.weatherwindknow.Model.superBase;
import com.example.weatherwindknow.View.BaseActivity;

public abstract class BasePresenter<M extends BaseModel, V extends BaseActivity, CONTRACT> extends superBase<CONTRACT> {

    protected V mView;
    protected M mModel;


    public BasePresenter() {
        this.mModel = getModelInstance();
        bindView(mView);
    }

    public void bindView (V view){
        this.mView = view;
    }
    public void unbindView(){
        this.mView = null;
    }
    public abstract M getModelInstance();
    public abstract CONTRACT getContract();

}
