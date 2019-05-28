package com.doushi.test.myproject.model.news

/**
 * @author xiemy2
 * @date 2019/5/27
 */
data class RecommendResponse(var message: String? = "",
                             var data: List<NewsInfo>? = ArrayList(),
                             var total_number: Int? = 0, // ": 18,
                             var has_more: Boolean? = false, // ": true,
                             var login_status: Int? = 0, // ": 0,
                             var show_et_status: Int? = 0, // ": 0,
                             var post_content_hint: String? = "", // ": "分享今日新鲜事",
                             var has_more_to_refresh: Boolean? = false, // ": true,
                             var action_to_last_stick: String? = "", // ": 0,
                             var feed_flag: String? = "", // ": 0,
                             var tips: TipsInfo? = TipsInfo(), // ": ?
                             var is_use_bytedance_stream: Boolean? = false // ": false

)

data class TipsInfo(
        var type: String? = "", //"": "app",
        var display_duration: Int? = 0, //": 2,
        var display_info: String? = "", //: "今日头条推荐引擎有18条更新",
        var display_template: String? = "", //: "今日头条推荐引擎有%s条更新",
        var open_url: String? = "", //: "",
        var web_url: String? = "", //: "",
        var download_url: String? = "", //: "",
        var app_name: String? = "", //: "今日头条",
        var package_name: String? = ""//: ""
)
