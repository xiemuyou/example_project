package com.news.example.myproject.ui.search.view;


import com.news.example.myproject.base.mvp.BaseView;
import com.news.example.myproject.model.base.BaseApiResponse;
import com.news.example.myproject.model.video.VideoDetails;

import java.util.List;

/**
 * 搜索
 * Created by xiemy on 2017/5/19.
 */
public interface SearchView extends BaseView {

    /**
     * 成功获取搜索数据
     */
    void searchDataList(/*SearchResponse*/ BaseApiResponse searchRes);

    /**
     * 成功获取更多视频列表
     */
    void getSearchedVideoSuccess(List<VideoDetails> searchVideoList);
}
