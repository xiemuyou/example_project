package com.news.example.myproject.ui.search.presenter

import android.text.TextUtils
import com.google.gson.reflect.TypeToken
import com.library.util.JsonUtil
import com.library.util.JsonUtil.jsonToList
import com.library.util.PreferencesUtils
import com.library.util.VerificationUtils
import com.news.example.myproject.base.mvp.BasePresenter
import com.news.example.myproject.model.base.BaseApiResponse
import com.news.example.myproject.ui.search.view.SearchView
import com.news.example.myproject.znet.InterfaceConfig
import com.news.example.myproject.znet.rx.RxRequestCallback
import io.reactivex.annotations.NonNull
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

    override fun onLoadDataSuccess(apiTag: InterfaceConfig.HttpHelperTag?, res: String?, params: MutableMap<String, Any>?) {
        super.onLoadDataSuccess(apiTag, res, params)
        mvpView.getSearchSuccess(res)
    }

    override fun onLoadDataSuccess(apiTag: InterfaceConfig.HttpHelperTag, modelRes: BaseApiResponse<*>?, params: Map<String, Any>?) {
        //nothing
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
