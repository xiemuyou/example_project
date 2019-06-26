package com.news.example.myproject.widgets.layoutmanager;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 设置RecyclerView 禁止滑动 LayoutManager
 *
 * @author xiemy
 * @date 2016/12/14
 */
public class CustomLinearLayoutManager extends LinearLayoutManager {

    private boolean isScrollEnabled = true;
    private double speedRatio = 1;

    public CustomLinearLayoutManager(Context context) {
        super(context);
    }

    /**
     * @param context       Current context, will be used to access resources.
     * @param orientation   Layout orientation. Should be {@link #HORIZONTAL} or {@link
     *                      #VERTICAL}.
     * @param reverseLayout When set to true, layouts from end to start.
     */
    public CustomLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    //设置RecyclerView滑动
    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically();
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return super.scrollVerticallyBy((int) (dy * speedRatio), recycler, state);
    }

    public void setSpeedRatio(double speedRatio) {
        this.speedRatio = speedRatio;
    }
}
