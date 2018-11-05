package com.doushi.library.widgets.adjustlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by xuleyuan on 23/11/2017
 */

public class AdjustLinearLayout extends LinearLayout {

    public AdjustLinearLayout(Context context) {
        super(context);
    }

    public AdjustLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdjustLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//        AdjustUtils.adjustViewPadding(this);
    }

    @Override
    public void onViewAdded(View view) {
        super.onViewAdded(view);
        AdjustUtils.adjustView(view);

    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        LayoutParams layoutParams = super.generateLayoutParams(attrs);
        return AdjustUtils.linearAdjust(layoutParams);
    }

}
