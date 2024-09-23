package com.example.weatherwindknow.myalwaysact;

import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherwindknow.R;
import com.example.weatherwindknow.mymain.today.forcastFragment;
import com.example.weatherwindknow.databinding.ItemAlcityBinding;

import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class alcityAdapter extends RecyclerView.Adapter<alcityAdapter.myViewHolder> {
    List<alcity> malcityList;
    alcityDbHelper dbHelper ;
    public alcityAdapter(List<alcity> alcityList) {
        this.malcityList = alcityList;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemAlcityBinding binding = ItemAlcityBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new myViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        alcity alcity = malcityList.get(position);
        if(alcity == null){
            holder.binding.alcityName.setVisibility(View.GONE);
            holder.binding.alcityTemp.setVisibility(View.GONE);
            holder.binding.alcityWeather.setVisibility(View.GONE);
        }
        dbHelper = alcityDbHelper.getInstance(holder.itemView.getContext());
        holder.setdate(alcity);
        holder.binding.alcitycardView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.recylegundong));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x;
                x =  holder.getAdapterPosition();
                Log.d("findinadpter", String.valueOf(x));
                FragmentManager fm = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                forcastFragment fragment = new forcastFragment();
                ft.replace(R.id.fragmentContainerView, fragment);
                ft.commit();
                fm.executePendingTransactions();  // 确保 Fragment 事务完成
                fragment.binding.viewPager.setCurrentItem(x,true);
                CircleIndicator3 indicator = fragment.binding.indicator;
                indicator.animatePageSelected(x);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final int[] i = {0};
                new AlertDialog.Builder(v.getContext())
                        .setTitle("确认")
                        .setMessage("确定要删除该城市吗?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (alcity.getAdcode() != null) {
                                    dbHelper.deleteByAdcode(alcity.getAdcode());
                                    malcityList.remove(holder.getAdapterPosition());
                                    notifyItemRemoved(holder.getAdapterPosition());
                                    notifyItemRangeChanged(holder.getAdapterPosition(), malcityList.size());
                                    i[0] =1;
                                }
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(R.drawable.dog)
                        .show();
                if(i[0]==1)
                    return true;
                return false;
            }

            @Override
            public boolean onLongClickUseDefaultHapticFeedback(@NonNull View v) {
                return View.OnLongClickListener.super.onLongClickUseDefaultHapticFeedback(v);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (malcityList == null)
            return 0;
        return malcityList.size();
    }

    static class myViewHolder extends RecyclerView.ViewHolder {
        private final ItemAlcityBinding binding;

        public myViewHolder(ItemAlcityBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setdate(alcity alcity) {
            if(alcity!=null) {
                binding.alcityName.setText(alcity.getMap());
                if (alcity.getMweatherHourlyBean() != null) {
                    binding.alcityTemp.setText(alcity.getMweatherHourlyBean().getHourly().get(0).getTemp() + "°C");
                    binding.alcityWeather.setText(" " + alcity.getMweatherHourlyBean().getHourly().get(0).getText() + "\n" + " " + alcity.getmWeatherDailyBean().getDaily().get(0).getTempMin() + "°C/" + alcity.getmWeatherDailyBean().getDaily().get(0).getTempMax() + "°C");
                }
            }
        }
    }
}
