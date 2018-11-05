package com.doushi.library.util;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

/**
 * 图片加载工具类
 * 参考链接 : https://my.oschina.net/u/1268043/blog/1591758
 *
 * @author xiemy
 */
public class ImageLoadUtils {

    private RequestManager requestManager;

    public ImageLoadUtils(Activity activity) {
        requestManager = Glide.with(activity);
    }

    public ImageLoadUtils(Fragment fragment) {
        requestManager = Glide.with(fragment);
    }

    public ImageLoadUtils(Context context) {
        requestManager = Glide.with(context);
    }

    public ImageLoadUtils(View view) {
        requestManager = Glide.with(view);
    }

    public void commonDisplayImage(@NonNull String imgUrl, @NonNull ImageView imageView, @DrawableRes int drawableRes) {
        if (requestManager != null) {
            requestManager.load(imgUrl).apply(getRequestOptions(drawableRes)).into(imageView);
        }
    }

    public void commonCircleImage(@NonNull String imgUrl, @NonNull ImageView imageView, @DrawableRes int drawableRes) {
        if (requestManager != null) {
            requestManager.load(imgUrl).apply(getRequestOptions(drawableRes).transform(new CircleCrop())).into(imageView);
        }
    }

    private RequestOptions getRequestOptions(@DrawableRes int drawableRes) {
        return new RequestOptions().placeholder(drawableRes).error(drawableRes);
    }

    /**
     * 清除Glide内存缓存
     *
     * @param context 上下文对象
     * @return 是否清除成功
     */
    public static boolean clearCacheMemory(Context context) {
        try {
            //只能在主线程执行
            if (Looper.myLooper() == Looper.getMainLooper()) {
                Glide.get(context).clearMemory();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
