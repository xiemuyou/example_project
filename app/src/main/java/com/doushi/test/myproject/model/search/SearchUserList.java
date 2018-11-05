package com.doushi.test.myproject.model.search;

import com.doushi.test.myproject.model.user.UserInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiemy
 * @date 2018/3/8.
 */
public class SearchUserList implements Serializable {

    /**
     * 用户数量
     */
    private int user_count;

    /**
     * 搜索用户列表
     */
    private List<UserInfo> user_list;

    public int getUser_count() {
        return user_count;
    }

    public List<UserInfo> getUser_list() {
        if (user_list == null) {
            user_list = new ArrayList<>();
        }
        return user_list;
    }
}
