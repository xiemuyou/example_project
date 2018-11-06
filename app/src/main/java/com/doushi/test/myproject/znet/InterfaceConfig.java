package com.doushi.test.myproject.znet;

import com.lzy.okgo.cache.CacheMode;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求接口配置信息
 *
 * @author xiemy
 * @date 2018/2/28
 */
public class InterfaceConfig {

    /**
     * 请求接口
     */
    public static final String INTERFACE_URL_STR = "INTERFACE_URL_STR";
    /**
     * 配置请求类型 POST/GET
     */
    public static final String INTERFACE_QUERY_TYPE = "INTERFACE_QUERY_TYPE";
    /**
     * 缓存模式,默认Application配置,一些特殊接口可修改此项配置
     */
    public static final String REQUEST_CONFIG = "RequestConfig";

    public static final String INTERFACE_QUERY_TYPE_POST = "POST";
    public static final String INTERFACE_QUERY_TYPE_GET = "GET";
    /**
     * 上传文件类型
     */
    public static final String InterfaceQueryType_POST_UPDATE = "POST_UPDATE";

    /**
     * 用户信息参数类型
     */
    private static final String PARAMS_TYPE = "PARAMS_TYPE";

    public enum HttpHelperTag {

        /**
         * 获取最新一天的干货
         */
        HTTPHelperTag_ToDay,


        //崩溃上报 start
        /**
         * 崩溃上报
         * ReportCrashLog
         */
        HTTPHelperTag_ReportCrashLog,
        //崩溃上报 end

    }

    //start----user---start
    /**
     * 获取最新一天的干货
     */
    private static final String TODAY = "today";
    /**
     * 崩溃上报
     */
    private static final String RX_URL_REPORT_CRASH_LOG = "UiP1/ReportCrashLog";
    //崩溃上报 end===============

    public static Map<String, Object> interfaceConfigByHttpTag(HttpHelperTag httpHelperTag) {

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(REQUEST_CONFIG, false);
        StringBuffer urlStr = new StringBuffer();
        urlStr.append(HttpConfig.getRootUrl());

        // 默认GET
        String queryTypeStr;
        switch (httpHelperTag) {
            // 获取最新一天的干货
            case HTTPHelperTag_ToDay:
                //2.1.3	token登录
                urlStr.append(TODAY);
                resultMap.put(INTERFACE_URL_STR, urlStr.toString());
                resultMap.put(REQUEST_CONFIG,
                        new RequestConfig()
                                //不重连
                                .setRetryCount(0)
                                // 不使用缓存
                                .setCacheMode(CacheMode.NO_CACHE));
                break;

            // region ----- 上传崩溃   ------
            //崩溃上报 GET
            case HTTPHelperTag_ReportCrashLog:
                urlStr.append(RX_URL_REPORT_CRASH_LOG);
                resultMap.put(INTERFACE_URL_STR, urlStr.toString());
                resultMap.put(REQUEST_CONFIG,
                        new RequestConfig()
                                //不重连
                                .setRetryCount(0)
                                // 不使用缓存
                                .setCacheMode(CacheMode.NO_CACHE));
                break;
            // endregion ----- 上传崩溃 ----end

            default:
                urlStr = new StringBuffer();
                resultMap.put(INTERFACE_URL_STR, urlStr.toString());
                queryTypeStr = "";
                resultMap.put(INTERFACE_QUERY_TYPE, queryTypeStr);
                break;
        }
        return resultMap;
    }
}