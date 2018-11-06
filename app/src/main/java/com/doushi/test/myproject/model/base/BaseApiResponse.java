package com.doushi.test.myproject.model.base;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * 请求网络返回数据基类
 *
 * @author xiemy
 * @date 2017/4/19.
 */
public class BaseApiResponse<T> {

    @Expose
    private boolean error;

    @Expose
    private List<String> category;

    @Expose
    private T results;

    private long time;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public List<String> getCategory() {
        return category;
    }

    public T getResults() {
        return results;
    }

    public boolean isError() {
        return error;
    }
}
