package com.news.example.myproject.ui.welcome;

import com.news.example.myproject.base.mvp.BaseView;

/**
 * 欢迎页UI更新
 *
 * @author xiemy
 * @date 2017/7/26.
 */
public interface WelcomeView extends BaseView {

    /**
     * 用户 Token 登录,
     *
     * @param showWelcome errorCode
     */
    void loginSuccess(int showWelcome);
}