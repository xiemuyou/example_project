package com.news.example.myproject.ui.search.view;

import com.news.example.myproject.base.mvp.BaseView;
import com.news.example.myproject.model.user.UserInfo;

import java.util.List;

/**
 * 更多用户
 * Created by xiemy on 2017/5/19.
 */
public interface MoreUserView extends BaseView {

    /**
     * 成功获取更多用户列表
     *
     * @param userList 用户列表
     */
    void getMoreUserSuccess(List<UserInfo> userList);
}
