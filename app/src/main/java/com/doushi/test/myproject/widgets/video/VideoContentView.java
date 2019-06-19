package com.doushi.test.myproject.widgets.video;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.doushi.library.util.DateUtil;
import com.doushi.library.util.ImageLoadUtils;
import com.doushi.test.myproject.R;
import com.doushi.test.myproject.base.component.BaseFragment;
import com.doushi.test.myproject.global.DefaultValue;
import com.doushi.test.myproject.model.user.UserInfo;
import com.doushi.test.myproject.model.video.VideoDetails;
import com.doushi.test.myproject.widgets.extra.LineSpaceExtraCompatTextView;
//import com.doushi.library.util.StringUtils;
//import com.doushi.library.util.VerificationUtils;
//import com.doushi.library.widgets.linespace.LineSpaceExtraCompatTextView;
//import com.kingnet.fiveline.R;
//import com.kingnet.fiveline.base.component.BaseFragment;
//import com.kingnet.fiveline.base.listener.OnInformationClickListener;
//import com.kingnet.fiveline.dana.PhpDanaClient;
//import com.kingnet.fiveline.global.DefaultValue;
//import com.kingnet.fiveline.model.collect.HistoryBean;
//import com.kingnet.fiveline.model.information.InformationInfo;
//import com.kingnet.fiveline.model.user.UserInfo;
//import com.kingnet.fiveline.model.video.VideoDetails;
//import com.kingnet.fiveline.shared.H5UrlSharedPreferences;
//import com.kingnet.fiveline.ui.main.home.follow.FollowFragment;
//import com.kingnet.fiveline.ui.main.mv.ConsultOperateView;
//import com.kingnet.fiveline.ui.main.video.VideoHelper;
//import com.kingnet.fiveline.ui.main.video.VideoPlayerViewWrapper;
//import com.kingnet.fiveline.utils.ShareUtil;
//import com.kingnet.fiveline.widgets.animator.PraiseView;
//import com.kingnet.videoplayer.VideoCallback;
//import com.kingnet.videoplayer.VideoModel;
//import com.kingnet.videoplayer.VideoPlayerManager;
//import com.kingnet.videoplayer.listener.VideoEventListener;
//import com.kingnet.videoplayer.ui.BaseVideoPlayerView;

import org.jetbrains.annotations.NotNull;
import org.litepal.crud.callback.SaveCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author xiemy1
 * @date 2018/7/24
 */
public class VideoContentView extends RelativeLayout {

    @BindView(R.id.tvVideoDescribe)
    LineSpaceExtraCompatTextView tvVideoDescribe;
    @BindView(R.id.vflVideoContent)
    ImageView vflVideoContent;
    @BindView(R.id.tvVideoTime)
    TextView tvVideoTime;
    //    @BindView(R.id.praiseView)
//    PraiseView praiseView;
    @BindView(R.id.tvCommentOrCreateTime)
    TextView tvCommentOrCreateTime;
    @BindView(R.id.ivUserPortrait)
    ImageView ivUserPortrait;
    @BindView(R.id.flUserAvatar)
    FrameLayout flUserAvatar;

    //    private OnInformationClickListener informationClickListener;
    private int position;
    private VideoDetails item;
    private BaseFragment baseFragment;
    /**
     * 数据上报需要的pos
     */
    private String fromPos = "";
    /**
     * 视频播放需要的tag
     */
    public String tag = "";

    public VideoContentView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public VideoContentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VideoContentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View childView = LayoutInflater.from(context).inflate(R.layout.view_video_item,
                this, true);
        ButterKnife.bind(this, childView);
    }

    public void setInformationClickListener(/*@NotNull OnInformationClickListener informationClickListener,*/
            int position, @NotNull String fromPos, @NotNull String tag) {
//        this.informationClickListener = informationClickListener;
        this.position = position;
        this.fromPos = fromPos;
        this.tag = tag;
    }

    public void setItemVideoContent(VideoDetails info,/*, BaseFragment baseFragment,*/ boolean
            showCrateTime, boolean isUserClick) {
        this.item = info;
//        this.baseFragment = baseFragment;
        setNickOrCreateTimeOrCommentCount(tvCommentOrCreateTime, info, showCrateTime, isUserClick);
        UserInfo uInfo = item.getUserInfo();
        if (uInfo != null) {
            flUserAvatar.setVisibility(VISIBLE);
            new ImageLoadUtils(baseFragment).commonCircleImage(uInfo.getAvatarUrl(), ivUserPortrait, DefaultValue.HEAD);
        }
        //视频描述
        //置頂图标占位空格符
//        String emptyTop = "       ";
//        String title = item.isTopFlag() ? emptyTop + item.getTitle() : item.getTitle();
        tvVideoDescribe.setText("视频标题");

        //设置点赞
//        if (item.validReadFlag()) {
//            praiseView.setVisibility(View.INVISIBLE);
//        } else {
//            praiseView.setVisibility(View.INVISIBLE);
//        }
//        praiseView.setPraise(item.getLike(), item.getLike_count(), item.getDislike_count());
//        VideoDetails video = ObjectUtils.isNotEmpty(item.getVideos()) ? item.getVideos().get(0) : null;

        tvVideoTime.setText("4:01"/*VideoHelper.Companion.getVideoDuration(video.getDuration())*/);

        new ImageLoadUtils(baseFragment).commonCircleImage(item.getImgUrl(), ivUserPortrait, DefaultValue.HEAD);
    }

    /**
     * 设置底部昵称|创建时间|评论数
     *
     * @param tvCommentOrCreateTime 底部控件
     * @param info                  数据
     */
    private void setNickOrCreateTimeOrCommentCount(TextView tvCommentOrCreateTime, VideoDetails info,
                                                   boolean showCreateTime, boolean isUserClick) {
        if (tvCommentOrCreateTime == null || info == null) {
            return;
        }
        String commentOrCreateTime = "100评论  13:23";
//        String commentCount = StringUtils.getString(info.getComment_count());
//        String createTime = DateUtil.getStringTime(info.getCreate_time());
//        String nick = info.getUinfo() != null ? info.getUinfo().getNickname() : "";
//        //3小时以内,显示时间
//        if (showCreateTime) {
//            //显示用户头像,显示评论数+时间
//            if (/*baseFragment instanceof FollowFragment ||*/ !isUserClick) {
//                commentOrCreateTime = getContext().getString(R.string.comment_create_time, commentCount, createTime);
//            } else {
//                commentOrCreateTime = getContext().getString(R.string.nick_comment_create_time, nick, commentCount, createTime);
//            }
//        } else {
//            //显示用户头像,显示评论数+时间
//            if (/*baseFragment instanceof FollowFragment || */!isUserClick) {
//                commentOrCreateTime = getContext().getString(R.string.number_comment, commentCount);
//            } else {
//                commentOrCreateTime = getContext().getString(R.string.nick_comment, nick, commentCount);
//            }
//        }
        tvCommentOrCreateTime.setText(commentOrCreateTime);
    }

    @OnClick({R.id.praiseView, R.id.tvCommentOrCreateTime, R.id.flUserAvatar, R.id.ivContentMore})
    public void onViewClicked(View view) {
//        if (item == null || VerificationUtils.isFastDoubleClick(view.getId())) {
//            return;
//        }
//        switch (view.getId()) {
//            case R.id.praiseView:
//                if (informationClickListener != null) {
//                    informationClickListener.onPraiseViewClick(ConsultOperateView.OPERATE_PRAISE, item.getItem_id());
//                }
//                break;
//
//            case R.id.tvCommentOrCreateTime:
//            case R.id.flUserAvatar:
//                if (informationClickListener != null) {
//                    informationClickListener.onUserAvatarClick(item);
//                }
//                break;
//
//            case R.id.ivContentMore:
//                if (informationClickListener != null) {
//                    informationClickListener.onMoreClick(item, getImageUrl(item), position);
//                }
//                break;
//            default:
//                break;
//        }
    }

//    /**
//     * 视频播放器时间回调监听
//     */
//    private VideoEventListener videoEventListener = new VideoCallback() {
//
//        @Override
//        public void onPlayOtherVideo() {
//            super.onPlayOtherVideo();
//            tvVideoTime.setVisibility(VISIBLE);
//        }
//
//        @Override
//        public void onClickStart() {
//            super.onClickStart();
//            if (videoEventListener != null) {
//                informationClickListener.setVideoPlayerViewWrapper(vflVideoContent);
//            }
//            VideoPlayerManager.getInstance().setPlayPosition(position);
//            VideoPlayerManager.getInstance().setPlayTag(tag);
//            PhpDanaClient.postEventVideoPlay(getContext(), PhpDanaClient.START, item.getItem_id(), fromPos);
//            tvVideoTime.setVisibility(GONE);
//        }
//
//        @Override
//        public void onClickStop() {
//            super.onClickStop();
//            PhpDanaClient.postEventVideoPlay(getContext(), PhpDanaClient.PAUSE, item.getItem_id(), fromPos);
//        }
//
//        @Override
//        public void onFirstPlay() {
//            super.onFirstPlay();
//            saveHistory(item);
//        }
//
//        @Override
//        public void onPlaying(String vid, String url) {
//            super.onPlaying(vid, url);
//            tvVideoTime.setVisibility(GONE);
//            praiseView.setVisibility(GONE);
//        }
//
//        @Override
//        public void onclickReplay() {
//            super.onclickReplay();
//            PhpDanaClient.postEventVideoPlay(getContext(), PhpDanaClient.REPLAY, item.getItem_id(), fromPos);
//        }
//
//        @Override
//        public void onClickShare(int platform) {
//            super.onClickShare(platform);
//            shareInformation(item, platform);
//        }
//
//        @Override
//        public void onDragProgress() {
//            super.onDragProgress();
//            PhpDanaClient.postEventVideoPlay(getContext(), PhpDanaClient.DRAG, item.getItem_id(), fromPos);
//        }
//
//        @Override
//        public void onCompleteVideo() {
//            super.onCompleteVideo();
//            PhpDanaClient.postEventVideoPlay(getContext(), PhpDanaClient.COMPLETE, item.getItem_id(), fromPos);
//        }
//
//        @Override
//        public void onValidRead() {
//            super.onValidRead();
//            item.setValidReadFlag(true);
//            praiseView.setVisibility(GONE);
//            PhpDanaClient.postEventValidRead(getContext(), item.getItem_id(), "A");
//        }
//    };

//    /**
//     * 保存播放历史
//     *
//     * @param item 播放视频
//     */
//    private void saveHistory(InformationInfo item) {
//        new HistoryBean(item.getItem_id(),
//                "video",
//                item.getTitle(),
//                item.getUinfo() != null ? item.getUinfo().getNickname() : "",
//                String.valueOf(item.getComment_count()),
//                getImageUrl(item),
//                item.getVideos() != null && item.getVideos().get(0) != null ?
//                        String.valueOf(item.getVideos().get(0).getDuration()) : "0",
//                System.currentTimeMillis())
//                .saveOrUpdateAsync("item_id = ?", item.getItem_id())
//                .listen(new SaveCallback() {
//                    @Override
//                    public void onFinish(boolean success) {
//
//                    }
//                });
//    }

    /**
     * 分享视频
     *
     * @param item     视频数据
     * @param platform 分享平台
     */
//    private void shareInformation(final InformationInfo item, int platform) {
//        String shareUrl = ShareUtil.createShareUrl(item.getItem_id(), H5UrlSharedPreferences.Share_Video);
//        ShareUtil shareUtil = new ShareUtil(getContext(), item.getItem_id(),
//                item.getTitle(),
//                item.getContent(),
//                getImageUrl(item),
//                shareUrl, new ShareUtil.OnShareListener() {
//
//            @Override
//            public void shareComplete(int result) {
//
//            }
//        });
//
//        switch (platform) {
//            case VideoPlayerViewWrapper.WECHAT:
//                shareUtil.showShare(Wechat.NAME);
//                break;
//
//            case VideoPlayerViewWrapper.WECHAT_MOMENTS:
//                shareUtil.showShare(WechatMoments.NAME);
//                break;
//
//            case VideoPlayerViewWrapper.QQ:
//                shareUtil.showShare(QQ.NAME);
//                break;
//
//            case VideoPlayerViewWrapper.SINA_WEIBO:
//                shareUtil.showShare(SinaWeibo.NAME);
//                break;
//
//            default:
//                break;
//        }
//    }

    /**
     * 分享，本地保存用缩略图
     *
     * @param item 资讯|视频数据详情
     * @return 本地保存用缩略图
     */
//    private String getImageUrl(InformationInfo item) {
//        return item.getThumbnails() != null && item.getThumbnails().size() > 0 && item.getThumbnails().get(0) != null ? item.getThumbnails().get(0).get360Url() : "";
//    }
}
