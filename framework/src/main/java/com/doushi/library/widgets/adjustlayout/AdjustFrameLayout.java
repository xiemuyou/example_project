package com.doushi.library.widgets.adjustlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by xuleyuan on 23/11/2017
 */

public class AdjustFrameLayout extends FrameLayout {

    public AdjustFrameLayout(Context context) {
        super(context);
    }

    public AdjustFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdjustFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
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
        return AdjustUtils.frameAdjust(layoutParams);
    }

}
