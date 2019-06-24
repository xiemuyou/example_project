package com.news.example.myproject.model.news

import com.news.example.myproject.model.user.UserInfo

/**
 * @author xiemy2
 * @date 2019/5/27
 */
data class NewsInfo(
        var content: String? = "",
        var title: String? = "",
        var stick_label: String? = "",
        var publish_time: Long? = 0L,
        var display_url: String? = "",
        var userInfo: UserInfo? = UserInfo(),
        var images: List<String>? = ArrayList()
)

/*

{abstract:每当在分娩的同时，体内的胎盘就会一同跟着被排出体外，而在没有任何功能之后，胎盘就会变成一坨肉。1.胎盘是胎宝宝的附属物之一，一般是由羊膜、丛密绒毛膜以及底蜕膜三种构成的。,
action_list:[
    {action:1,desc:,extra:{}},
    {action:3,desc:,extra:{}},
    {action:7,desc:,extra:{}},
    {action:9,desc:,extra:{}}
    ],
aggr_type:1,
allow_download:false,
article_sub_type:0,
article_type:0
,article_url:http://toutiao.com/group/6695345997414924814/,
article_version:0,
ban_comment:0,
behot_time:1558939736,
bury_count:0,
cell_flag:262155,
cell_layout_style:1,
cell_type:0,
comment_count:1,
content_decoration:,
cursor:1558939736000,
digg_count:4,
display_url:http://toutiao.com/group/6695345997414924814/,
filter_words:[
    {id:8:0,is_selected:false,name:看过了},
    {id:9:1,is_selected:false,name:内容太水},
    {id:5:2478739554,is_selected:false,name:拉黑作者:文竹妈妈},
    {id:3:290561241,is_selected:false,name:不想看:怀孕},
    {id:6:256020742,is_selected:false,name:不想看:不完美妈妈}
    ],forward_info:{forward_count:0},
gallary_image_count:6,
group_id:6695345997414924814,
group_source:2,
has_image:true,
has_m3u8_video:false,
has_mp4_video:0,
has_video:false,
hot:0,ignore_we...4-11e9-8fe7-98039ba37db0},
{url:http://p1-tt.byteimg.com/list/640x360/tos-cn-i-0000/73b393d4-7fc4-11e9-8fe7-98039ba37db0}],width:641}},
share_url:http://m.toutiao.com/a6695345997414924814/?app=news_article\u0026is_hit_share_recommend=0,
show_dislike:true,
show_portrait:false,
show_portrait_article:false,
source:文竹妈妈,
source_icon_style:1,
source_open_url:sslocal://profile?uid=50675717547,
tag:news_baby,
tag_id:6695345997414924814,
tip:0,
title:生完孩子后，“胎盘”去哪了？这些用处或许你没想到,
ugc_recommend:{activity:,reason:优质育儿领域创作者},
url:http://toutiao.com/group/6695345997414924814/,
user_info:{
    avatar_url:http://p9.pstatp.com/thumb/10005000007a5ca08d001,
    description:两个宝宝的妈妈，专注育儿知识分享,
    follow:false,follower_count:0,
    live_info_type:1,
    name:文竹妈妈,
    schema:sslocal://profile?uid=50675717547\u0026refer=all,
    user_auth_info:{\auth_type\:\0\,\auth_info\:\优质育儿领域创作者\,\other_auth\:{\interest\:\优质育儿领域创作者\}
    },user_id:50675717547,user_verified:true,verified_content:优质育儿领域创作者},user_repin:0,user_verified:1,verified_content:优质育儿领域创作者,video_style:0}
 */