package com.news.example.myproject.model.sort

/**
 * Created by tanghao on 2018/9/8.
 */
object SortFilter {

    fun divideList(sortList: List<NewsSortInfo>?): SortInfoData {
        val sortData = SortInfoData()
        if (sortList?.isNullOrEmpty() == true) {
            return sortData
        }
        for (sortInfo in sortList) {
            when (sortInfo.itemType) {
                NewsSortInfo.FIXED -> sortData.fixedList.add(sortInfo)

                NewsSortInfo.CHOOSE -> sortData.chooseList.add(sortInfo)

                NewsSortInfo.MORE -> sortData.editList.add(sortInfo)

                else -> {
                }
            }
        }
        return sortData
    }

    fun <T> dataToHeavy(oldDataList: MutableList<T>, newDataList: MutableList<T>): List<T> {
        return newDataList.filter {
            !oldDataList.contains(it)
        }
    }
}