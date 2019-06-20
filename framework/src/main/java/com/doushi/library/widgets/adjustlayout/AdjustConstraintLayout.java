package com.doushi.library.widgets.adjustlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;


/**
 * @author xuleyuan
 * @date 24/11/2017
 */
public class AdjustConstraintLayout extends ConstraintLayout {

    public AdjustConstraintLayout(Context context) {
        super(context);
    }

    public AdjustConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdjustConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        AdjustUtils.adjustViewPadding(this);
    }

    @Override
    public void onViewAdded(View view) {
        super.onViewAdded(view);
        AdjustUtils.adjustView(view);

    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        ConstraintLayout.LayoutParams layoutParams = super.generateLayoutParams(attrs);
        return AdjustUtils.constraintAdjust(layoutParams);
    }

}
