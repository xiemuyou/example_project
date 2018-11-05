package com.doushi.test.myproject.base.mvp;

/**
 * MVP View层基类,负责更新UI
 *
 * @author xiemy
 * @date 2018/2/18
 */
public interface BaseView {

    /**
     * 请求失败
     *
     * @param errorInfo 错误信息描述
     */
    void loadDataFail(String errorInfo);
}
