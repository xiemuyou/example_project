package com.doushi.test.myproject.model.base;

import com.google.gson.annotations.Expose;

/**
 * 请求网络返回数据基类
 *
 * @author xiemy
 * @date 2017/4/19.
 */
public class BaseApiResponse<T> {

    @Expose
    private int errcode;

    @Expose
    private int dynamics;

    @Expose
    private String errmsg;

    @Expose
    private T data;

    private long time;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getErrcode() {
        return errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getDynamics() {
        return dynamics;
    }
}
