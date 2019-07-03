package com.news.example.myproject.model.sort

/**
 * Created by tanghao on 2018/9/8.
 */
object SortFilter {

    fun filterCloseList(sysList: List<NewsSortInfo>): List<NewsSortInfo> {
        return sysList/*.filter { it.state == NewsSortInfo.STATE_CLOSE_SORT }*/
    }

    fun filterOpenList(sysList: List<NewsSortInfo>): List<NewsSortInfo> {
        return sysList/*.filter { it.labelType == NewsSortInfo.STATE_OPEN_SORT }*/
    }

    fun divideList(sortList: List<NewsSortInfo>, sortData: SortInfoData): SortInfoData {
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