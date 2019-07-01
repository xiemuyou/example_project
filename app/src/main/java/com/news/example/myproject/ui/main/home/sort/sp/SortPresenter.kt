package com.news.example.myproject.ui.main.home.sort.sp

import com.google.gson.reflect.TypeToken
import com.library.util.JsonUtil
import com.news.example.myproject.model.sort.NewsSortInfo

/**
 * @author xiemy
 * @date 2018/2/28.
 */
class SortPresenter {

    /**
     * 对比列表,
     * 1.本地保存,服务器已经删除,删除本地
     * 2.名称有变化,修改本地名称
     * 3.服务器新增添加到本地
     */
    fun compareList(sList: MutableList<NewsSortInfo>?): MutableList<NewsSortInfo>? {

        return sList

//        var saveList = getSaveList()
//        if (saveList == null || sList == null) {
//            return sList ?: saveList
//        }
//        //1:本地保存,服务器已经删除,删除本地
//        saveList = saveList.filter {
//            sList.contains(it)
//        } as MutableList<NewsSortInfo>?
//        sList.forEach {
//            val pos = saveList?.indexOf(it) ?: 0
//            //2:名称有变化,修改本地名称
//            saveList?.elementAtOrNull(pos)?.name = it.name
//            //3:服务器新增添加到本地
//            if (saveList?.contains(it) == false) {
//                saveList.add(it)
//            }
//        }
//        PreferencesUtils.setStringPreferences(sortListKey, JsonUtil.objetcToJson(saveList))
//        return saveList
    }


    private fun getSaveList(): List<NewsSortInfo>? {
        val tt = object : TypeToken<ArrayList<NewsSortInfo>>() {
        }
        return JsonUtil.jsonToList(sortListKey, tt) as? ArrayList<NewsSortInfo>
    }
}

private const val sortListKey = "newsSortInfoList"
