package com.library.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Field;

/**
 * 控件工具类
 *
 * @author xiemy
 * @date 2016/7/28.
 */
public class ViewUtil {

    private static final String TAG = "ViewUtil";

    /**
     * 设置View的Margin (外边距)
     *
     * @param v 控件
     * @param l 左边距
     * @param t 上边距
     * @param r 右边距
     * @param b 下边距
     */
    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v == null) {
            return;
        }
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    /**
     * 设置View的Margin (外边距)
     *
     * @param v 控件
     * @param l 左边距
     * @param t 上边距
     * @param r 右边距
     * @param b 下边距
     */
    public static void setPadding(View v, int l, int t, int r, int b) {
        if (v == null) {
            return;
        }
        v.setPadding(l, t, r, b);
    }

    public static void setTextColor(Context context, TextView v, @ColorRes int textColor) {
        if (v == null || context == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            v.setTextColor(context.getResources().getColor(textColor, null));
        } else {
            v.setTextColor(context.getResources().getColor(textColor));
        }
    }

    public static void setTextSizeForDip(TextView v, float textSize) {
        if (v == null) {
            return;
        }
        v.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
    }

    public static void setBackgroundColor(Context context, View v, @ColorRes int bgColor) {
        if (v == null || context == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            v.setBackgroundColor(context.getResources().getColor(bgColor, null));
        } else {
            v.setBackgroundColor(context.getResources().getColor(bgColor));
        }
    }

    public static void setBackground(Context context, View v, @DrawableRes int bgRes) {
        if (v == null || context == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            v.setBackground(context.getResources().getDrawable(bgRes, null));
        } else {
            v.setBackground(context.getResources().getDrawable(bgRes));
        }
    }

    /**
     * 设置多颜色字符串 (待优化)
     *
     * @param context           上下问对象
     * @param insetStr          插入字符串
     * @param resId             源字符串
     * @param colorRes          插入字符串颜色
     * @param moreColorTextView 多颜色TextView
     */
    public static void setTextMoreColor(Context context, int insetStr, @StringRes int resId,
                                        @ColorRes int colorRes, TextView moreColorTextView) {
        setTextMoreColor(context, String.valueOf(insetStr), resId, colorRes, moreColorTextView);
    }

    public static void setTextMoreColor(Context context, String insetStr, @StringRes int resId,
                                        @ColorRes int colorRes, TextView moreColorTextView) {
        String moreColorStr = context.getString(resId, insetStr);
        SpannableString joinCountStr = new SpannableString(moreColorStr);
        joinCountStr.setSpan(new ForegroundColorSpan(context.getResources().getColor(colorRes)),
                moreColorStr.indexOf(insetStr), moreColorStr.indexOf(insetStr) + insetStr
                        .length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        moreColorTextView.setText(joinCountStr);
    }

    public static void setDrawable(Context context, TextView view, @DrawableRes int drawableRes, DrawablePosition position) {
        if (drawableRes == 0 || position == null) {
            view.setCompoundDrawables(null, null, null, null);
            return;
        }
        Drawable drawableOff;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            drawableOff = context.getResources().getDrawable(drawableRes, null);
        } else {
            drawableOff = context.getResources().getDrawable(drawableRes);
        }
        //调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
        drawableOff.setBounds(0, 0, drawableOff.getMinimumWidth(), drawableOff.getMinimumHeight());
        switch (position) {
            //设置左图标
            case LEFT:
                view.setCompoundDrawables(drawableOff, null, null, null);
                break;

            //设置上图标
            case TOP:
                view.setCompoundDrawables(null, drawableOff, null, null);
                break;

            //设置右图标
            case RIGHT:
                view.setCompoundDrawables(null, null, drawableOff, null);
                break;

            //设置右图标
            case BOTTOM:
                view.setCompoundDrawables(null, null, null, drawableOff);
                break;

            default:
                view.setCompoundDrawables(drawableOff, null, null, null);
                break;
        }
    }

    public enum DrawablePosition {
        LEFT, TOP, RIGHT, BOTTOM
    }

    /**
     * 设置tablayout 标签下划线宽度 与文字同宽度+10
     *
     * @param tabs
     */
    public static void setIndicator(TabLayout tabs) {
        Class<?> tabLayout = tabs.getClass();
        try {
            //拿到tabLayout的mTabStrip属性  
            Field mTabStripField = tabLayout.getDeclaredField("mTabStrip");
            mTabStripField.setAccessible(true);

            LinearLayout mTabStrip = (LinearLayout) mTabStripField.get(tabs);

            int dp15 = SizeUtils.dp2px(15);

            for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                View tabView = mTabStrip.getChildAt(i);

                //拿到tabView的mTextView属性  
                Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                mTextViewField.setAccessible(true);

                TextView mTextView = (TextView) mTextViewField.get(tabView);
                tabView.setPadding(0, 0, 0, 0);

                //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度  
                int width;
                width = mTextView.getWidth();
                if (width == 0) {
                    mTextView.measure(0, 0);
                    width = mTextView.getMeasuredWidth();
                }

                //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的  
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                params.width = width + SizeUtils.dp2px(10);
                params.leftMargin = dp15;
                params.rightMargin = dp15;
                tabView.setLayoutParams(params);

                tabView.invalidate();
            }

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static final int screenWidthPixels = ScreenUtils.getScreenWidth();
    private static final int maxVideoHeight = (int) (ScreenUtils.getScreenHeight() * 0.66);

    public static int getVideoHeight(int vw, int vh) {
        return setVideoHeight(screenWidthPixels, vw, vh, null);
    }

    public static int getVideoHeight(int itemViewWidth, int vw, int vh) {
        return setVideoHeight(itemViewWidth, vw, vh, null);
    }

    public static int setVideoHeight(int vw, int vh, View view) {
        return setVideoHeight(screenWidthPixels, vw, vh, view);
    }

    private static int setVideoHeight(int itemViewWidth, int vw, int vh, View view) {
        //默认的视频宽高比
        double ratio = (9 * 1.0) / 16;
        int defaultVideoHeight = (int) (itemViewWidth * ratio);

//        int height;
//        if (vw <= 0 || vh <= 0) {
//            height = defaultVideoHeight;
//        } else {
//            //等比缩放
//            ratio = (itemViewWidth * 1.0) / vw;
//            height = (int) (vh * ratio);
//            if (height > maxVideoHeight) {
//                height = maxVideoHeight;
//            }
//            if (height < defaultVideoHeight) {
//                height = defaultVideoHeight;
//            }
//        }
        if (view != null) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = defaultVideoHeight;
            view.setLayoutParams(layoutParams);
        }
        return defaultVideoHeight;
    }

    /**
     * UI 设计师标记的参考手机屏幕宽度大小,
     * 用于计算标记的高度比例设置控件显示高度
     */
    public static final int useHeight = 720;

    public static void setViewScreenProportionHeight(View view, int uiTagDpHeight) {
        setViewScreenProportionHeight(view, ViewGroup.LayoutParams.MATCH_PARENT, uiTagDpHeight);
    }

    public static void setViewScreenProportionHeight(View view, int viewWight, int uiTagDpHeight) {
        if (view != null) {
            //默认的视频宽高比
            double ratio = (uiTagDpHeight * 1.0) / useHeight;
            int defaultVideoHeight = (int) (ScreenUtils.getScreenWidth() * ratio);
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.width = viewWight;
            layoutParams.height = defaultVideoHeight;
            view.setLayoutParams(layoutParams);
        }
    }

    public static void setViewSize(View v, int width, int height) {
        if (v == null) {
            return;
        }
        ViewGroup.LayoutParams p = v.getLayoutParams();
        if (width > 0) {
            p.width = width;
        }
        if (height > 0) {
            p.height = height;
        }
        v.requestLayout();
    }

    private static int imgViewHeight = -1;

    public static int setSmallImgViewHeight(View view) {
        if (imgViewHeight <= 0) {
            setSmallImageViewSize(null);
        }
        if (view != null) {
            ViewGroup.LayoutParams vglp = view.getLayoutParams();
            if (vglp != null) {
                vglp.height = imgViewHeight;
            }
        }
        return imgViewHeight;
    }

    /**
     * 设置赋值图片宽高
     */
    public static void setSmallImageViewSize(View view) {
        //分割线宽度 * 2
        int dSize = SizeUtils.dp2px(3) * 2;
        //边距宽度 * 2
        int mSize = SizeUtils.dp2px(18) * 2;
        int imgViewWidth = (ScreenUtils.getScreenWidth() - (mSize + dSize)) / 3;
        //默认的图片3/4宽高比
        double ratio = (3 * 1.0) / 4;
        imgViewHeight = (int) (imgViewWidth * ratio);
        if (view != null) {
            ViewGroup.LayoutParams vglp = view.getLayoutParams();
            if (vglp != null) {
                vglp.width = imgViewWidth;
                vglp.height = imgViewHeight;
            }
        }
    }
}
