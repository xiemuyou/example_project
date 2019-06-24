package com.news.example.myproject.base.listener;

import com.news.example.myproject.base.mvp.Presenter;

/**
 * @author xiemy
 * @date 2018/3/5.
 */
public interface PresenterListener {

    /**
     * 业务处理类添加到列表
     * <p>页面Destroy时便于销毁,不在更新UI</p>
     *
     * @param presenter 业务处理类
     */
    void addPresenter(Presenter presenter);
}
