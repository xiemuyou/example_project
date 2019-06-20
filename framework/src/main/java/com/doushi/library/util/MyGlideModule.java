package com.doushi.library.util;

import android.content.Context;
import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.module.GlideModule;
import com.bumptech.glide.request.target.ViewTarget;

/**
 * 解决glide 加载列表 item错乱
 * https://github.com/bumptech/glide/releases/tag/v3.6.0
 *
 * @author zhangw
 * @date 2017/6/20.
 */
public class MyGlideModule implements GlideModule {

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        ViewTarget.setTagId(com.doushi.library.R.id.glide_tag);
    }

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
//        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
//        int memoryCacheSize = calculator.getMemoryCacheSize();
//        int bitmapPoolSize = calculator.getBitmapPoolSize();
//
//        int customCacheSize = (int) (0.2 * memoryCacheSize);
//        int customBitmapPoolSize = (int) (0.2 * bitmapPoolSize);
//
//        builder.setMemoryCache(new LruResourceCache(customCacheSize));
//        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));
//        Log.e("AAA", "=============================================");
    }
}
