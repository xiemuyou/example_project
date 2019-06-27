package com.news.example.myproject.ui.search.presenter

import android.text.TextUtils
import com.library.util.LogUtil
import com.library.util.PreferencesUtils
import com.news.example.myproject.base.mvp.BasePresenter
import com.news.example.myproject.model.base.BaseApiResponse
import com.news.example.myproject.ui.search.view.SearchView
import com.news.example.myproject.ui.web.CommonWebActivity
import com.news.example.myproject.znet.InterfaceConfig
import com.news.example.myproject.znet.rx.RxRequestCallback
import java.util.*


/**
 * 搜索类实现
 *
 * @author xiemy
 * @date 2017/5/19
 */
class SearchPresenter(searchView: SearchView) : BasePresenter<SearchView>() {

    private val SEARCH_HISTORY_LIST = "SearchHistory"
    private var searchHistoryList: MutableList<String>? = null

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
        val result = !TextUtils.isEmpty(keyword) && mvpView != null
        if (result) {
            val map = HashMap<String, Any>()
            map["keyword"] = keyword!!
            RxRequestCallback().request(params = map, api = InterfaceConfig.HttpHelperTag.SEARCH_TOU_TIAO, presenter = this)
        }
        return result
    }

    override fun onLoadDataSuccess(apiTag: InterfaceConfig.HttpHelperTag?, res: String?, params: MutableMap<String, Any>?) {
        super.onLoadDataSuccess(apiTag, res, params)
        val key = params?.get("keyword")?.toString()
        LogUtil.d(TAG, "SearchResponse = $res searchKey = $key")
        CommonWebActivity.showClass(webUrl = "", content = res)
    }

    override fun onLoadDataSuccess(apiTag: InterfaceConfig.HttpHelperTag, modelRes: BaseApiResponse<*>?, params: Map<String, Any>?) {
        when (apiTag) {
            InterfaceConfig.HttpHelperTag.SEARCH_TOU_TIAO -> {
                //val res =

//                val success = parseJson(res)
//                mvpView.getCategoryExtraSuccess(success)
            }

            else -> {
            }
        }


        //        switch (tag) {
        //            case HTTPHelperTag_Search:
        //                SearchResponse searchRes = (SearchResponse) JsonUtil.jsonToObject(responseObj, SearchResponse.class);
        //                if (searchRes.getErrcode() == 0) {
        //                    if (isViewAttached())
        //                        getMvpView().searchDataList(searchRes.getData());
        //                } else {
        //                    loadDataFail(searchRes.getErrmsg());
        //                }
        //                break;
        //
        //            case HTTPHelperTag_GetSearchedVideos:
        //                VideoListResponse videosRes = (VideoListResponse) JsonUtil.jsonToObject(responseObj, VideoListResponse.class);
        //                if (videosRes.getErrcode() == 0) {
        //                    if (isViewAttached())
        //                        getMvpView().getSearchedVideoSuccess(videosRes.getData());
        //                } else {
        //                    loadDataFail(videosRes.getErrmsg());
        //                }
        //                break;
        //        }
    }

    /**
     * 搜索更多视频列表
     *
     * @param inputStr 用户输入要字符串
     */
//    fun getSearchedVideos(page: Int, cnt: Int, inputStr: String) {
//        val map = HashMap<String, Any>()
//        map["key"] = inputStr
//        val fromCount = page * cnt
//        map.put(Constants.FROM, fromCount);
//        map.put(Constants.CNT, cnt);
//        ZZAllService.sharedInstance().serviceQueryByObj(map, serviceHelperDelegate, InterfaceConfig.HttpHelperTag.HTTPHelperTag_GetSearchedVideos);
//    }

    fun getSearchHistoryList(): List<String>? {
        if (searchHistoryList == null || searchHistoryList!!.size == 0) {
//            val searchHistory = PreferencesUtils.getStringPreferences(SEARCH_HISTORY_LIST, "")
//            if (!TextUtils.isEmpty(searchHistory)) {
//                searchHistoryList = JsonUtil.jsonToList(searchHistory, object : TypeToken<ArrayList<String>>() {
//
//                }) as List<String>
//                Collections.reverse(searchHistoryList)
//            }
        }
        return searchHistoryList
    }

    fun saveSearchHistory(searchStr: String) {
        if (!TextUtils.isEmpty(searchStr)) {
//            val searchHistory = PreferencesUtils.getStringPreferences(SEARCH_HISTORY_LIST, "")
//            if (!TextUtils.isEmpty(searchHistory)) {
//                searchHistoryList = JsonUtil.jsonToList(searchHistory, object : TypeToken<ArrayList<String>>() {
//
//                }) as List<String>
//                if (getSearchHistoryList() != null && searchHistoryList!!.size > 0 && searchHistoryList!!.contains(searchStr)) {
//                    searchHistoryList!!.remove(searchStr)
//                }
//            }
//            if (searchHistoryList == null) {
//                searchHistoryList = ArrayList()
//            }
//            searchHistoryList!!.add(searchStr)
//            PreferencesUtils.setStringPreferences(SEARCH_HISTORY_LIST, JsonUtil.objetcToJson(searchHistoryList))
        }
    }

    fun removeSearchHistory() {
        PreferencesUtils.remove(SEARCH_HISTORY_LIST)
    }
}
