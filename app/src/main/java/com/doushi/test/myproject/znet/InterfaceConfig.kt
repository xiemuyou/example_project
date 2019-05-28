package com.doushi.test.myproject.znet

import com.lzy.okgo.cache.CacheMode

import java.util.HashMap

/**
 * 请求接口配置信息
 *
 * @author xiemy
 * @date 2018/2/28
 */
object InterfaceConfig {

    /**
     * 请求接口
     */
    val INTERFACE_URL_STR = "INTERFACE_URL_STR"
    /**
     * 配置请求类型 POST/GET
     */
    val INTERFACE_QUERY_TYPE = "INTERFACE_QUERY_TYPE"
    /**
     * 缓存模式,默认Application配置,一些特殊接口可修改此项配置
     */
    val REQUEST_CONFIG = "RequestConfig"

    val INTERFACE_QUERY_TYPE_POST = "POST"
    val INTERFACE_QUERY_TYPE_GET = "GET"
    /**
     * 上传文件类型
     */
    val InterfaceQueryType_POST_UPDATE = "POST_UPDATE"

    /**
     * 用户信息参数类型
     */
    private val PARAMS_TYPE = "PARAMS_TYPE"

    //start----user---start
    /**
     * 获取最新一天的干货
     */
    private val TODAY = "today"
    /**
     * 分类列表
     */
    private val ARTICLE_CATEGORY_GET_EXTRA_V1 = "article/category/get_extra/v1/"
    /**
     * 分类列表
     */
    private val NEWS_FEED_V58 = "api/news/feed/v58/"
    /**
     * 分类列表
     */
    private val NATIVE_FEED_BROW = "wenda/v1/native/feedbrow"
    /**
     * 崩溃上报
     */
    private val RX_URL_REPORT_CRASH_LOG = "UiP1/ReportCrashLog"

    enum class HttpHelperTag {

        /**
         * 获取最新一天的干货
         */
        HTTPHelperTag_ToDay,
        /**
         * 获取最新一天的干货
         */
        HTTPHelperTag_GET_EXTRA,
        /**
         * 获取最新一天的干货
         * /wenda/v1/native/feedbrow/
         */
        HTTPHelperTag_NativeFeedBrow,
        /**
         * 获取最新一天的干货
         * https://is.snssdk.com/api/news/feed/v58/
         */
        HTTPHelperTag_NesFeedV58,


        //崩溃上报 start
        /**
         * 崩溃上报
         * ReportCrashLog
         */
        HTTPHelperTag_ReportCrashLog
        //崩溃上报 end

    }
    //崩溃上报 end===============

    fun interfaceConfigByHttpTag(httpHelperTag: HttpHelperTag): Map<String, Any> {

        val resultMap = HashMap<String, Any>()
        resultMap[REQUEST_CONFIG] = false
        val urlStr = StringBuffer()
        urlStr.append(HttpConfig.getRootUrl())

        when (httpHelperTag) {
            // 获取最新一天的干货
            InterfaceConfig.HttpHelperTag.HTTPHelperTag_ToDay -> {
                //2.1.3	token登录
                urlStr.append(TODAY)
                resultMap[INTERFACE_URL_STR] = urlStr.toString()
                resultMap[REQUEST_CONFIG] = RequestConfig()
                        //不重连
                        .setRetryCount(0)
                        // 不使用缓存
                        .setCacheMode(CacheMode.NO_CACHE)
            }

            //分类列表
            InterfaceConfig.HttpHelperTag.HTTPHelperTag_GET_EXTRA -> {
                urlStr.append(ARTICLE_CATEGORY_GET_EXTRA_V1)
                resultMap[INTERFACE_URL_STR] = urlStr.toString()
            }

            //分类列表
            InterfaceConfig.HttpHelperTag.HTTPHelperTag_NesFeedV58 -> {
                urlStr.append(NEWS_FEED_V58)
                resultMap[INTERFACE_URL_STR] = urlStr.toString()
            }

            //分类详情
            InterfaceConfig.HttpHelperTag.HTTPHelperTag_NativeFeedBrow -> {
                urlStr.append(NATIVE_FEED_BROW)
                resultMap[INTERFACE_URL_STR] = urlStr.toString()
            }

            // region ----- 上传崩溃   ------
            //崩溃上报 GET
            InterfaceConfig.HttpHelperTag.HTTPHelperTag_ReportCrashLog -> {
                urlStr.append(RX_URL_REPORT_CRASH_LOG)
                resultMap[INTERFACE_URL_STR] = urlStr.toString()
            }
            // endregion ----- 上传崩溃 ----end

        }// 获取最新一天的干货
        return resultMap
    }
}