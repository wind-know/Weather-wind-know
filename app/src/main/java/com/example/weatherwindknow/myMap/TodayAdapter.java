package com.example.weatherwindknow.myMap;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherwindknow.databinding.ItemTodayDetailBinding;
import com.example.weatherwindknow.myservice.Lives;

import java.util.List;

public class TodayAdapter extends RecyclerView.Adapter<TodayAdapter.TodayViewHolder> {
    private List<Lives> livesList;

    public TodayAdapter(List<Lives> livesList) {
        this.livesList = livesList;
    }

    @NonNull
    @Override
    public TodayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTodayDetailBinding binding = ItemTodayDetailBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TodayViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TodayViewHolder holder, int position) {
        Lives lives = livesList.get(position);
        holder.binding.tvName.setText(lives.getWeather());
        holder.binding.tvValue.setText(lives.getTemperature());
//        int iconResId = getWeatherIconResId();
//        holder.binding.ivIcon.setImageResource(iconResId);
    }

    @Override
    public int getItemCount() {
        return livesList.size();
    }

    static class TodayViewHolder extends RecyclerView.ViewHolder {
        private final ItemTodayDetailBinding binding; // 使用具体的Binding类名

        public TodayViewHolder(ItemTodayDetailBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }
}