package com.doushi.test.myproject.ui.main.video.vv;

import com.doushi.test.myproject.base.mvp.BaseView;
import com.doushi.test.myproject.model.news.RecommendResponse;
import com.doushi.test.myproject.model.video.VideoDetails;

import java.util.List;

/**
 * 刷新页UI更新
 *
 * @author xiemy
 * @date 2017/7/26.
 */
public interface VideoView extends BaseView {

    /**
     * 成功获取数据,允许数据为NULL
     *
     * @param videoList 视频列表
     */
    void getVideoListSuccess(List<VideoDetails> videoList);
}