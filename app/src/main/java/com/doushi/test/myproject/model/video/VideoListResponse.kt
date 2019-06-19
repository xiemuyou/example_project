package com.doushi.test.myproject.model.video

/**
 * @author xiemy2
 * @date 2019/6/19
 */
data class VideoListResponse(
        var code: Int? = 0,
        var message: String? = "",
        var videoList: ArrayList<VideoDetails>? = null)