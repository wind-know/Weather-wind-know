package com.example.weatherwindknow.myMap;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherwindknow.R;
import com.example.weatherwindknow.databinding.ItemThreeDayDailyListBinding;
import com.example.weatherwindknow.myservice.Forecast;

import java.util.List;
public class ThreeDailyAdapter extends RecyclerView.Adapter<ThreeDailyAdapter.ThreeDailyViewHolder> {

    private List<Forecast.Cast> casts;

    public ThreeDailyAdapter(List<Forecast.Cast> casts) {
        this.casts = casts;
        this.casts.addAll(casts);
        this.casts.addAll(casts);
    }

    @Override
    public ThreeDailyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemThreeDayDailyListBinding binding = ItemThreeDayDailyListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ThreeDailyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ThreeDailyViewHolder holder, int position) {
        // 2. 绑定数据到ViewHolder
        Forecast.Cast cast = casts.get(position);
        holder.binding.tvDate.setText(cast.getDate());
        holder.binding.tvWeather.setText(cast.getDayweather());
        int iconResId = getWeatherIconResId(cast.getDayweather());
        holder.binding.ivWeatherState.setImageResource(iconResId);
        holder.binding.tvTempHeight.setText("日间"+cast.getDaytemp()+"~");
        holder.binding.tvTempLow.setText("夜间"+cast.getNighttemp());
    }

    public static int getWeatherIconResId(String dayweather) {
        switch (dayweather) {
            case "晴":
            case "少云":
                return R.drawable.tianqibaitianqing;
            case "晴间多云":
            case "多云":
                return R.drawable.tianqiqingtianduoyun;
            case "阴":
                return R.drawable.tianqiyin;
            case "有风":
            case "平静":
            case "微风":
            case "和风":
            case "清风":
                return R.drawable.tianqiweifeng; // 强风或微风的图标资源 ID
            case "强风":
            case "劲风":
                return R.drawable.tianqiqiangfeng;
            case "疾风":
            case "大风":
            case "烈风":
                return R.drawable.tianqidafeng; // 疾风或大风的图标资源 ID
            case "风暴":
            case "狂爆风":
                return R.drawable.tianqifengbao;
            case "飓风":
            case "热带风暴":
                return R.drawable.tianqiredaifengbao;
            case "霾":
            case "中度霾":
            case "重度霾":
            case "严重霾":
                return R.drawable.tianqiwumai;
            case "阵雨":
            case "雨":
            case "毛毛雨/细雨":
                return R.drawable.tianqibaitianzhenyu;
            case "雷阵雨":
            case "雷阵雨并伴有冰雹":
                return R.drawable.tianqileizhenyu;
            case "小雨":
                return R.drawable.tianqixiaoyu;
            case "中雨":
                return R.drawable.tianqizhongyu;
            case "大雨":
                return R.drawable.tianqidayu;
            case "暴雨":
                return R.drawable.tianqibaoyu;
            case "大暴雨":
                return R.drawable.tianqidabaoyu;
            case "特大暴雨":
                return R.drawable.tianqitedabaoyu;
            case "强阵雨":
                return R.drawable.tianqiqiangzhenyu;
            case "强雷阵雨":
                return R.drawable.tianqiqiangleizhenyu;
            case "极端降雨":
                return R.drawable.tianqijiduanjiangyu;
            case "小雨-中雨":
                return R.drawable.tianqixiaodaozhongyu;
            case "中雨-大雨":
                return R.drawable.tianqizhongdaodayu;
            case "大雨-暴雨":
                return R.drawable.tianqidadaobaoyu;
            case "暴雨-大暴雨":
                return R.drawable.tianqibaoyudaodabaoyu;
            case "大暴雨-特大暴雨":
                return R.drawable.tianqidabaoyuzhuantedabaoyu;
            case "雨雪天气":
                return R.drawable.tianqiyuxue;
            case "雨夹雪":
                return R.drawable.tianqiyujiaxue;
            case "阵雨夹雪":
                return R.drawable.tianqizhenyujiaxue;
            case "冻雨":
                return R.drawable.tianqidongyu;
            case "雪":
            case "阵雪":
                return R.drawable.tianqiqingtianzhenxue;
            case "小雪":
                return R.drawable.tianqixiaoxue;
            case "中雪":
                return R.drawable.tianqizhongxue;
            case "大雪":
                return R.drawable.tianqidaxue;
            case "暴雪":
                return R.drawable.tianqibaoxue;
            case "小雪-中雪":
                return R.drawable.tianqixiaodaozhongxue;
            case "中雪-大雪":
            case "大雪-暴雪":
                return R.drawable.tianqidadaobaoxue;

            case "浮尘":
                return R.drawable.tianqifuchen;
            case "扬沙":
                return R.drawable.tianqiyangsha;
            case "沙尘暴":
            case "强沙尘暴":
                return R.drawable.tianqishachenbao;
            case "龙卷风":
                return R.drawable.tianqitaifeng;
            case "雾":
            case "浓雾":
            case "强浓雾":
            case "轻雾":
            case "大雾":
            case "特强浓雾":
                return R.drawable.tianqiwu;
            case "热":
                return R.drawable.tianqireqiwengao;
            case "冷":
                return R.drawable.tianqilengqiwendi;
            default:
                return R.drawable.tianqiweizhi; // 默认图标
        }
    }

    @Override
    public int getItemCount() {
        return casts.size();
    }

    public static class ThreeDailyViewHolder extends RecyclerView.ViewHolder {
        private final ItemThreeDayDailyListBinding binding;

        public ThreeDailyViewHolder(ItemThreeDayDailyListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
//        public ItemThreeDayDailyListBinding getBinding() {
//            return binding;
//        }
    }
}