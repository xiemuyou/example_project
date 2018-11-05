package com.doushi.test.myproject.znet;

import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;

/**
 * 网络请求配置,application配置默认的请求配置
 * <p>
 * CacheTime 缓存时长
 * RetryCount 重连次数
 * CacheMode 读取缓存模式
 * ...
 * </p>
 * 一些特殊接口,可以实现当前类进行配置更改
 *
 * @author xiemy1
 * @date 2018/4/23
 */
public class RequestConfig {

    private long cacheTime;
    private int retryCount;
    private CacheMode cacheMode;

    /**
     * 全局默认网络请求配置
     */
    public RequestConfig() {
        //缓存时长
        this.cacheTime = CacheEntity.CACHE_NEVER_EXPIRE;
        //重连次数
        this.retryCount = 3;
        //读取缓存模式
        this.cacheMode = CacheMode.REQUEST_FAILED_READ_CACHE;
    }

    public long getCacheTime() {
        return cacheTime;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public CacheMode getCacheMode() {
        return cacheMode;
    }

    public RequestConfig setCacheTime(long cacheTime) {
        this.cacheTime = cacheTime;
        return this;
    }

    public RequestConfig setRetryCount(int retryCount) {
        this.retryCount = retryCount;
        return this;
    }

    public RequestConfig setCacheMode(CacheMode cacheMode) {
        this.cacheMode = cacheMode;
        return this;
    }
}
