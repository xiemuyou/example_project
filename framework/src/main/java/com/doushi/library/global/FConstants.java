package com.doushi.library.global;

/**
 * 全局常量
 * Created by xiemy on 2017/4/19.
 */
public class FConstants {

    /**
     * debug 标识
     */
    public static String DEBUG_TAG = "SanTong";
    /**
     * DEBUG 模式
     */
    public static boolean debug = true;
    /**
     * 全局网络读取超时时间
     */
    public static final long DEFAULT_MILLISECONDS = 30000;
    /**
     * 服务器与客户端相差时长,客户端时间/1000获取秒数 - 服务器秒数 = 相差时长,请求时加上相差时长用于验证
     */
    public static long SERVER_DIFFERENCE_TIME;
    /**
     * 端口
     */
    public static final String PORT = "port";
    /**
     * 用户ID
     */
    public static final String MY_UID = "myuid";
    /**
     * 用户Token
     */
    public static final String TOKEN = "token";
    /**
     * 用户ID,一般用于第三方<>获取他人详细信息,获取他人视频列表</>
     */
    public static final String UID = "uid";
    /**
     * 用户类型,游客/登录用户
     */
    public static final String USER_TYPE = "userType";
    /**
     * 游客类型
     */
    public static final int VISITOR_TYPE = 1;
}
