package com.news.example.myproject.model.news

import com.news.example.myproject.model.user.UserInfo

/**
 * @author xiemy2
 * @date 2019/7/4
 */
data class NewsDetails(var detail_source: String?,
                       val media_user: UserInfo?,
                       val publish_time: Long?,
                       val title: String,
                       val url: String,
                       val impression_count: String,
                       val source: String,
                       val content: String,
                       val comment_count: String,
                       val logo_show_strategy: String,
                       val creator_uid: String
)

/*
{
  "_ck": {

  },
  "data": {
    "detail_source": "\u4eba\u6c11\u7f51",
    "media_user": {
      "screen_name": "\u4eba\u6c11\u7f51",
      "no_display_pgc_icon": false,
      "avatar_url": "http:\/\/p9.pstatp.com\/thumb\/ca400072481685ad43b",
      "id": "50502346173",
      "user_auth_info": {
        "auth_type": "0",
        "auth_info": "\u4eba\u6c11\u7f51\u5b98\u65b9\u8d26\u53f7"
      }
    },
    "publish_time": 1562212621,
    "title": "\u4e00\u4e2a\u6708\u5185\u591a\u6b21\u51fa\u8bbf \u4e60\u8fd1\u5e73\u5f15\u7528\u4e86\u8fd9\u4e9b\u8bd7\u8bcd\u5178\u6545",
    "url": "http:\/\/toutiao.com\/group\/6709651998402150920\/",
    "high_quality_flag": "0",
    "impression_count": "643534",
    "is_original": true,
    "is_pgc_article": true,
    "content": "",
	"source": "\u4eba\u6c11\u7f51",
    "comment_count": 9,
    "logo_show_strategy": "normal",
    "creator_uid": 50502346173
  },
  "success": true
}

 */