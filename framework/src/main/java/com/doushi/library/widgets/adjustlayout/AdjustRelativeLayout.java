package com.doushi.library.widgets.adjustlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;


/**
 * @author xuleyuan
 * @date 24/11/2017
 */
public class AdjustRelativeLayout extends RelativeLayout {

    public AdjustRelativeLayout(Context context) {
        super(context);
    }

    public AdjustRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdjustRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
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
        return AdjustUtils.relativeAdjust(layoutParams);
    }

}
