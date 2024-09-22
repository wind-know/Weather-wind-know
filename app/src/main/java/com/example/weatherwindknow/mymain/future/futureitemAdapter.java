package com.example.weatherwindknow.mymain.future;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherwindknow.R;
import com.example.weatherwindknow.databinding.ItemAlcityBinding;
import com.example.weatherwindknow.databinding.ItemFutureBinding;
import com.example.weatherwindknow.myalwaysact.alcity;
import com.example.weatherwindknow.mymain.today.forcastFragment;
import com.qweather.sdk.bean.weather.WeatherDailyBean;

import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class futureitemAdapter extends RecyclerView.Adapter<futureitemAdapter.myViewHolder> {
    List<WeatherDailyBean.DailyBean> malcityList;

    public futureitemAdapter(List<WeatherDailyBean.DailyBean> alcityList) {
        this.malcityList = alcityList;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemFutureBinding binding = ItemFutureBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new myViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        WeatherDailyBean.DailyBean weatherDailyBean = malcityList.get(position);
        holder.setdate(weatherDailyBean);
        holder.binding.futurecardView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.recylegundong));
    }

    @Override
    public int getItemCount() {
        if (malcityList == null)
            return 0;
        return malcityList.size();
    }

    static class myViewHolder extends RecyclerView.ViewHolder {
        private final ItemFutureBinding binding;

        public myViewHolder(ItemFutureBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setdate(WeatherDailyBean.DailyBean weatherDailyBean) {
            binding.futureTime.setText(weatherDailyBean.getFxDate());
            binding.futureWeather.setText(weatherDailyBean.getTextDay());
            binding.futureTemp.setText(weatherDailyBean.getTempMax());

        }
    }
}
