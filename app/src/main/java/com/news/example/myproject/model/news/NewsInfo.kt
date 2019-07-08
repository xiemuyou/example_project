package com.news.example.myproject.model.news

import com.news.example.myproject.model.user.UserInfo

/**
 * @author xiemy2
 * @date 2019/5/27
 */
data class NewsInfo(
        var content: String? = "",
        var title: String? = "",
        var newsId: String? = "",
        var stick_label: String? = "",
        var publish_time: Long? = 0L,
        var display_url: String? = "",
        var userInfo: UserInfo? = UserInfo(),
        var images: List<String>? = ArrayList()
) {
    override fun equals(other: Any?): Boolean {
        return other is NewsInfo && other.newsId == newsId
    }

    override fun hashCode(): Int {
        return newsId?.hashCode() ?: 0
    }
}