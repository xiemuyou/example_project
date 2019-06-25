package com.news.example.myproject.znet

import com.lzy.okgo.cache.CacheMode

/**
 * 接口配置
 *
 * @author xiemy2
 * @date 2019/3/14
 */
object InterfaceConfig {

    internal const val METHOD_POST = "POST"
    internal const val METHOD_GET = "GET"

    enum class HttpHelperTag
    /**
     * API 接口配置
     *
     * @param method 请求类型 post|get
     * @param apiTag 接口api
     * @param rType  地址类型
     */
    constructor(
            /**地址类型(支付/默认)  */
            @param:HttpConfig.RootType @field:HttpConfig.RootType
            private val rType: Int = HttpConfig.API_OPEN,
            /**请求类型 post|get */
            val method: String? = METHOD_GET,
            /**接口api*/
            private val apiTag: String,
            /**网络请求配置api*/
            var config: RequestConfig? = null) {

        /* ------------------------ > 用户  User Start <---------------------- */
        /**
         * 获取最新一天的干货
         */
        TODAY(HttpConfig.SNS_SDK, "today"),
        /**
         *热搜
         */
        ARTICLE_HOT_WORDS(HttpConfig.SNS_SDK, "2/article/hot_words/"),
        /**
         * 分类列表
         */
        ARTICLE_CATEGORY_GET_EXTRA_V1(HttpConfig.SNS_SDK, "article/category/get_extra/v1/"),
        /**
         * 分类列表
         */
        NEWS_FEED_V58(HttpConfig.SNS_SDK, "api/news/feed/v58/"),
        /**
         * 崩溃上报
         */
        RX_URL_REPORT_CRASH_LOG(HttpConfig.SNS_SDK, "UiP1/ReportCrashLog"),
        /* ------------------------ > 用户  User Start <---------------------- */


        /* ------------------------ > 用户  User Start <---------------------- */
        /***获取支付渠道 https://api.apiopen.top/todayVideo */
        TODAY_VIDEO(HttpConfig.API_OPEN,
                "/todayVideo",
                RequestConfig().setCacheMode(CacheMode.NO_CACHE)
                        .setCacheTime(10000).setRetryCount(3));
        /* ------------------------ > 用户  User Start <---------------------- */

        /**是否有需要回调到View(前台显示)*/
        var isAttachedView = true

        /**
         * API 接口配置
         *
         * @param apiTag 接口api
         */
        constructor(apiTag: String) : this(METHOD_GET, apiTag)

        /**
         * API 接口配置
         *
         * @param method 请求类型 post|get
         * @param apiTag 接口api
         */
        constructor(method: String, apiTag: String) : this(HttpConfig.API_OPEN, method, apiTag)

        /**
         * API 接口配置
         *
         * @param apiTag 接口api
         * @param rType  地址类型
         */
        constructor(@HttpConfig.RootType rType: Int, apiTag: String) : this(rType, METHOD_GET, apiTag)

        /**
         * API 接口配置
         *
         * @param apiTag    接口api
         * @param rType     地址类型
         * @param config    网络配置
         */
        constructor(@HttpConfig.RootType rType: Int, apiTag: String, config: RequestConfig) : this(rType, METHOD_GET, apiTag, config)

        val apiUrl: String
            get() = HttpConfig.getRootUrl(rType) + apiTag
    }
}
