package com.doushi.library.widgets.tag;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.doushi.library.R;

import java.util.List;

/**
 * Created by wangyuchao on 15/8/21.
 */
public abstract class TagViewGroup<V extends TextView, T> extends ViewGroup {

    private static final int DEFAULT_MARGIN = 0;

    private int showLine;

    private float tagMarginLeft;
    private float tagMarginTop;
    private float tagMarginRight;
    private float tagMarginBottom;

    private OnTagClickListener onTagClickListener;
    private OnTagCheckedChangeListener onTagCheckedChangeListener;
    private boolean isRadio;

    //是否显示加载更多
    private boolean isShow;

    public TagViewGroup(Context context) {
        this(context, null);
    }

    public TagViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.parseAttributeSet(context, attrs);
    }

    private void parseAttributeSet(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TagViewGroup);
//        if (array.hasValue(R.styleable.TagViewGroup_singleLine)) {
//            setSingleLine(array.getBoolean(R.styleable.TagViewGroup_singleLine, false));
//        }
        if (array.hasValue(R.styleable.TagViewGroup_isRadio)) {
            setRadio(array.getBoolean(R.styleable.TagViewGroup_isRadio, false));
        }
        if (array.hasValue(R.styleable.TagViewGroup_showLine)) {
            setShowLine(array.getInteger(R.styleable.TagViewGroup_showLine, 1));
        }

        //边距默认
        if (array.hasValue(R.styleable.TagViewGroup_tagMargin)) {
            float margin = array.getDimension(R.styleable.TagViewGroup_tagMargin, DEFAULT_MARGIN);
            setTagMarginLeft(margin);
            setTagMarginTop(margin);
            setTagMarginRight(margin);
            setTagMarginBottom(margin);
        }

        if (array.hasValue(R.styleable.TagViewGroup_tagMarginLeft)) {
            setTagMarginLeft(array.getDimension(R.styleable.TagViewGroup_tagMarginLeft, tagMarginLeft));
        }
        if (array.hasValue(R.styleable.TagViewGroup_tagMarginTop)) {
            setTagMarginTop(array.getDimension(R.styleable.TagViewGroup_tagMarginTop, tagMarginTop));
        }
        if (array.hasValue(R.styleable.TagViewGroup_tagMarginRight)) {
            setTagMarginRight(array.getDimension(R.styleable.TagViewGroup_tagMarginRight, tagMarginRight));
        }
        if (array.hasValue(R.styleable.TagViewGroup_tagMarginBottom)) {
            setTagMarginBottom(array.getDimension(R.styleable.TagViewGroup_tagMarginBottom, tagMarginBottom));
        }

        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 获得它的父容器为它设置的测量模式和大小
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        int measuredWidth = 0;
        int measuredHeight = 0;

        int length = 0;
        int rows = 0;

        for (int index = 0; index < getChildCount(); index++) {
            View child = getChildAt(index);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
            int childHeight = child.getMeasuredHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
            if ((length + childWidth) > sizeWidth) {
                if (rows == showLine - 1) {
                    measuredHeight = showLine * childHeight;
                    break;
                } else {
                    if (length != 0) {
                        length = 0;
                        rows++;
                    }
                }
            }
            length = length + childWidth;
            measuredWidth = Math.max(length, measuredWidth);
            measuredHeight = (rows + 1) * childHeight;
        }
        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth : measuredWidth,
                (heightMode == MeasureSpec.EXACTLY) ? sizeHeight : measuredHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int parentWidth = right - left;
        int width = 0;
        int height = 0;
        int rows = 0;//行号

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.setVisibility(View.INVISIBLE);
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) child.getLayoutParams();

            int childWidth = child.getMeasuredWidth() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
            int childHeight = child.getMeasuredHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
            if ((childWidth + width) > parentWidth) {
                if (rows == showLine - 1) {
                    if (width == 0) {//如果第一行太长
                        child.setVisibility(View.VISIBLE);
                        child.layout(width + marginLayoutParams.leftMargin, height + marginLayoutParams.topMargin,
                                parentWidth - marginLayoutParams.rightMargin, height + childHeight - marginLayoutParams.bottomMargin);
                    }
                    isShow = true;
                    break;
                } else {
                    if (width != 0) {
                        width = 0;
                        rows++;
                    }
                }
            }

            height = rows * childHeight;//起点

            child.setVisibility(View.VISIBLE);
            child.layout(width + marginLayoutParams.leftMargin, height + marginLayoutParams.topMargin,
                    Math.min((childWidth + width), parentWidth) - marginLayoutParams.rightMargin,
                    height + childHeight - marginLayoutParams.bottomMargin);

            width = width + childWidth;
        }
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShowLine(Integer showLine) {
        this.showLine = showLine;
        this.requestLayout();
    }

    public void setTagMarginLeft(float tagMarginLeft) {
        this.tagMarginLeft = tagMarginLeft;
    }

    public void setTagMarginTop(float tagMarginTop) {
        this.tagMarginTop = tagMarginTop;
    }

    public void setTagMarginRight(float tagMarginRight) {
        this.tagMarginRight = tagMarginRight;
    }

    public void setTagMarginBottom(float tagMarginBottom) {
        this.tagMarginBottom = tagMarginBottom;
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    public abstract V getTagItemView(int position, T tag);

    public void update(List<T> tags) {
        this.removeAllViews();
        for (int i = 0; i < tags.size(); i++) {
            T tag = tags.get(i);
            if (tag != null) {
                V view = getTagItemView(i, tag);
                MarginLayoutParams marginLayoutParams = new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                marginLayoutParams.setMargins((int) tagMarginLeft, (int) tagMarginTop, (int) tagMarginRight, (int) tagMarginBottom);
                view.setLayoutParams(marginLayoutParams);
                this.addView(view);
                setListener(view, i, tag);
            }
        }
    }

    private void setListener(V view, final int position, final T tag) {
        if (view instanceof CheckBox) {
            //如果是CheckBox
            CheckBox checkBox = (CheckBox) view;
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    if (onTagCheckedChangeListener != null) {
                        if (isRadio) {
                            if (checked) {
                                radio(position);
                                onTagCheckedChangeListener.onTagCheckedChanged(compoundButton, checked, position, tag);
                            }
                        } else {
                            onTagCheckedChangeListener.onTagCheckedChanged(compoundButton, checked, position, tag);
                        }
                    }
                }
            });
        } else {
            //如果是Button
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onTagClickListener != null) {
                        onTagClickListener.onTagClick(position, tag);
                    }
                }
            });
        }
    }

    public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
        this.onTagClickListener = onTagClickListener;
    }

    public void setRadio(boolean radio) {
        isRadio = radio;
    }

    public void setOnTagCheckedChangeListener(OnTagCheckedChangeListener onTagCheckedChangeListener) {
        this.onTagCheckedChangeListener = onTagCheckedChangeListener;
    }

    /**
     * 单选某个position
     */
    private void radio(int position) {
        for (int i = 0; i < getChildCount(); i++) {
            CheckBox checkBox = (CheckBox) getChildAt(i);
            //把之前选中的置为false
            if (i != position) {
                checkBox.setChecked(false);
            }
        }
    }
}
