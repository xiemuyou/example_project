package com.library.widgets.adjustlayout;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;


/**
 * @author xuleyuan
 * @date 24/11/2017
 */
public class AdjustUnScrollRecyclerView extends AdjustRecyclerView {

    public AdjustUnScrollRecyclerView(Context context) {
        super(context);
    }

    public AdjustUnScrollRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AdjustUnScrollRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return false;
    }
}
