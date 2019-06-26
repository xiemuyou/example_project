package com.news.example.myproject.model.base;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @author xiemy1
 * @date 2018/4/27
 */
public interface BaseMultiItemEntity extends MultiItemEntity {

    @Override
    int getItemType();

    void setItemType(int itemType);
}
