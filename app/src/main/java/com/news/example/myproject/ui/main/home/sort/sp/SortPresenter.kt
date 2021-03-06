package com.news.example.myproject.ui.main.home.sort.sp

import com.library.thread.AbstractSafeThread
import com.library.thread.ThreadPool
import com.library.util.JsonUtil
import com.library.util.PreferencesUtils
import com.news.example.myproject.model.sort.NewsSortInfo
import com.news.example.myproject.model.sort.SortFilter
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
    fun compareList(sortList: MutableList<NewsSortInfo>?): SortInfoData? {
        var newData = if (sortList != null) getNewSortData(sortList) else null
        var saveData = getSaveSortData()
//        if (saveData?.getAllList().isNullOrEmpty()) {
//            return newData
//        }
//        return saveData

        var saveList = saveData?.getAllList()
        val sList = newData?.getAllList()
        if (saveList == null || sList == null) {
            return saveData ?: newData
        }
        //1:本地保存,服务器已经删除,删除本地
        saveList = saveList.filter {
            sList.contains(it)
        } as MutableList<NewsSortInfo>?

        sList.forEach { info ->
            saveList?.forEach {
                if (it == info) {
                    it.name = info.name
                }
            }
            if (saveList?.contains(info) == false) {
                saveList.add(info)
            }
        }
        saveData = SortFilter.divideList(saveList)
        saveSortData(saveData)
        return saveData
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
    var TAG = "SortPresenter"

    /**
     * 添加固定的分类
     */
    private fun getNewsFixedList(): MutableList<NewsSortInfo> {
        val fixedList: MutableList<NewsSortInfo> = ArrayList(1)
        val recommendSort = NewsSortInfo()
        recommendSort.concern_id = "001"
        recommendSort.name = recommend
        recommendSort.itemType = NewsSortInfo.FIXED
        fixedList.add(0, recommendSort)

        val newSort = NewsSortInfo()
        newSort.concern_id = "002"
        newSort.name = "关注"
        newSort.itemType = NewsSortInfo.FIXED
        fixedList.add(1, newSort)
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
