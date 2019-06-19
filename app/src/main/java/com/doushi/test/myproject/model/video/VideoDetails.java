package com.doushi.test.myproject.model.video;

import com.doushi.test.myproject.model.user.UserInfo;

import java.io.Serializable;

/**
 * @author xiemy
 * @date 2018/3/20.
 */
public class VideoDetails implements Serializable {
    private int vid;
    private String imgUrl;
    private String mp4Url;
    private String description;
    private long createTime;
    private UserInfo userInfo;

    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getMp4Url() {
        return mp4Url;
    }

    public void setMp4Url(String mp4Url) {
        this.mp4Url = mp4Url;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
