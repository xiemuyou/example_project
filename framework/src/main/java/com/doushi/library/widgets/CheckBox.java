package com.doushi.library.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doushi.library.R;

/**
 * @author user
 * @date 2018/4/19
 */
public class CheckBox extends LinearLayout {

    private boolean isSelected = false;
    private int type = 1;
    private Context context;
    private ImageView checkbox;

    /**
     * 关注
     */
    private final static int FOCUS = 1;

    public CheckBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    public CheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }

    public CheckBox(Context context) {
        super(context);
        this.context = context;
    }

    @SuppressLint("Recycle")
    private void init(AttributeSet attrs) {
        checkbox = new ImageView(context);
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        this.addView(checkbox, lp);

        TextView tv = new TextView(context);
        TypedArray attribute = context.obtainStyledAttributes(attrs, R.styleable.CheckBox);
        type = attribute.getInteger(R.styleable.CheckBox_type, 1);
        String text = attribute.getString(R.styleable.CheckBox_text);
        int textColor = attribute.getInteger(R.styleable.CheckBox_textColor, Color.BLACK);
        int textSize = attribute.getInteger(R.styleable.CheckBox_textSize, 14);
        tv.setText(text);
        tv.setTextColor(textColor);
        tv.setGravity(Gravity.CENTER_VERTICAL);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
        setBackground();
        checkbox.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (onClick != null) {
                    isSelected = !isSelected;
                    setBackground();
                    onClick.click(CheckBox.this, isSelected);
                }
            }
        });

        tv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                isSelected = !isSelected;
                setBackground();
                onClick.click(CheckBox.this, isSelected);
            }
        });
        attribute.recycle();
    }

    public void setChecked(boolean isSelected) {
        this.isSelected = isSelected;
        setBackground();
    }

    public boolean isChecked() {
        return isSelected;
    }

    public void setBackground() {
        switch (type) {
            case FOCUS:
                checkbox.setBackgroundResource(isSelected ? R.drawable.default_selected : R.drawable.default_unselect);
                break;

            default:
                checkbox.setBackgroundResource(isSelected ? R.drawable.default_selected : R.drawable.default_unselect);
                break;
        }
    }

    public OnClick onClick;

    /**
     * 设置点击监听
     *
     * @param onClick 点击监听回调
     */
    public void setOnClicked(OnClick onClick) {
        this.onClick = onClick;
    }

    public interface OnClick {

        /**
         * 点击
         *
         * @param v         视图
         * @param isChecked 是否选择
         */
        void click(View v, boolean isChecked);
    }
}
