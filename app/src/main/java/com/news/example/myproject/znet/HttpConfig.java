package com.news.example.myproject.znet;import androidx.annotation.IntDef;import java.lang.annotation.Retention;import java.lang.annotation.RetentionPolicy;/** * 请求配置 * * @author xiemy * @date 2017/7/27 */public class HttpConfig {    //https://www.jianshu.com/p/e6f072839282    //https://www.wanandroid.com/openapis    //http://myersguo.cn/2017/03/01/toutiao-interface.html    //https://github.com/iMeiji/Toutiao/wiki/%E4%BB%8A%E6%97%A5%E5%A4%B4%E6%9D%A1Api%E5%88%86%E6%9E%90#%E8%8E%B7%E5%8F%96%E6%96%B0%E9%97%BB    public static String TAG = "HttpConfig";    /**     * https://is.snssdk.com/article/category/get_extra/v1/     */    private static final String SN_URL = "http://is.snssdk.com";    /**     * https://api.apiopen.top     */    private static final String OPEN_URL = "http://api.apiopen.top";    /**     * http://m.toutiao.com     */    private static final String M_TT_UTL = "http://m.toutiao.com";    static String getRootUrl(@RootType int type) {        StringBuffer url;        switch (type) {            //https://is.snssdk.com            case SNS_SDK:                url = new StringBuffer(SN_URL).append("/");                break;            //https://api.apiopen.top            case API_OPEN:                url = new StringBuffer(OPEN_URL).append("/");                break;            case MTT:                url = new StringBuffer(M_TT_UTL).append("/");                break;            //https://api.apiopen.top            default:                url = new StringBuffer(OPEN_URL).append("/");                break;        }        return url.toString();    }    public static final int SNS_SDK = 0;    public static final int API_OPEN = 1;    public static final int MTT = 2;    //接口，定义新的注解类型    @IntDef({SNS_SDK, API_OPEN, MTT})    @Retention(RetentionPolicy.SOURCE)    @interface RootType {    }}