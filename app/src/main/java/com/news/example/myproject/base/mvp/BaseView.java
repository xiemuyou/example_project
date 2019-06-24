package com.news.example.myproject.base.mvp;

import com.news.example.myproject.znet.InterfaceConfig;

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
     * @param apiTag    请求标签
     * @param errorInfo 错误信息描述
     */
    void loadDataFail(InterfaceConfig.HttpHelperTag apiTag, String errorInfo);
}
