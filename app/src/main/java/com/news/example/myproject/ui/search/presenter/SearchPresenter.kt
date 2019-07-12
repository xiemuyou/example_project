package com.news.example.myproject.ui.search.presenter

import android.text.TextUtils
import com.blankj.utilcode.util.StringUtils
import com.google.gson.reflect.TypeToken
import com.library.util.JsonUtil
import com.library.util.JsonUtil.jsonToList
import com.library.util.PreferencesUtils
import com.library.util.VerificationUtils
import com.news.example.myproject.base.mvp.BasePresenter
import com.news.example.myproject.global.Constants
import com.news.example.myproject.model.base.BaseApiResponse
import com.news.example.myproject.model.news.NewsInfo
import com.news.example.myproject.model.news.RecommendResponse
import com.news.example.myproject.model.user.UserInfo
import com.news.example.myproject.ui.search.view.SearchView
import com.news.example.myproject.znet.InterfaceConfig
import com.news.example.myproject.znet.rx.RxRequestCallback
import io.reactivex.annotations.NonNull
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

/**
 * 搜索类实现
 *
 * @author xiemy
 * @date 2017/5/19
 */
class SearchPresenter(searchView: SearchView) : BasePresenter<SearchView>() {

    private var historySearchKeys: ArrayList<String>? = null
    private val MAX_HISTORY_SEARCH_SIZE = 10
    private val HISTORY_SEARCH_KEYS = "historySearchKeys"

    init {
        attachView(searchView)
    }

    /**
     * 搜索
     * https://www.toutiao.com/search/?keyword=
     *
     * @param keyword 用户输入要字符串
     */
    fun search(keyword: String?): Boolean {
        if (VerificationUtils.isFastDoubleClick()) {
            return false
        }
        val result = !TextUtils.isEmpty(keyword) && mvpView != null
        if (keyword != null) {
            addHistorySearchKeys(keyword)
            val map = HashMap<String, Any>()
            map["keyword"] = keyword
            RxRequestCallback().request(params = map, api = InterfaceConfig.HttpHelperTag.SEARCH_TOU_TIAO, presenter = this)
        }
        return result
    }

    /**
     * 搜索
     * http://is.snssdk.com/api/2/wap/search_content/?from=search_tab&iid=12507202490&device_id=37487219424&count=10&format=json&keyword=特朗普&cur_tab=1&offset=0
     *
     * @param keyword 用户输入要字符串
     */
    fun searchNew(keyword: String?): Boolean {
        if (VerificationUtils.isFastDoubleClick()) {
            return false
        }
        val result = !TextUtils.isEmpty(keyword) && mvpView != null
        if (keyword != null) {
            addHistorySearchKeys(keyword)
            val map = HashMap<String, Any>()
            map["device_id"] = "37487219424"
            map["count"] = Constants.CNT_NUMBER
            map["cur_tab"] = 1
            map["keyword"] = keyword
            RxRequestCallback().request(params = map, api = InterfaceConfig.HttpHelperTag.SEARCH_TOU_TIAO_NEW, presenter = this)
        }
        return result
    }

    override fun onLoadDataSuccess(apiTag: InterfaceConfig.HttpHelperTag?, res: String?, params: MutableMap<String, Any>?) {
        super.onLoadDataSuccess(apiTag, res, params)
        mvpView.getSearchSuccess(res)
    }

    override fun onLoadDataSuccess(apiTag: InterfaceConfig.HttpHelperTag, modelRes: BaseApiResponse<*>?, params: Map<String, Any>?) {
        //nothing
    }


    private fun parseJson(resStr: String): RecommendResponse? {
        val res = RecommendResponse()
        val jsons = JSONObject(resStr)
        val isSuccess = StringUtils.equals(jsons.getString("message"), "success")
        if (isSuccess) {
            val newsList = jsons.getJSONArray("data")
            val list = java.util.ArrayList<NewsInfo>(newsList.length())
            for (i in 0 until newsList.length()) {
                val newsContent = newsList[i] as? JSONObject
                val news = NewsInfo()

                news.newsId = newsContent?.getString("id")
                //news1.newsId = newsContent?.getString("id_str")

                val display = newsContent?.getJSONObject("display")
                val emphasized = display?.getJSONObject("emphasized")
                news.content = emphasized?.getString("summary")
                news.title = emphasized?.getString("title")

                val info = display?.getJSONObject("info")

                news.images = getImageList(newsContent)
                news.display_url = info?.getString("url")

                val user = UserInfo()
                user.userId = newsContent?.getString("user_id")
                user.name = newsContent?.getString("source")
                user.avatarUrl = newsContent?.getString("media_avatar_url")
                news.userInfo = user
                list.add(news)
            }
            res.data = list
        }
        return res
    }

    private fun getImageList(json: JSONObject?): ArrayList<String>? {
        val imgs: ArrayList<String>
        val imgListArr = json?.getJSONArray("image_list")
        if (imgListArr != null && imgListArr.length() > 0) {
            val listLen = imgListArr.length()
            imgs = ArrayList(listLen)
            for (i in 0 until listLen) {
                (imgListArr.get(i) as? JSONObject)?.getString("url").let {
                    if (it != null) {
                        imgs.add(it)
                    }
                }
            }
        } else {
            val images = json?.getJSONArray("images")
            val iSize = images?.length() ?: 0
            imgs = ArrayList(iSize)
            for (i in 0 until iSize) {
                images?.get(i)?.let { imgs.add(it.toString()) }
            }
        }
        return imgs
    }

    /**
     * 获取历史搜索关键字列表
     *
     * @return 搜索关键字列表
     */
    fun getHistorySearchKeys(): List<String> {
        if (historySearchKeys == null) {
            historySearchKeys = ArrayList(MAX_HISTORY_SEARCH_SIZE)
            val searchKey = PreferencesUtils.getStringPreferences(HISTORY_SEARCH_KEYS, "")
            if (!TextUtils.isEmpty(searchKey)) {
                val tt = object : TypeToken<java.util.ArrayList<String>>() {
                }
                historySearchKeys = jsonToList(searchKey, tt) as ArrayList<String>
            }
        }
        return historySearchKeys as ArrayList<String>
    }

    /**
     * 添加搜索关键字
     *
     * @param searchKey 搜索关键字
     */
    private fun addHistorySearchKeys(@NonNull searchKey: String) {
        if (TextUtils.isEmpty(searchKey) || TextUtils.isEmpty(searchKey.trim { it <= ' ' })) {
            return
        }
        if (historySearchKeys == null) {
            historySearchKeys = ArrayList(MAX_HISTORY_SEARCH_SIZE)
        }
        if (historySearchKeys?.contains(searchKey) == true) {
            historySearchKeys?.remove(searchKey)
        }
        if (historySearchKeys?.size ?: 0 >= MAX_HISTORY_SEARCH_SIZE) {
            historySearchKeys?.removeAt(0)
        }
        historySearchKeys?.add(searchKey)
        if (isViewAttached) {
            mvpView.notifyChangedHistorySearchKey()
        }
    }
    
    /**
     * 保存搜索关键字列表
     */
    fun saveHistorySearchKeys() {
        if (historySearchKeys?.isNullOrEmpty() == false) {
            PreferencesUtils.setStringPreferences(HISTORY_SEARCH_KEYS, JsonUtil.objetcToJson(historySearchKeys))
        }
    }

    /**
     * 移除搜索关键字列表
     */
    fun removerHistorySearchKeys() {
        if (historySearchKeys?.isNotEmpty() == true) {
            historySearchKeys?.clear()
        }
        PreferencesUtils.remove(HISTORY_SEARCH_KEYS)
        if (isViewAttached) {
            mvpView.notifyChangedHistorySearchKey()
        }
    }
}
