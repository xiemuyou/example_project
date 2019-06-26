package com.news.example.myproject.model.base;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author xiemy2
 * @date 2019/6/26
 */
public class Adapter extends BaseMultiItemQuickAdapter<BaseMultiItemEntity, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public Adapter(Context context, List<BaseMultiItemEntity> data) {
        super(data);
        View view = new View(context);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseMultiItemEntity item) {

    }
}
