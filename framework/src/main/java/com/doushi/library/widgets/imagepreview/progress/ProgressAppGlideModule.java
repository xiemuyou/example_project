package com.doushi.library.widgets.imagepreview.progress;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

/**
 * @author by sunfusheng on 2017/6/14.
 */
@GlideModule
public class ProgressAppGlideModule extends AppGlideModule {

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
//        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(ProgressManager.getOkHttpClient()));
    }

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        MemorySizeCalculator calculator = new MemorySizeCalculator
                .Builder(context)
                .build();

        builder.setMemoryCache(new LruResourceCache((int) (calculator.getMemoryCacheSize() * 0.2)));
        builder.setBitmapPool(new LruBitmapPool((int) (calculator.getBitmapPoolSize() * 0.2)));
        builder.setDiskCache(
                new InternalCacheDiskCacheFactory(context, DiskCache.Factory.DEFAULT_DISK_CACHE_DIR, 1024 * 1024 * 500));
    }
}