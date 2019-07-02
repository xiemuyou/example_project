package com.news.example.myproject.ui.main.home.sort.sp

import com.library.thread.AbstractSafeThread
import com.library.thread.ThreadPool
import com.library.util.JsonUtil
import com.library.util.PreferencesUtils
import com.news.example.myproject.model.sort.NewsSortInfo
import com.news.example.myproject.model.sort.SortInfoData

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
    fun compareList(sList: MutableList<NewsSortInfo>?): SortInfoData? {
        val newData = if (sList != null) getNewSortData(sList) else null
        val saveData = getSaveSortData()
        if (saveData?.getAllList().isNullOrEmpty()) {
            return newData
        }
        return saveData
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

    private fun getNewSortData(sortInfoList: MutableList<NewsSortInfo>): SortInfoData? {
        val newData = SortInfoData()
        newData.fixedList = getNewsFixedList()
        val sortSize = sortInfoList.size
        //循环添加分类页
        for (i in 0 until sortSize) {
            val it = sortInfoList[i]
            if (i < 10) {
                it.itemType = NewsSortInfo.CHOOSE
                newData.chooseList.add(sortInfoList[i])
            } else {
                it.itemType = NewsSortInfo.MORE
                newData.editList.add(sortInfoList[i])
            }
        }
        return newData
    }

    //手动添加推荐页
    var recommend = ""

    /**
     * 添加固定的分类
     */
    private fun getNewsFixedList(): MutableList<NewsSortInfo> {
        val fixedList: MutableList<NewsSortInfo> = ArrayList(1)
        val recommendSort = NewsSortInfo()
        recommendSort.name = recommend
        recommendSort.itemType = NewsSortInfo.FIXED
        fixedList.add(0, recommendSort)
        return fixedList
    }

    private fun getSaveSortData(): SortInfoData? {
        var sortData: SortInfoData?
        try {
            val json = PreferencesUtils.getStringPreferences(sortListKey, "")
            sortData = JsonUtil.jsonToObject(json, SortInfoData::class.java) as SortInfoData
        } catch (e: Exception) {
            sortData = null
        }
        return sortData
    }

    fun saveSortData(sortData: SortInfoData?) {
        if (sortData == null) {
            return
        }
        ThreadPool.execute(object : AbstractSafeThread() {
            override fun deal() {
                val json = JsonUtil.objetcToJson(sortData)
                PreferencesUtils.setStringPreferences(sortListKey, json)
            }
        })
    }
}

private const val sortListKey = "newsSortInfoList"
