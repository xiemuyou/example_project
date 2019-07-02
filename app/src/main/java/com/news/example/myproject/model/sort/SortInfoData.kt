package com.news.example.myproject.model.sort

/**
 * @author xiemy2
 * @date 2019/7/1
 */
data class SortInfoData(
        var fixedList: MutableList<NewsSortInfo> = ArrayList(),
        var chooseList: MutableList<NewsSortInfo> = ArrayList(),
        var editList: MutableList<NewsSortInfo> = ArrayList()) {

    /**
     * 获取全部List
     */
    fun clearAllList() {
        fixedList.clear()
        chooseList.clear()
        editList.clear()
    }

    /**
     * 获取全部List
     */
    fun getAllList(): MutableList<NewsSortInfo> {
        val allList = ArrayList<NewsSortInfo>()
        allList.addAll(fixedList)
        allList.addAll(chooseList)
        allList.addAll(editList)
        return allList
    }
}