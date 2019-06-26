package com.news.example.myproject.widgets.reward;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Grid 分割线
 * Created by xiemy on 2017/5/16.
 */
public class RectangleItemDecoration extends RecyclerView.ItemDecoration {

    //分割线宽度
    protected final int mSpace;

    public RectangleItemDecoration(int space) {
        mSpace = space;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.left = mSpace / 2;
        outRect.right = mSpace / 2;
        outRect.top = mSpace / 2;
        outRect.bottom = mSpace / 2;
    }
}