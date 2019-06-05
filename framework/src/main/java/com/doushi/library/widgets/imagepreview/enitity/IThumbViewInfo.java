package com.doushi.library.widgets.imagepreview.enitity;

import android.graphics.Rect;
import android.os.Parcelable;

/**
 * date 2017/4/26
 * Deprecated: 图片预览接口
 */
public interface IThumbViewInfo extends Parcelable {
    /****
     * 图片地址
     * @return String
     * ****/
    String getUrl();

    String get720Url();

    String get360Url();

    /**
     * 记录坐标
     *
     * @return Rect
     ***/
    Rect getBounds();


}
