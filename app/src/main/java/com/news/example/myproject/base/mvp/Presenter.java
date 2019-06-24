package com.news.example.myproject.base.mvp;

/**
 * MVP 业务处理层基类，
 *
 * @author xiemy
 * @date 2018/2/18
 */
public interface Presenter<V extends BaseView> {

    /**
     * 绑定View
     *
     * @param mvpView BaseView 基类,需要实现加载错误方法
     */
    void attachView(V mvpView);

    /**
     * 解除绑定
     */
    void detachView();
}

