package com.doushi.test.myproject.model.user;

import java.io.Serializable;
import java.util.List;

/**
 * LoginByToken 返回数据
 *
 * @author xiemy
 * @date 2017/8/10.
 */
public class ConfigData implements Serializable {

    private UserInfo userInfo;
    private String outIp;
    private String city;

    /**
     * 购买视频列表
     */
    private List<Integer> paidVids;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public String getOutIp() {
        return outIp;
    }

    public String getCity() {
        return city;
    }

    public List<Integer> getPaidVids() {
        return paidVids;
    }

    public void setPaidVids(List<Integer> paidVids) {
        this.paidVids = paidVids;
    }
}
