package com.library.widgets.adjustlayout;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author xuleyuan
 * @date 24/11/2017
 */
public class AdjustRecyclerView extends RecyclerView {

    public AdjustRecyclerView(Context context) {
        super(context);
    }

    public AdjustRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AdjustRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        MarginLayoutParams layoutParams = new MarginLayoutParams(getContext(), attrs);
        AdjustUtils.marginAdjust(layoutParams);
        return layoutParams;
    }

}
