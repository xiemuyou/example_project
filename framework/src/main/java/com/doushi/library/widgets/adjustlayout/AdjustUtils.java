package com.doushi.library.widgets.adjustlayout;

import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.doushi.library.widgets.KeepDesignValueUtils;


/**
 * @author xuleyuan
 * @date 24/11/2017
 */
public class AdjustUtils {

    public static void adjustViewPadding(View view) {
        int paddingBottom = view.getPaddingBottom();
        int paddingTop = view.getPaddingTop();
        int paddingStart = view.getPaddingStart();
        int paddingEnd = view.getPaddingEnd();
        int paddingLeft = view.getPaddingLeft();
        int paddingRight = view.getPaddingRight();
        if (paddingBottom == 0 && paddingTop == 0 && paddingStart == 0 && paddingEnd == 0 && paddingLeft == 0 && paddingRight == 0) {
            return;
        }
        if (paddingLeft != 0 || paddingRight != 0) {
            view.setPadding(
                    KeepDesignValueUtils.getInstance().calculatorValue(paddingLeft),
                    KeepDesignValueUtils.getInstance().calculatorValue(paddingTop),
                    KeepDesignValueUtils.getInstance().calculatorValue(paddingRight),
                    KeepDesignValueUtils.getInstance().calculatorValue(paddingBottom)
            );
        }
        view.setPaddingRelative(
                KeepDesignValueUtils.getInstance().calculatorValue(paddingStart),
                KeepDesignValueUtils.getInstance().calculatorValue(paddingTop),
                KeepDesignValueUtils.getInstance().calculatorValue(paddingEnd),
                KeepDesignValueUtils.getInstance().calculatorValue(paddingBottom)
        );
    }

    public static ConstraintLayout.LayoutParams constraintAdjust(ConstraintLayout.LayoutParams layoutParams) {
        int marginEnd = layoutParams.getMarginEnd();
        int marginStart = layoutParams.getMarginStart();
        int bottomMargin = layoutParams.bottomMargin;
        int topMargin = layoutParams.topMargin;
        int leftMargin = layoutParams.leftMargin;
        int rightMargin = layoutParams.rightMargin;
        int height = layoutParams.height;
        int width = layoutParams.width;
        if (height != ConstraintLayout.LayoutParams.MATCH_PARENT && height != ConstraintLayout.LayoutParams.WRAP_CONTENT && height != 0) {
            layoutParams.height = KeepDesignValueUtils.getInstance().calculatorValue(height);
        }
        if (width != ConstraintLayout.LayoutParams.MATCH_PARENT && width != ConstraintLayout.LayoutParams.WRAP_CONTENT && width != 0) {
            layoutParams.width = KeepDesignValueUtils.getInstance().calculatorValue(width);
        }


        layoutParams.setMargins(
                KeepDesignValueUtils.getInstance().calculatorValue(leftMargin),
                KeepDesignValueUtils.getInstance().calculatorValue(topMargin),
                KeepDesignValueUtils.getInstance().calculatorValue(rightMargin),
                KeepDesignValueUtils.getInstance().calculatorValue(bottomMargin)
        );

        layoutParams.setMarginEnd(KeepDesignValueUtils.getInstance().calculatorValue(marginEnd));
        layoutParams.setMarginStart(KeepDesignValueUtils.getInstance().calculatorValue(marginStart));
        return layoutParams;
    }

    public static RelativeLayout.LayoutParams relativeAdjust(RelativeLayout.LayoutParams layoutParams) {

        int marginEnd = layoutParams.getMarginEnd();
        int marginStart = layoutParams.getMarginStart();
        int bottomMargin = layoutParams.bottomMargin;
        int topMargin = layoutParams.topMargin;
        int leftMargin = layoutParams.leftMargin;
        int rightMargin = layoutParams.rightMargin;
        int height = layoutParams.height;
        int width = layoutParams.width;
        if (height != ConstraintLayout.LayoutParams.MATCH_PARENT && height != ConstraintLayout.LayoutParams.WRAP_CONTENT && height != 0) {
            layoutParams.height = KeepDesignValueUtils.getInstance().calculatorValue(height);
        }
        if (width != ConstraintLayout.LayoutParams.MATCH_PARENT && width != ConstraintLayout.LayoutParams.WRAP_CONTENT && width != 0) {
            layoutParams.width = KeepDesignValueUtils.getInstance().calculatorValue(width);
        }

        layoutParams.setMargins(
                KeepDesignValueUtils.getInstance().calculatorValue(leftMargin),
                KeepDesignValueUtils.getInstance().calculatorValue(topMargin),
                KeepDesignValueUtils.getInstance().calculatorValue(rightMargin),
                KeepDesignValueUtils.getInstance().calculatorValue(bottomMargin)
        );

        layoutParams.setMarginEnd(KeepDesignValueUtils.getInstance().calculatorValue(marginEnd));
        layoutParams.setMarginStart(KeepDesignValueUtils.getInstance().calculatorValue(marginStart));
        return layoutParams;
    }

    public static LinearLayout.LayoutParams linearAdjust(LinearLayout.LayoutParams layoutParams) {
        int marginEnd = layoutParams.getMarginEnd();
        int marginStart = layoutParams.getMarginStart();
        int bottomMargin = layoutParams.bottomMargin;
        int topMargin = layoutParams.topMargin;
        int leftMargin = layoutParams.leftMargin;
        int rightMargin = layoutParams.rightMargin;
        int height = layoutParams.height;
        int width = layoutParams.width;
        if (height != ConstraintLayout.LayoutParams.MATCH_PARENT && height != ConstraintLayout.LayoutParams.WRAP_CONTENT && height != 0) {
            layoutParams.height = KeepDesignValueUtils.getInstance().calculatorValue(height);
        }
        if (width != ConstraintLayout.LayoutParams.MATCH_PARENT && width != ConstraintLayout.LayoutParams.WRAP_CONTENT && width != 0) {
            layoutParams.width = KeepDesignValueUtils.getInstance().calculatorValue(width);
        }

        layoutParams.setMargins(
                KeepDesignValueUtils.getInstance().calculatorValue(leftMargin),
                KeepDesignValueUtils.getInstance().calculatorValue(topMargin),
                KeepDesignValueUtils.getInstance().calculatorValue(rightMargin),
                KeepDesignValueUtils.getInstance().calculatorValue(bottomMargin)
        );

        layoutParams.setMarginEnd(KeepDesignValueUtils.getInstance().calculatorValue(marginEnd));
        layoutParams.setMarginStart(KeepDesignValueUtils.getInstance().calculatorValue(marginStart));
        return layoutParams;
    }

    public static FrameLayout.LayoutParams frameAdjust(FrameLayout.LayoutParams layoutParams) {
        int marginEnd = layoutParams.getMarginEnd();
        int marginStart = layoutParams.getMarginStart();
        int bottomMargin = layoutParams.bottomMargin;
        int topMargin = layoutParams.topMargin;
        int leftMargin = layoutParams.leftMargin;
        int rightMargin = layoutParams.rightMargin;
        int height = layoutParams.height;
        int width = layoutParams.width;
        if (height != ConstraintLayout.LayoutParams.MATCH_PARENT && height != ConstraintLayout.LayoutParams.WRAP_CONTENT && height != 0) {
            layoutParams.height = KeepDesignValueUtils.getInstance().calculatorValue(height);
        }
        if (width != ConstraintLayout.LayoutParams.MATCH_PARENT && width != ConstraintLayout.LayoutParams.WRAP_CONTENT && width != 0) {
            layoutParams.width = KeepDesignValueUtils.getInstance().calculatorValue(width);
        }

        layoutParams.setMargins(
                KeepDesignValueUtils.getInstance().calculatorValue(leftMargin),
                KeepDesignValueUtils.getInstance().calculatorValue(topMargin),
                KeepDesignValueUtils.getInstance().calculatorValue(rightMargin),
                KeepDesignValueUtils.getInstance().calculatorValue(bottomMargin)
        );

        layoutParams.setMarginEnd(KeepDesignValueUtils.getInstance().calculatorValue(marginEnd));
        layoutParams.setMarginStart(KeepDesignValueUtils.getInstance().calculatorValue(marginStart));
        return layoutParams;
    }


    public static void adjustView(View view) {
//        if (view instanceof AdjustLinearLayout || view instanceof AdjustConstraintLayout || view instanceof AdjustFrameLayout || view instanceof AdjustRelativeLayout)
//            return;
        AdjustUtils.adjustViewPadding(view);
        if (view instanceof TextView) {
            ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_PX, KeepDesignValueUtils.getInstance().calculatorValue(((TextView) view).getTextSize()));
            ((TextView) view).setLineSpacing(KeepDesignValueUtils.getInstance().calculatorValue(((TextView) view).getLineSpacingExtra()), 1.0f);
        }
    }

    public static ViewGroup.MarginLayoutParams marginAdjust(ViewGroup.MarginLayoutParams layoutParams) {
        int marginEnd = layoutParams.getMarginEnd();
        int marginStart = layoutParams.getMarginStart();
        int bottomMargin = layoutParams.bottomMargin;
        int topMargin = layoutParams.topMargin;
        int leftMargin = layoutParams.leftMargin;
        int rightMargin = layoutParams.rightMargin;
        int height = layoutParams.height;
        int width = layoutParams.width;
        if (height != ConstraintLayout.LayoutParams.MATCH_PARENT && height != ConstraintLayout.LayoutParams.WRAP_CONTENT && height != 0) {
            layoutParams.height = KeepDesignValueUtils.getInstance().calculatorValue(height);
        }
        if (width != ConstraintLayout.LayoutParams.MATCH_PARENT && width != ConstraintLayout.LayoutParams.WRAP_CONTENT && width != 0) {
            layoutParams.width = KeepDesignValueUtils.getInstance().calculatorValue(width);
        }

        layoutParams.setMargins(
                KeepDesignValueUtils.getInstance().calculatorValue(leftMargin),
                KeepDesignValueUtils.getInstance().calculatorValue(topMargin),
                KeepDesignValueUtils.getInstance().calculatorValue(rightMargin),
                KeepDesignValueUtils.getInstance().calculatorValue(bottomMargin)
        );

        layoutParams.setMarginEnd(KeepDesignValueUtils.getInstance().calculatorValue(marginEnd));
        layoutParams.setMarginStart(KeepDesignValueUtils.getInstance().calculatorValue(marginStart));
        return layoutParams;
    }
}
