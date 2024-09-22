package com.example.weatherwindknow.mymain.today;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherwindknow.R;
import com.example.weatherwindknow.databinding.ItemMainDayweatherBinding;

import com.example.weatherwindknow.myMap.help.HourlyResponse;
import com.qweather.sdk.bean.weather.WeatherHourlyBean;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class weatherhourAdapter extends RecyclerView.Adapter<weatherhourAdapter.weatherhourViewHolder> {

    private List<HourlyResponse.HourlyBean> casts;
    private Context context;
//    RecyclerView recyclerView;
    public weatherhourAdapter(List<WeatherHourlyBean.HourlyBean> casts,Context context) {
        List<HourlyResponse.HourlyBean> hourlyWeatherList = convertHourlyBeans(casts);
        this.casts = hourlyWeatherList;
        this.context = context;
//        this.recyclerView = recyclerView;
    }
    private List<HourlyResponse.HourlyBean> convertHourlyBeans(List<WeatherHourlyBean.HourlyBean> sourceList) {
        List<HourlyResponse.HourlyBean> resultList = new ArrayList<>();
        for (WeatherHourlyBean.HourlyBean sourceBean : sourceList) {
            HourlyResponse.HourlyBean targetBean = new HourlyResponse.HourlyBean();
            // 复制属性
            targetBean.setFxTime(sourceBean.getFxTime());
            Log.d("TAGs", sourceBean.getFxTime());
            Log.d("TAGt", targetBean.getFxTime());
            targetBean.setTemp(sourceBean.getTemp());
            targetBean.setIcon(sourceBean.getIcon());
            targetBean.setText(sourceBean.getText());
            targetBean.setWindSpeed(sourceBean.getWindSpeed());
            targetBean.setWindDir(sourceBean.getWindDir());
            targetBean.setWindScale(sourceBean.getWindScale());
            targetBean.setWind360(sourceBean.getWind360());
            targetBean.setPrecip(sourceBean.getPrecip());
            targetBean.setHumidity(sourceBean.getHumidity());
            targetBean.setPressure(sourceBean.getPressure());
            targetBean.setPop(sourceBean.getPop());
            targetBean.setCloud(sourceBean.getCloud());
            targetBean.setDew(sourceBean.getDew());
            resultList.add(targetBean);
        }
        return resultList;
    }
    @Override
    public weatherhourViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMainDayweatherBinding binding = ItemMainDayweatherBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new weatherhourViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(weatherhourViewHolder holder, int position) {
        // 2. 绑定数据到ViewHolder
        HourlyResponse.HourlyBean cast = casts.get(position);
        String time = cast.getFxTime();
        time = time.substring(time.length() - 11, time.length() - 6);
        holder.binding.tvMainTime.setText(time);
        holder.binding.hourlycardView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.recyleviewdanru));
//        LocalTime now = LocalTime.now();
//        int currentHour = now.getHour();
//        if (currentHour < 19&& currentHour >= 6) {
//            holder.binding.ivMainDayweather.setImageResource(getDayIconDark(cast.getIcon()));
//        }else {
//            holder.binding.ivMainDayweather.setImageResource(getNightIconDark(cast.getIcon()));
//        }
        holder.binding.tvMainDaytemp.setText(cast.getTemp()+"°C");
        holder.binding.tvMainDayweather.setText(cast.getText());
        Typeface font = Typeface.createFromAsset(context.getAssets(), "qweather-icons.ttf");//加载图标字体
        holder.binding.ivMainDayweather.setTypeface(font);
        if(!icon(cast.getText(),holder)) {
            finish(cast.getIcon(), holder);
        }
    }
    public boolean icon(String tianqicode,weatherhourViewHolder holder) {
        switch (tianqicode) {
            case "晴":
                holder.binding.ivMainDayweather.setText("🌞");
                break;
            case "多云":
                holder.binding.ivMainDayweather.setText("⛅️");
                break;
            case "少云":
            case "晴间多云":
                holder.binding.ivMainDayweather.setText("🌤️");
                break;
            case "阴":
                holder.binding.ivMainDayweather.setText("☁️");
                break;
            case "阵雨":
                holder.binding.ivMainDayweather.setText("🌦️");
                break;
            case "强阵雨":
                holder.binding.ivMainDayweather.setText("🌧️");
                break;
            case "雷阵雨":
                holder.binding.ivMainDayweather.setText("⛈️");
                break;
            case "强雷阵雨":
                holder.binding.ivMainDayweather.setText("⛈️");
                break;
            case "雷阵雨伴有冰雹":
                holder.binding.ivMainDayweather.setText("⛈️");
                break;
            case "小雨":
                holder.binding.ivMainDayweather.setText("🌂");
                break;
            case "中雨":
                holder.binding.ivMainDayweather.setText("☂️");
                break;
            case "大雨":
                holder.binding.ivMainDayweather.setText("☔");
                break;
            case "极端降雨":
                holder.binding.ivMainDayweather.setText("💧");
                break;
            case "毛毛雨/细雨":
                holder.binding.ivMainDayweather.setText("🌂");
                break;
            case "暴雨":
                holder.binding.ivMainDayweather.setText("🌧️");
                break;
            case "大暴雨":
                holder.binding.ivMainDayweather.setText("🌨️");
                break;
            case "特大暴雨":
                holder.binding.ivMainDayweather.setText("🌨️");
                break;
            case "冻雨":
                holder.binding.ivMainDayweather.setText("🥶");
                break;
            case "小到中雨":
                holder.binding.ivMainDayweather.setText("☂️");
                break;
            case "中到大雨":
                holder.binding.ivMainDayweather.setText("🌨️");
                break;
            case "大到暴雨":
            case "暴雨到大暴雨":
            case "大暴雨到特大暴":
                holder.binding.ivMainDayweather.setText("🌧️");
                break;
            case "雨":
                holder.binding.ivMainDayweather.setText("🌧️");
                break;
            case "小雪":
                holder.binding.ivMainDayweather.setText("❄️");
                break;
            case "中雪":
                holder.binding.ivMainDayweather.setText("❄️");
                break;
            case "大雪":
                holder.binding.ivMainDayweather.setText("❄️");
                break;
            case "暴雪":
                holder.binding.ivMainDayweather.setText("❄️");
                break;
            case "雨夹雪":
                holder.binding.ivMainDayweather.setText("🌨️❄️");
                break;
            case "雨雪天气":
                holder.binding.ivMainDayweather.setText("🌨️");
                break;
            case "阵雨夹雪":
                holder.binding.ivMainDayweather.setText("🌨️");
                break;
            case "阵雪":
                holder.binding.ivMainDayweather.setText("❄️");
                break;
            case "小到中雪":
                holder.binding.ivMainDayweather.setText("❄️");
                break;
            case "中到大雪":
                holder.binding.ivMainDayweather.setText("❄️");
                break;
            case "大到暴雪":
                holder.binding.ivMainDayweather.setText("❄️");
                break;
            case "雪":
                holder.binding.ivMainDayweather.setText("❄️");
                break;
            case "薄雾":
                holder.binding.ivMainDayweather.setText("🌫️");
                break;
            case "雾":
                holder.binding.ivMainDayweather.setText("🌫️");
                break;
            case "霾":
                holder.binding.ivMainDayweather.setText("🌫️");
                break;
            case "扬沙":
                holder.binding.ivMainDayweather.setText("🌫️");
                break;
            case "浮尘":
                holder.binding.ivMainDayweather.setText("🌫️");
                break;
            case "沙尘暴":
                holder.binding.ivMainDayweather.setText("🌫️");
                break;
            case "强沙尘暴":
                holder.binding.ivMainDayweather.setText("🌫️");
                break;
            case "浓雾":
                holder.binding.ivMainDayweather.setText("🌫️");
                break;
            case "强浓雾":
                holder.binding.ivMainDayweather.setText("🌫️");
                break;
            case "中度霾":
                holder.binding.ivMainDayweather.setText("🌫️");
                break;
            case "重度霾":
                holder.binding.ivMainDayweather.setText("🌫️");
                break;
            case "严重霾":
                holder.binding.ivMainDayweather.setText("🌫️");
                break;
            case "大雾":
                holder.binding.ivMainDayweather.setText("🌫️");
                break;
            case "特强浓雾":
                holder.binding.ivMainDayweather.setText("🌫️");
                break;
            case "热":
                holder.binding.ivMainDayweather.setText("🔥");
                break;
            case "冷":
                holder.binding.ivMainDayweather.setText("❄️");
                break;
            case "未知":
                holder.binding.ivMainDayweather.setText("❓");
                break;
            default:
                return false;
        }return true;
    }
    public void finish(String tianqicode,weatherhourViewHolder holder) {
        analyzeJSONArray(tubiaoDay, tianqicode, holder);
    }

    String tubiaoDay="{" +
            "'100':'&#xF101;','101':'&#xF102;','102':'&#xF103;'," +
            "'103':'&#xF104;','104':'&#xF105;','300':'&#xF10A;'," +
            "'301':'&#xF10B;','302':'&#xF10C;','303':'&#xF10D;'," +
            "'304':'&#xF10E;','305':'&#xF10F;','306':'&#xF110;'," +
            "'307':'&#xF111;','308':'&#xF112;','309':'&#xF113;'," +
            "'310':'&#xF114;','311':'&#xF115;','312':'&#xF116;'," +
            "'313':'&#xF117;','314':'&#xF118;','315':'&#xF119;'," +
            "'316':'&#xF11A;','317':'&#xF11B;','318':'&#xF11C;'," +
            "'399':'&#xF11F;','400':'&#xF120;','401':'&#xF121;'," +
            "'402':'&#xF122;','403':'&#xF123;','404':'&#xF124;'," +
            "'405':'&#xF125;','406':'&#xF126;','407':'&#xF127;'," +
            "'408':'&#xF128;','409':'&#xF129;','410':'&#xF12A;'," +
            "'499':'&#xF12D;','500':'&#xF12E;','501':'&#xF12F;'," +
            "'502':'&#xF130;','503':'&#xF131;','504':'&#xF132;'," +
            "'507':'&#xF133;','508':'&#xF134;','509':'&#xF135;'," +
            "'510':'&#xF136;','511':'&#xF137;','512':'&#xF138;'," +
            "'513':'&#xF139;','514':'&#xF13A;','515':'&#xF13B;'," +
            "'900':'&#xF144;','901':'&#xF145;'}" ;

    public void analyzeJSONArray(String json,String tianqicode,weatherhourViewHolder holder) {
        try {
            JSONObject jsonObjectALL = new JSONObject(json);
            String b=jsonObjectALL.getString(tianqicode);
            holder.binding.ivMainDayweather.setText(Html.fromHtml(b));
        } catch (Exception e) {
            //e.printStackTrace();
//            holder.binding.ivMainDayweather.setText(Html.fromHtml("&#xF146;"));//如果未找到则显示N/A
        }
    }
    @Override
    public int getItemCount() {
        return casts.size();
    }

    public static class weatherhourViewHolder extends RecyclerView.ViewHolder {
        private final ItemMainDayweatherBinding binding;

        public weatherhourViewHolder(ItemMainDayweatherBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}