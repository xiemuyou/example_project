package com.doushi.library.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.blankj.utilcode.util.ScreenUtils;
import com.doushi.library.R;

import java.math.BigDecimal;

/**
 * 百分比的ImageView
 * 使用方式：
 * uiWidth,uiHeight 填入对应的UI数据
 * resize属性填入height则重新计算height 填入width则重新计算width 填入screen，则根据传入的ui屏幕宽度，重计算图片宽高
 *
 * @author xuleyuan
 * @date 2017/10/20
 */
public class RateImageView extends AppCompatImageView {

    double rate = 1;
    int uiWidth;
    int uiHeight;
    int uiScreenWidth;
    int mode;
    private final int RESIZE_HEIGHT = 0;
    private final int RESIZE_WIDTH = 1;
    private final static int RESIZE_BY_SCREEN = 2;

    public RateImageView(Context context) {
        super(context);
    }

    public RateImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RateImageView);
        init(typedArray);
    }

    public RateImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RateImageView);
        init(typedArray);
    }

    private void init(TypedArray typedArray) {
        uiHeight = typedArray.getInteger(R.styleable.RateImageView_uiHeight, 1);
        uiWidth = typedArray.getInteger(R.styleable.RateImageView_uiWidth, 1);
        mode = typedArray.getInt(R.styleable.RateImageView_resize, RESIZE_HEIGHT);
        uiScreenWidth = typedArray.getInt(R.styleable.RateImageView_uiScreenWidth, 1000);
        typedArray.recycle();
        switch (mode) {
            case RESIZE_HEIGHT:
                rate = div(uiHeight, uiWidth, 50);
                break;

            case RESIZE_WIDTH:
                rate = div(uiWidth, uiHeight, 50);
                break;

            default:
                rate = 1;
                break;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int measureWidth = 0, measureHeight = 0;
        switch (mode) {
            case RESIZE_HEIGHT:
                measureWidth = width;
                //match_parent
                measureHeight = (int) (width * rate);
                break;

            case RESIZE_WIDTH:
                measureHeight = height;
                //match_parent
                measureWidth = ((int) (height * rate));
                break;

            case RESIZE_BY_SCREEN:
                measureWidth = (int) div(mul(uiWidth, ScreenUtils.getScreenWidth()), uiScreenWidth, 1);
                measureHeight = (int) div(mul(measureWidth, uiHeight), uiWidth, 1);
                break;

            default:
                break;
        }
        setMeasuredDimension(measureWidth, measureHeight);
    }

    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }
}
