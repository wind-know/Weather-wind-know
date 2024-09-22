package com.example.weatherwindknow.myMap.help;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherwindknow.R;

public class NestedScrollingRecyclerView extends RecyclerView {

    public NestedScrollingRecyclerView(Context context) {
        super(context);
    }

    public NestedScrollingRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedScrollingRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 当手指触摸屏幕时，允许 RecyclerView 处理事件
            return super.dispatchTouchEvent(ev);
        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            // 当手指在屏幕上移动时，检查是否可以滑动
            if (canScrollVertically(1) || canScrollVertically(-1)) {
                // 如果 RecyclerView 可以滑动，则消费事件
                return true;
            }
        } else if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL) {
            // 当手指离开屏幕或者触摸事件被取消时，事件传递给父布局处理
            getParent().requestDisallowInterceptTouchEvent(false);
        }
        // 默认情况下，事件传递给父布局处理
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        // 重写 onInterceptTouchEvent 方法，这里选择不拦截事件，让 dispatchTouchEvent 处理
        return super.onInterceptTouchEvent(e);
    }
}