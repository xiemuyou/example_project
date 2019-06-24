package com.news.example.myproject.znet;

import java.io.Serializable;

/**
 * 请求 Cookie
 *
 * @author xiemy
 * @date 2016/6/5.
 */
public class RequestCookie implements Serializable {

    private static final long serialVersionUID = -1353850465936313952L;

    /**
     * int	客户端unix时间戳，需要跟服务器时间做同步
     */
    private long ts;
    /**
     * 客户端id
     * 1：iphone
     * 2：android phone
     * 3：ipad
     * 4：android pad
     * 5：其他
     */
    private int devtype;
    /**
     * 操作系统版本，如8.0.0
     */
    private String osver;
    /**
     * 设备id
     */
    private String mid;
    /**
     * MD5校验值
     */
    private String sum;
    /**
     * 渠道ID
     */
    private int chid;
    /**
     * 网络环境
     */
    private String net;
    /**
     * 极光设备id
     */
    private String did_jg;

    public String getDid_jg() {
        return did_jg;
    }

    public void setDid_jg(String did_jg) {
        this.did_jg = did_jg;
    }

    public void setNet(String net) {
        this.net = net;
    }

    public int getChid() {
        return chid;
    }

    public String getNet() {
        return net;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public int getDevtype() {
        return devtype;
    }

    public void setDevtype(int devtype) {
        this.devtype = devtype;
    }

    public String getOsver() {
        return osver;
    }

    public void setOsver(String osver) {
        this.osver = osver;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public void setChid(int chid) {
        this.chid = chid;
    }
}
