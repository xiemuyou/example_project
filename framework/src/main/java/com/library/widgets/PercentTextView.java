package com.library.widgets;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
/**
 * Created by xuleyuan on 22/11/2017
 * 百分比TextView，改完类名直接把textSize改为设计稿的px，就可以了，记住是px,不要除2
 */

public class PercentTextView extends androidx.appcompat.widget.AppCompatTextView {

    public PercentTextView(Context context) {
        super(context);
//        adjust();
    }

    public PercentTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        adjust();
    }

    public PercentTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        adjust();
    }


//    private void adjust() {
//        float beforeVal = getTextSize();
//        setTextSize(TypedValue.COMPLEX_UNIT_PX, KeepDesignValueUtils.getInstance().calculatorValue(beforeVal));
//    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        AdjustUtils.adjustView(this);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
