package com.news.example.myproject.model.news

import com.library.util.LogUtil
import com.news.example.myproject.model.user.UserInfo
import java.io.Serializable

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
) : Serializable {

    override fun equals(other: Any?): Boolean {
        val isEquals = other is NewsInfo && (other.newsId == newsId || other.title == title)
        if (other is NewsInfo && other.title == title) {
            LogUtil.d("NewsInfo", "otherId=${other.newsId}==$newsId and otherName=${other.title} == $title and isEquals=$isEquals")
        }
        return isEquals
    }

    override fun hashCode(): Int {
        var result = title?.hashCode() ?: 0
        result = 31 * result + (newsId?.hashCode() ?: 0)
        return result
    }
}