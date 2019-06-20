package com.doushi.test.myproject.widgets.video;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.doushi.library.util.ImageLoadUtils;
import com.doushi.test.myproject.R;
import com.doushi.test.myproject.base.component.BaseFragment;
import com.doushi.test.myproject.global.DefaultValue;
import com.doushi.test.myproject.model.user.UserInfo;
import com.doushi.test.myproject.model.video.VideoDetails;
import com.doushi.test.myproject.ui.main.video.widget.SampleCoverVideo;
import com.doushi.test.myproject.widgets.extra.LineSpaceExtraCompatTextView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.utils.GSYVideoHelper;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
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

import java.util.HashMap;
import java.util.Map;

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
    //    @BindView(R.id.vflVideoContent)
//    ImageView vflVideoContent;
//    @BindView(R.id.tvVideoTime)
//    TextView tvVideoTime;
    //    @BindView(R.id.praiseView)
//    PraiseView praiseView;
    @BindView(R.id.tvCommentOrCreateTime)
    TextView tvCommentOrCreateTime;
    @BindView(R.id.ivUserPortrait)
    ImageView ivUserPortrait;
    @BindView(R.id.flUserAvatar)
    FrameLayout flUserAvatar;
//    @BindView(R.id.flVideoContent)
//    FrameLayout flVideoContent;
//    @BindView(R.id.ivVideoPlay)
//    ImageView ivVideoPlay;

    private int position;
    private VideoDetails item;
    private Context context;
    /**
     * 数据上报需要的pos
     */
    private String fromPos = "";
    /**
     * 视频播放需要的tag
     */
    public String tag = "";

    @BindView(R.id.flVideoContent)
    SampleCoverVideo gsyVideoPlayer;

    ImageView imageView;
    GSYVideoOptionBuilder gsyVideoOptionBuilder;

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
        this.context = context;
        View childView = LayoutInflater.from(context).inflate(R.layout.view_video_item, this, true);
        ButterKnife.bind(this, childView);
        initVideo();
    }

    private void initVideo() {
        imageView = new ImageView(context);
        gsyVideoOptionBuilder = new GSYVideoOptionBuilder();
    }

    public void setItemVideoContent(VideoDetails info, BaseFragment baseFragment, boolean showCrateTime, boolean isUserClick) {
        this.item = info;
        setNickOrCreateTimeOrCommentCount(tvCommentOrCreateTime, info, showCrateTime, isUserClick);
        UserInfo uInfo = item.getUserInfo();
        if (uInfo != null) {
            flUserAvatar.setVisibility(VISIBLE);
            new ImageLoadUtils(context).commonCircleImage(uInfo.getAvatarUrl(), ivUserPortrait, DefaultValue.HEAD);
        }
        tvVideoDescribe.setText(info.getDescription());
//        tvVideoTime.setText("4:01");
        onBind(position, info);
    }

    /**
     * 设置底部昵称|创建时间|评论数
     *
     * @param tvCommentOrCreateTime 底部控件
     * @param info                  数据
     */
    private void setNickOrCreateTimeOrCommentCount(TextView tvCommentOrCreateTime, VideoDetails info, boolean showCreateTime, boolean isUserClick) {
        if (tvCommentOrCreateTime == null || info == null) {
            return;
        }
        String commentOrCreateTime = "100评论  13:23";
        tvCommentOrCreateTime.setText(commentOrCreateTime);
    }

    public void onBind(final int position, VideoDetails videoModel) {
        //增加封面
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        new ImageLoadUtils(context).commonRoundImage(item.getImgUrl(), imageView, SizeUtils.dp2px(6f), DefaultValue.RADIUS_BACKGROUND);
        if (imageView.getParent() != null) {
            ViewGroup viewGroup = (ViewGroup) imageView.getParent();
            viewGroup.removeView(imageView);
        }
        String url = videoModel.getMp4Url();
        String title = videoModel.getDescription();
        Map<String, String> header = new HashMap<>();
        header.put("ee", "33");

        //防止错位，离开释放
        //gsyVideoPlayer.initUIState();
        gsyVideoOptionBuilder
                .setIsTouchWiget(false)
                .setThumbImageView(imageView)
                .setUrl(url)
                .setVideoTitle(title)
                .setCacheWithPlay(false)
                .setRotateViewAuto(true)
                .setLockLand(true)
                .setPlayTag(String.valueOf(videoModel.getVid()))
                .setMapHeadData(header)
                .setShowFullAnimation(true)
                .setNeedLockFull(true)
                .setPlayPosition(position)
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        if (!gsyVideoPlayer.isIfCurrentIsFullscreen()) {
                            //静音
                            GSYVideoManager.instance().setNeedMute(true);
                        }

                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        //全屏不静音
                        GSYVideoManager.instance().setNeedMute(true);
                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        super.onEnterFullscreen(url, objects);
                        GSYVideoManager.instance().setNeedMute(false);
                        gsyVideoPlayer.getCurrentPlayer().getTitleTextView().setText((String) objects[0]);
                    }
                }).build(gsyVideoPlayer);

        //增加title
        gsyVideoPlayer.getTitleTextView().setVisibility(View.GONE);
        //设置返回键
        gsyVideoPlayer.getBackButton().setVisibility(View.GONE);
        //设置全屏按键功能
        gsyVideoPlayer.getFullscreenButton().setOnClickListener(v -> gsyVideoPlayer.startWindowFullscreen(context, true, true));
    }
}
