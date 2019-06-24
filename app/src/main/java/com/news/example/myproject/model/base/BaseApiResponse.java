package com.news.example.myproject.model.base;

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
    private T result;

    @Expose
    private T data;

    private long time;
    private String message;
    private int code;

    public boolean isSuccess() {
        return "success".equals(message);
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public List<String> getCategory() {
        return category;
    }

    public T getData() {
        return result != null ? result : data;
    }

    public boolean isError() {
        return !isSuccess();
    }
}
