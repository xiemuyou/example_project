package com.library.widgets.fcnestedscrollview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.library.R;

/**
 * Created by fangcan on 2018/3/28.
 */

public class FCRecyclerView extends RecyclerView {
    public static final int MODEL_ALL = 0; //自己优先滚动
    public static final int MODEL_UP = 1; //自己优先向上滚动
    public static final int MODEL_DOWN = 2; //自己优先向下滚动
    public static final int MODEL_NONE = 3; //自己最后滚动
    private static final String TAG = "FCRecycleView";

    private int nestedScrollModel = MODEL_ALL;
    /**
     * 当滚到顶或底部的时候 否联动parent滚动
     */
    private boolean isLinkedParent;

    public FCRecyclerView(Context context) {
        this(context, null);
    }

    public FCRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FCRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FCRecyclerView);
        nestedScrollModel = typedArray.getInt(R.styleable.FCRecyclerView_fc_scroll_mode, MODEL_ALL);
        isLinkedParent = typedArray.getBoolean(R.styleable.FCRecyclerView_fc_is_linked_parent, true);
        typedArray.recycle();
    }

    public int getNestedScrollModel() {
        return nestedScrollModel;
    }

    public void setNestedScrollModel(int nestedScrollModel) {
        this.nestedScrollModel = nestedScrollModel;
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {
        boolean flag = super.fling(velocityX, velocityY);
        FcNestedUtil.getCurrVelocityXY(this);
        return flag;
    }

    /**
     * 当滚到顶或底部的时候 否联动parent滚动
     *
     * @param direction < 0 向下滚，> 0 向上滚
     * @return
     */
    protected boolean isLinkedParentFling(int direction) {
        return isLinkedParent;
    }

    public void setLinkedParent(boolean linkedParent) {
        isLinkedParent = linkedParent;
    }

    protected boolean isCanScrollVertically(int direction) {
        boolean flag = false;
        switch (getNestedScrollModel()) {
            case MODEL_ALL:
                flag = canScrollVertically(direction);
                break;
            case MODEL_DOWN:
                flag = direction < 0 && canScrollVertically(direction);
                break;
            case MODEL_UP:
                flag = direction > 0 && canScrollVertically(direction);
                break;
            case MODEL_NONE:
                flag = false;
                break;
            default:
                break;
        }
        return flag;
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return super.startNestedScroll(axes);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, ViewCompat.TYPE_TOUCH);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow, int type) {
        if (isCanScrollVertically(dy)) {
            return false;
        } else {
            if (type == ViewCompat.TYPE_TOUCH || isLinkedParentFling(dy)) {
                return super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type);
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, ViewCompat.TYPE_TOUCH);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow, int type) {
        if (type == ViewCompat.TYPE_TOUCH || isLinkedParentFling(dyUnconsumed)) {
            return super.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type);
        } else {
            return false;
        }
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        if (isCanScrollVertically((int) velocityY)) {
            return false;
        } else {
            return super.dispatchNestedPreFling(velocityX, velocityY);
        }
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return super.dispatchNestedFling(velocityX, velocityY, consumed && isCanScrollVertically((int) velocityY));
    }
}
