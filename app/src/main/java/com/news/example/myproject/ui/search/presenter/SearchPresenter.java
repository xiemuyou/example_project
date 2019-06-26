package com.news.example.myproject.ui.search.presenter;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.library.util.JsonUtil;
import com.library.util.PreferencesUtils;
import com.news.example.myproject.base.mvp.BasePresenter;
import com.news.example.myproject.model.base.BaseApiResponse;
import com.news.example.myproject.ui.search.view.SearchView;
import com.news.example.myproject.znet.InterfaceConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 搜索类实现
 *
 * @author xiemy
 * @date 2017/5/19
 */
public class SearchPresenter extends BasePresenter<SearchView> {

    public SearchPresenter(SearchView searchView) {
        attachView(searchView);
    }

    @Override
    public void onLoadDataSuccess(InterfaceConfig.HttpHelperTag apiTag, BaseApiResponse modelRes, Map<String, Object> params) {
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
     * 搜索
     *
     * @param inputStr 用户输入要字符串
     */
    public boolean search(String inputStr) {
        boolean result = getMvpView() != null;
        if (result) {
            Map<String, Object> map = new HashMap<>();
            map.put("key", inputStr);
//            ZZAllService.sharedInstance().serviceQueryByObj(map, serviceHelperDelegate, InterfaceConfig.HttpHelperTag.HTTPHelperTag_Search);
        }
        return result;
    }

    /**
     * 搜索更多视频列表
     *
     * @param inputStr 用户输入要字符串
     */
    public void getSearchedVideos(int page, int cnt, String inputStr) {
        Map<String, Object> map = new HashMap<>();
        map.put("key", inputStr);
        int fromCount = page * cnt;
//        map.put(Constants.FROM, fromCount);
//        map.put(Constants.CNT, cnt);
//        ZZAllService.sharedInstance().serviceQueryByObj(map, serviceHelperDelegate, InterfaceConfig.HttpHelperTag.HTTPHelperTag_GetSearchedVideos);
    }

    private final String SEARCH_HISTORY_LIST = "SearchHistory";
    private List<String> searchHistoryList;

    public List<String> getSearchHistoryList() {
        if (searchHistoryList == null || searchHistoryList.size() == 0) {
            String searchHistory = PreferencesUtils.getStringPreferences(SEARCH_HISTORY_LIST, "");
            if (!TextUtils.isEmpty(searchHistory)) {
                searchHistoryList = (List<String>) JsonUtil.jsonToList(searchHistory, new TypeToken<ArrayList<String>>() {
                });
                Collections.reverse(searchHistoryList);
            }
        }
        return searchHistoryList;
    }

    public void saveSearchHistory(String searchStr) {
        if (!TextUtils.isEmpty(searchStr)) {
            String searchHistory = PreferencesUtils.getStringPreferences(SEARCH_HISTORY_LIST, "");
            if (!TextUtils.isEmpty(searchHistory)) {
                searchHistoryList = (List<String>) JsonUtil.jsonToList(searchHistory, new TypeToken<ArrayList<String>>() {
                });
                if (getSearchHistoryList() != null && searchHistoryList.size() > 0 && searchHistoryList.contains(searchStr)) {
                    searchHistoryList.remove(searchStr);
                }
            }
            if (searchHistoryList == null) {
                searchHistoryList = new ArrayList<>();
            }
            searchHistoryList.add(searchStr);
            PreferencesUtils.setStringPreferences(SEARCH_HISTORY_LIST, JsonUtil.objetcToJson(searchHistoryList));
        }
    }

    public void removeSearchHistory() {
        PreferencesUtils.remove(SEARCH_HISTORY_LIST);
    }
}
