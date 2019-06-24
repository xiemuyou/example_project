package com.doushi.test.myproject.ui.main.video.vp

import android.text.TextUtils
import com.doushi.test.myproject.base.mvp.BasePresenter
import com.doushi.test.myproject.global.ParamConstants
import com.doushi.test.myproject.model.base.BaseApiResponse
import com.doushi.test.myproject.model.user.UserInfo
import com.doushi.test.myproject.model.video.VideoDetails
import com.doushi.test.myproject.model.video.VideoListResponse
import com.doushi.test.myproject.ui.main.video.vv.VideoView
import com.doushi.test.myproject.znet.InterfaceConfig
import com.doushi.test.myproject.znet.rx.RxRequestCallback
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList

import java.util.HashMap

/**
 * @author xiemy
 * @date 2018/2/28.
 */
class VideoPresenter(view: VideoView) : BasePresenter<VideoView>(view) {

    fun getTodayVideoList(searchKey: String) {
        val params = HashMap<String, Any>()
        params[ParamConstants.CATEGORY] = searchKey
        RxRequestCallback().request(params = null, api = InterfaceConfig.HttpHelperTag.TODAY_VIDEO, presenter = this)
    }

    override fun onLoadDataSuccess(apiTag: InterfaceConfig.HttpHelperTag, modelRes: BaseApiResponse<*>, params: Map<String, Any>) {
    }

    override fun onLoadDataSuccess(apiTag: InterfaceConfig.HttpHelperTag?, res: String?, params: MutableMap<String, Any>?) {
        if (!TextUtils.isEmpty(res)) {
            val listResponse = parseVideoList(res)
            mvpView.getVideoListSuccess(listResponse?.videoList)
        }
    }

    private fun parseVideoList(json: String?): VideoListResponse? {
        if (json == null) {
            return null
        }
        val obj = JSONObject(json)
        val res = VideoListResponse()
        //第一级{code,message,result[]}
        res.code = obj.getInt("code")
        res.message = obj.getString("message")

        //result[]
        val array = obj.getJSONArray("result")
        val list = ArrayList<VideoDetails>(array.length())
        for (i in 0 until array.length()) {
            var isValid = false
            val video = VideoDetails()
            try {
                //result[i]
                val io: JSONObject = array[i] as JSONObject

                //result[i]{data}
                val iData = getObj(io, "data")

                //data{content{data}}
                val content = getObj(iData, "content")
                val cd = getObj(content, "data")

                //cd{playUrl,description,createTime,owner{}}
                video.mp4Url = getString(cd, "playUrl")
                video.vid = getInt(cd, "id")

                val tags = getJSONArray(cd, "tags")
                if (tags[0] != null) {
                    val it: JSONObject = tags[0] as JSONObject
                    video.imgUrl = getString(it, "bgPicture")
                }

                if (TextUtils.isEmpty(video.mp4Url) || TextUtils.isEmpty(video.imgUrl)) {
                    continue
                }
                video.description = getString(cd, "description")
                video.createTime = getLong(cd, "createTime")

                val user = UserInfo()
                //owner{uid,nickname,avatar,????cover}
                val owner = getObj(cd, "owner")
                user.userId = getString(owner, "uid")
                user.name = getString(owner, "nickname")
                user.avatarUrl = getString(owner, "avatar")
                video.userInfo = user
                isValid = !list.contains(video)
            } catch (e: JSONException) {
                continue
            } finally {
                if (isValid) {
                    list.add(video)
                }
            }
        }
        res.videoList = list
        return res
    }

    private fun getJSONArray(obj: JSONObject?, key: String): JSONArray {
        try {
            return obj?.getJSONArray(key) ?: JSONArray()
        } catch (e: JSONException) {
        }
        return JSONArray()
    }

    private fun getObj(obj: JSONObject?, key: String): JSONObject {
        try {
            return obj?.getJSONObject(key) ?: JSONObject()
        } catch (e: JSONException) {
        }
        return JSONObject()
    }

    private fun getString(obj: JSONObject?, key: String): String {
        try {
            return obj?.getString(key) ?: ""
        } catch (e: JSONException) {
        }
        return ""
    }

    private fun getInt(obj: JSONObject?, key: String): Int {
        try {
            return obj?.getInt(key) ?: 0
        } catch (e: JSONException) {
        }
        return 0
    }

    private fun getLong(obj: JSONObject?, key: String): Long {
        try {
            return obj?.getLong(key) ?: 0L
        } catch (e: JSONException) {
        }
        return 0L
    }
}
