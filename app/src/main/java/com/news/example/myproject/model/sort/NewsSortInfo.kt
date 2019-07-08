package com.news.example.myproject.model.sort

/**
 * @author xiemy2
 * @date 2019/5/27
 */
data class NewsSortInfo(
        var itemType: Int? = 0,
        var category: String? = "",
        var web_url: String? = "",
        var flags: String? = "",
        var name: String? = "",
        var tip_new: String? = "",
        var default_add: String? = "",
        var concern_id: String? = "",
        var icon_url: String? = "") /*: BaseMultiItemEntity*/ {

    companion object {
        //固定
        const val FIXED = 0
        //选择
        const val CHOOSE = 1
        //标题
        const val TITLE = 2
        //更多
        const val MORE = 3
    }

    /*override */fun setItemType(itemType: Int) {
        this.itemType = itemType
    }

    override fun equals(other: Any?): Boolean {
        return other is NewsSortInfo && other.concern_id == concern_id
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    fun getType(): Int {
        return itemType ?: 0
    }
}