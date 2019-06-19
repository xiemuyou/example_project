package com.doushi.test.myproject.ui.refresh.rp

import android.text.TextUtils
import com.blankj.utilcode.util.StringUtils
import com.doushi.test.myproject.base.mvp.BasePresenter
import com.doushi.test.myproject.global.ParamConstants
import com.doushi.test.myproject.model.base.BaseApiResponse
import com.doushi.test.myproject.model.news.NewsInfo
import com.doushi.test.myproject.model.news.RecommendResponse
import com.doushi.test.myproject.model.user.UserInfo
import com.doushi.test.myproject.ui.refresh.rv.RefreshListView
import com.doushi.test.myproject.znet.InterfaceConfig
import com.doushi.test.myproject.znet.rx.RxRequestCallback
import org.json.JSONObject

import java.util.ArrayList
import java.util.HashMap

/**
 * @author xiemy
 * @date 2018/2/28.
 */
class RefreshPresenter(view: RefreshListView) : BasePresenter<RefreshListView>(view) {

    fun getSearchUsers(searchKey: String) {
        val params = HashMap<String, Any>()
        params[ParamConstants.CATEGORY] = searchKey
        RxRequestCallback().request(params = null, api = InterfaceConfig.HttpHelperTag.NEWS_FEED_V58, presenter = this)
    }

    override fun onLoadDataSuccess(apiTag: InterfaceConfig.HttpHelperTag, modelRes: BaseApiResponse<*>, params: Map<String, Any>) {
    }

    override fun onLoadDataSuccess(apiTag: InterfaceConfig.HttpHelperTag?, res: String?, params: MutableMap<String, Any>?) {
        if (!TextUtils.isEmpty(res)) {
            mvpView.getDataSuccess(parseJson(res!!))
        }
    }

    private fun parseJson(resStr: String): RecommendResponse {
        val res = RecommendResponse()
        val jsons = JSONObject(resStr)
        val isSuccess = StringUtils.equals(jsons.getString("message"), "success")
        if (isSuccess) {
            val newsList = jsons.getJSONArray("data")
            val list = ArrayList<NewsInfo>(newsList.length())
            for (i in 0 until newsList.length()) {
                val news = NewsInfo()
                val newsContent = newsList[i] as JSONObject
                var content = newsContent.getString("content")

                content = content.replace("{", "")
                content = content.replace("}", "")
                content = content.replace("[", "")
                content = content.replace("]", "")
                content = content.replace("(", "")
                content = content.replace(")", "")
                content = content.replace("\\\"", "\"")
                content = content.replace("\"", "")

                val keyValue = content.split(",")
                val images = ArrayList<String>()
                val user = UserInfo()

                tempList = ArrayList()
                keyValue.forEach {
                    if (!it.contains("http")) {
                        val param = it.split(":")
                        val key = if (param.isNotEmpty()) param[0] else ""
                        val value = if (param.size > 1) param[1] else ""
                        if (key == "abstract") {
                            news.content = value
                        }
                        if (key == "title") {
                            news.title = value
                        }
                        if (key == "stick_label") {
                            news.stick_label = value
                        }
                        if (key == "publish_time") {
                            news.publish_time = value.toLongOrNull()
                        }
                        if (key == "name") {
                            user.name = if (value.length > 8) value.replace(value.substring(8), "...") else value
                        }
                    } else {
                        val index = it.indexOf(':')
                        val key = it.substring(0, index)
                        val value = it.substring(index + 1)

                        if (key == "url" && addImageUrl(value)) {
                            images.add(value)
                        }
                        if (key == "display_url") {
                            news.display_url = value
                        }
                        if ("user_info" == key) {
                            value.apply {
                                val uIndex = indexOf(':')
                                val uKey = substring(0, uIndex)
                                val uValue = substring(uIndex + 1)
                                if (uKey == "avatar_url") {
                                    user.avatarUrl = uValue
                                }
                            }
                        }
                    }
                }
                if (images.size > 1) {
                    images.removeAt(images.size - 1)
                }
                news.userInfo = user
                news.images = images
                list.add(news)
            }
            res.data = list
        }
        return res
    }

    private var tempList = ArrayList<String>()
    private fun addImageUrl(url: String): Boolean {
        val vl = url.substring(url.lastIndexOf("-") + 1)
        if (!tempList.contains(vl)) {
            tempList.add(vl)
            return true
        }
        return false
    }
}
