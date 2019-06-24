package com.news.example.myproject.model.user;

import java.io.Serializable;

/**
 * 用户数据
 *
 * @author xiemy
 * @date 2018/2/28.
 */
public class UserInfo implements Serializable {
    private String name;
    private String userId;
    private String avatarUrl;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
