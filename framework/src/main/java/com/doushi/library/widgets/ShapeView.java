package com.doushi.library.widgets;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import androidx.annotation.ColorRes;

import com.doushi.library.R;

/**
 * author：zhangw
 * date: 2017/7/28.
 * email: zhangwei@kingnet.com
 * 设置view背景
 * 用法：
 * ShapeView shapeView= new ShapeView.Builder(this)
 * .setStrokeWidth(15)
 * .setRoundRadius(15,15 ,15 ,15)
 * .setFillColor(R.color.red_back_color)
 * .setStrokeColor(R.color.colorAccent)
 * .create();
 * view.setBackground(shapeView);
 */
public class ShapeView extends GradientDrawable {


    public static class Builder {
        /**
         * 线宽度
         */
        private int strokeWidth;
        /**
         * 圆角角度
         */
        private int roundLeftTop;
        private int roundRightTop;
        private int roundRightBottom;
        private int roundLeftBottom;
        /**
         * 线颜色
         */
        private
        @ColorRes
        int strokeColor;
        /**
         * 填充颜色
         */
        private
        @ColorRes
        int fillColor;

        private Context context;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 设置线宽度
         *
         * @param strokeWidth 提示标题 px
         */
        public ShapeView.Builder setStrokeWidth(int strokeWidth) {
            this.strokeWidth = strokeWidth;
            return this;
        }

        /**
         * 设置圆角角度
         *
         * @param leftTop     px  左上
         * @param rightTop    px 右上
         * @param rightBottom px 右下
         * @param leftBottom  px 左下
         * @return
         */
        public ShapeView.Builder setRoundRadius(int leftTop, int rightTop, int rightBottom, int leftBottom) {
            this.roundLeftTop = leftTop;
            this.roundRightTop = rightTop;
            this.roundRightBottom = rightBottom;
            this.roundLeftBottom = leftBottom;
            return this;
        }

        /**
         * 设置线颜色
         *
         * @param strokeColor
         * @return
         */
        public ShapeView.Builder setStrokeColor(@ColorRes int strokeColor) {
            this.strokeColor = strokeColor;
            return this;
        }

        /**
         * 设置填充颜色
         */
        public ShapeView.Builder setFillColor(@ColorRes int fillColor) {
            this.fillColor = fillColor;
            return this;
        }

        /**
         * 创建GradientDrawable
         *
         * @return
         */
        public ShapeView create() {
            ShapeView gd = new ShapeView();
            gd.setCornerRadii(new float[]{roundLeftTop, roundLeftTop, roundRightTop, roundRightTop, roundRightBottom, roundRightBottom, roundLeftBottom, roundLeftBottom});
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                gd.setColor(fillColor > 0 ? context.getResources().getColor(fillColor, null) : context.getResources().getColor(R.color.transparent, null));
                if (strokeWidth > 0 ) {
                    gd.setStroke(strokeWidth, context.getResources().getColor(strokeColor, null));
                }
            } else {
                gd.setColor(fillColor > 0 ? context.getResources().getColor(fillColor) : context.getResources().getColor(R.color.transparent));
                if (strokeWidth > 0 ) {
                    gd.setStroke(strokeWidth, context.getResources().getColor(strokeColor));
                }
            }
            return gd;
        }
    }
}
