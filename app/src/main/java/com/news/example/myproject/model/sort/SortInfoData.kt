package com.news.example.myproject.model.sort

/**
 * @author xiemy2
 * @date 2019/7/1
 */
data class SortInfoData(
        var fixedList: MutableList<NewsSortInfo> = ArrayList(),
        var chooseList: MutableList<NewsSortInfo> = ArrayList(),
        var editList: MutableList<NewsSortInfo> = ArrayList())