package com.example.weatherwindknow.mymain.future;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherwindknow.R;
import com.example.weatherwindknow.databinding.BottomSheetDialogBinding;
import com.example.weatherwindknow.databinding.ItemAlcityBinding;
import com.example.weatherwindknow.databinding.ItemFutureBinding;
import com.example.weatherwindknow.myalwaysact.alcity;
import com.example.weatherwindknow.mymain.today.forcastFragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.qweather.sdk.bean.weather.WeatherDailyBean;

import java.util.ArrayList;
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
        holder.itemView.setOnClickListener(v -> {
            showDailyDetailDialog(weatherDailyBean, holder);
        });
    }
    private void showDailyDetailDialog(WeatherDailyBean.DailyBean dailyBean, myViewHolder holder) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(holder.itemView.getContext());
        View dialogView = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.bottom_sheet_dialog, null);
        bottomSheetDialog.setContentView(dialogView);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        if (bottomSheetDialog.getWindow() != null) {
            bottomSheetDialog.getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            );
            bottomSheetDialog.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            );
        }
        BottomSheetDialogBinding detailBinding = BottomSheetDialogBinding.bind(dialogView);
        Typeface customFont = Typeface.createFromAsset(holder.itemView.getContext().getAssets(), "iconfont.ttf");
        detailBinding.tvTime.setText(dailyBean.getFxDate());
        detailBinding.ivTmpMax.setTypeface(customFont);
        detailBinding.tvTmpMax.setText(String.format("%s℃", dailyBean.getTempMax()));
        detailBinding.ivTmpMin.setTypeface(customFont);
        detailBinding.tvTmpMin.setText(String.format("%s℃", dailyBean.getTempMin()));
        detailBinding.ivUvIndex.setTypeface(customFont);
        detailBinding.tvUvIndex.setText(dailyBean.getUvIndex());
        detailBinding.ivCondTxtD.setTypeface(customFont);
        detailBinding.tvCondTxtD.setText(dailyBean.getTextDay());
        detailBinding.ivCondTxtN.setTypeface(customFont);
        detailBinding.tvCondTxtN.setText(dailyBean.getTextNight());
        detailBinding.ivWindDeg.setTypeface(customFont);
        detailBinding.tvWindDeg.setText(dailyBean.getWindDirDay() + " " + String.format("%s°", dailyBean.getWind360Day()));
        detailBinding.ivWindSc.setTypeface(customFont);
        detailBinding.tvWindSc.setText(String.format("%s级", dailyBean.getWindScaleDay()));
        detailBinding.ivWindSpd.setTypeface(customFont);
        detailBinding.tvWindSpd.setText(String.format("%s公里/小时", dailyBean.getWindSpeedDay()));
        detailBinding.ivCloudiness.setTypeface(customFont);
        detailBinding.tvCloudiness.setText(String.format("%s%%", dailyBean.getCloud()));
        detailBinding.ivHumidity.setTypeface(customFont);
        detailBinding.tvHumidity.setText(String.format("%s%%", dailyBean.getHumidity()));
        detailBinding.ivPressure.setTypeface(customFont);
        detailBinding.tvPressure.setText(String.format("%shPa", dailyBean.getPressure()));
        detailBinding.ivPrecipitation.setTypeface(customFont);
        detailBinding.tvPrecipitation.setText(String.format("%smm", dailyBean.getPrecip()));
        detailBinding.ivVisibility.setTypeface(customFont);
        detailBinding.tvVisibility.setText(String.format("%skm", dailyBean.getVis()));
        bottomSheetDialog.show();
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
