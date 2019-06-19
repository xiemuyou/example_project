package com.doushi.test.myproject.model.video;

import com.doushi.test.myproject.model.user.UserInfo;

import java.io.Serializable;

/**
 * @author xiemy
 * @date 2018/3/20.
 */
public class VideoDetails implements Serializable {
    private int vid;
    private String head;
    private String nick;
    private String imgUrl;
    private String mp4Url;
    private UserInfo userInfo;

    public VideoDetails(int vid, String head, String nick, String imgUrl, String mp4Url) {
        this.vid = vid;
        this.head = head;
        this.nick = nick;
        this.imgUrl = imgUrl;
        this.mp4Url = mp4Url;
    }

    public int getVid() {
        return vid;
    }

    public String getHead() {
        return head;
    }

    public String getNick() {
        return nick;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getMp4Url() {
        return mp4Url;
    }
}
