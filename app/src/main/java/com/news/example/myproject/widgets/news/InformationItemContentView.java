package com.news.example.myproject.widgets.news;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.library.util.DateUtil;
import com.library.util.ImageLoadUtils;
import com.library.util.ViewUtil;
import com.news.example.myproject.R;
import com.news.example.myproject.global.DefaultValue;
import com.news.example.myproject.model.news.NewsInfo;
import com.news.example.myproject.model.user.UserInfo;
import com.news.example.myproject.widgets.extra.LineSpaceExtraCompatTextView;

import java.math.BigDecimal;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author xiemy1
 * @date 2018/7/24
 */
public class InformationItemContentView extends RelativeLayout {

    @BindView(R.id.tvInformationTitle)
    LineSpaceExtraCompatTextView tvInformationTitle;
    @BindView(R.id.ivSimpleGraph)
    ImageView ivSimpleGraph;
    @BindView(R.id.tvVideoTime)
    TextView tvVideoTime;
    @BindView(R.id.ivUserAvatar1)
    ImageView ivUserAvatar1;
    @BindView(R.id.flUserAvatar1)
    FrameLayout flUserAvatar1;
    @BindView(R.id.tvNickOrCreateTime1)
    TextView tvNickOrCreateTime1;
    @BindView(R.id.llItemBottomView1)
    RelativeLayout llItemBottomView1;
    @BindView(R.id.ivUserAvatar2)
    ImageView ivUserAvatar2;
    @BindView(R.id.flUserAvatar2)
    FrameLayout flUserAvatar2;
    @BindView(R.id.tvNickOrCreateTime2)
    TextView tvNickOrCreateTime2;
    @BindView(R.id.llItemBottomView2)
    RelativeLayout llItemBottomView2;
    @BindView(R.id.clItemSimpleGraph)
    ConstraintLayout clItemSimpleGraph;
    @BindView(R.id.tvInformationDesc)
    LineSpaceExtraCompatTextView tvInformationDesc;
    @BindView(R.id.tvNickOrCreateTime)
    TextView tvNickOrCreateTime;
    @BindView(R.id.ivInfoPic1)
    ImageView ivInfoPic1;
    @BindView(R.id.ivInfoPic2)
    ImageView ivInfoPic2;
    @BindView(R.id.ivInfoPic3)
    ImageView ivInfoPic3;
    @BindView(R.id.ivUserAvatar0)
    ImageView ivUserAvatar0;
    @BindView(R.id.flUserAvatar)
    FrameLayout flUserAvatar;
    @BindView(R.id.rlItemBottomView)
    RelativeLayout rlItemBottomView;
    @BindView(R.id.llItemImgs)
    LinearLayout llItemImgs;
    @BindView(R.id.clItemThreePic)
    ConstraintLayout clItemThreePic;

    private NewsInfo info;

    public InformationItemContentView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public InformationItemContentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public InformationItemContentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View childView = LayoutInflater.from(context).inflate(R.layout.item_news, this, true);
        ButterKnife.bind(this, childView);
    }

    public void setInformationContent(@NonNull Context context, @NonNull NewsInfo info) {
        boolean showUserPic = false;
        if (ObjectUtils.isEmpty(info)) {
            return;
        }
        this.info = info;
        //设置资讯详情信息
        String name = info.getUserInfo() != null ? info.getUserInfo().getName() : "";
        long cTime = info.getPublish_time() != null ? info.getPublish_time() : 0L;
        String commentOrCreateTime = getCommentOrCreateTime(context, name, cTime);
        UserInfo uInfo = info.getUserInfo();
        if (uInfo != null) {
            showUserPic = true;
        }
        int tSize = (ObjectUtils.isNotEmpty(info.getImages())) ? info.getImages().size() : 0;

        //>3 显示3张
        int maxItems = 3;
        if (rlItemBottomView != null) {
            ViewUtil.setMargins(rlItemBottomView, SizeUtils.dp2px(18), SizeUtils.dp2px(10), 0, SizeUtils.dp2px(10));
        }
        //无图
        if (tSize == 0) {
            setNoImageInformation(showUserPic, uInfo != null ? uInfo.getAvatarUrl() : "", commentOrCreateTime);
            //一张图
        } else if (tSize < maxItems) {
            setSimpleGraphLayout(context, info, showUserPic, commentOrCreateTime);
        } else {
            //三张图
            setThreePicLayout(context, info, showUserPic, commentOrCreateTime);
        }
    }

    /**
     * 设置无图样式
     *
     * @param showUserPic 是否显示用户头像
     * @param avatarUrl   用户头像URL
     */
    private void setNoImageInformation(boolean showUserPic, String avatarUrl, String commentOrCreateTime) {
        if (rlItemBottomView != null) {
            ViewUtil.setMargins(rlItemBottomView, SizeUtils.dp2px(18), SizeUtils.dp2px(15), 0, SizeUtils.dp2px(10));
        }
        llItemImgs.setVisibility(View.GONE);
        clItemSimpleGraph.setVisibility(View.GONE);
        clItemThreePic.setVisibility(View.VISIBLE);
        tvInformationDesc.setText(info.getTitle());
        tvNickOrCreateTime.setText(commentOrCreateTime);
        if (showUserPic) {
            flUserAvatar.setVisibility(VISIBLE);
            new ImageLoadUtils(getContext()).commonCircleImage(avatarUrl, ivUserAvatar0, DefaultValue.HEAD);
        } else {
            flUserAvatar.setVisibility(GONE);
        }
    }

    /**
     * 设置资讯单张图
     *
     * @param info 资讯详情
     */
    private void setSimpleGraphLayout(@NonNull final Context context,
                                      @NonNull final NewsInfo info,
                                      final boolean showUserPic,
                                      final String commentOrCreateTime) {
        clItemSimpleGraph.setVisibility(View.VISIBLE);
        clItemThreePic.setVisibility(View.GONE);
        tvInformationTitle.setText(info.getTitle());
        flUserAvatar1.setVisibility(View.GONE);
        flUserAvatar2.setVisibility(View.GONE);

        //设置图片(3图)4/3宽高比
        ViewUtil.setSmallImageViewSize(ivSimpleGraph);
        String img0 = (info.getImages() != null && !info.getImages().isEmpty()) ? info.getImages().get(0) : "";
        new ImageLoadUtils(context).commonRoundImage(img0, ivSimpleGraph, SizeUtils.dp2px(6f), DefaultValue.RADIUS_BACKGROUND);
        //置顶视频类型设置视频时长
//        if (BaseMultiItemEntity.VIDEO.equals(info.getItem_type())) {
//            tvVideoTime.setVisibility(VISIBLE);
//            VideoDetails video = ObjectUtils.isNotEmpty(info.getVideos()) ? info.getVideos().get(0) : null;
//            if (video != null) {
//                tvVideoTime.setText(VideoHelper.Companion.getVideoDuration(video.getDuration()));
//            }
//        } else {
//            tvVideoTime.setVisibility(GONE);
//        }
        tvVideoTime.setVisibility(GONE);
        tvInformationTitle.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                //这个回调会调用多次，获取完行数记得注销监听
                tvInformationTitle.getViewTreeObserver().removeOnPreDrawListener(this);
                int maxCount = 3;
                int lineCount = tvInformationTitle.getLineCount();
                if (lineCount < maxCount) {
                    llItemBottomView1.setVisibility(View.VISIBLE);
                    tvNickOrCreateTime1.setVisibility(View.VISIBLE);
                    tvNickOrCreateTime1.setText(commentOrCreateTime);
                    llItemBottomView2.setVisibility(View.GONE);
                    tvNickOrCreateTime2.setVisibility(View.GONE);
                    ViewUtil.setPadding(clItemSimpleGraph, 0, 0, 0, SizeUtils.dp2px(18));
                    if (showUserPic) {
                        flUserAvatar1.setVisibility(View.VISIBLE);
                        if (info.getUserInfo() != null) {
                            new ImageLoadUtils(context).commonCircleImage(info.getUserInfo().getAvatarUrl(), ivUserAvatar1, DefaultValue.HEAD);
                        }
                    }
                } else {
                    llItemBottomView1.setVisibility(View.GONE);
                    tvNickOrCreateTime1.setVisibility(View.GONE);
                    llItemBottomView2.setVisibility(View.VISIBLE);
                    tvNickOrCreateTime2.setVisibility(View.VISIBLE);
                    tvNickOrCreateTime2.setText(commentOrCreateTime);
                    ViewUtil.setPadding(clItemSimpleGraph, 0, 0, 0, 0);
                    if (showUserPic && info.getUserInfo() != null) {
                        flUserAvatar2.setVisibility(View.VISIBLE);
                        new ImageLoadUtils(context).commonCircleImage(info.getUserInfo().getAvatarUrl(), ivUserAvatar2, DefaultValue.HEAD);
                    }
                }
                return false;
            }
        });
    }

    /**
     * 设置资讯三张图
     *
     * @param info 资讯详情
     */
    private void setThreePicLayout(@NonNull Context context, @NonNull final NewsInfo info, boolean showUserPic, String commentOrCreateTime) {
        llItemImgs.setVisibility(View.VISIBLE);
        clItemSimpleGraph.setVisibility(View.GONE);
        clItemThreePic.setVisibility(View.VISIBLE);
        tvInformationDesc.setText(info.getTitle());
        tvNickOrCreateTime.setText(commentOrCreateTime);
        ViewUtil.setSmallImgViewHeight(llItemImgs);
        //设置图片(3图)4/3宽高比
        ViewUtil.setSmallImageViewSize(ivInfoPic1);
        ViewUtil.setSmallImageViewSize(ivInfoPic2);
        ViewUtil.setSmallImageViewSize(ivInfoPic3);
        String img0 = (info.getImages() != null && !info.getImages().isEmpty()) ? info.getImages().get(0) : "";
        new ImageLoadUtils(context).commonRoundLeftImage(img0, ivInfoPic1, SizeUtils.dp2px(6), DefaultValue.RADIUS_LEFT_BACKGROUND);
        String img1 = (info.getImages() != null && info.getImages().size() > 1) ? info.getImages().get(1) : "";
        new ImageLoadUtils(context).asBitmap(img1, ivInfoPic2, DefaultValue.BACKGROUND);
        String img2 = (info.getImages() != null && info.getImages().size() > 2) ? info.getImages().get(2) : "";
        new ImageLoadUtils(context).commonRoundRightImage(img2, ivInfoPic3, SizeUtils.dp2px(6), DefaultValue.RADIUS_RIGHT_BACKGROUND);
        if (showUserPic) {
            flUserAvatar.setVisibility(VISIBLE);
            new ImageLoadUtils(context).commonCircleImage(info.getUserInfo() != null ? info.getUserInfo().getAvatarUrl() :
                    "", ivUserAvatar0, DefaultValue.HEAD);
        } else {
            flUserAvatar.setVisibility(GONE);
        }
    }

    private String getCommentOrCreateTime(Context context, String nick, long cTime) {
        long random = new Random().nextInt(10000);
        String commentCount = getString(random);
        String createTime = DateUtil.getStringTime(cTime);
        return context.getString(R.string.nick_comment_create_time, nick, commentCount, createTime);
    }

//    @OnClick({R.id.tvNickOrCreateTime, R.id.tvNickOrCreateTime1, R.id.tvNickOrCreateTime2,
//            R.id.ivContentMore, R.id.ivContentMore1, R.id.ivContentMore2,
//            R.id.flUserAvatar, R.id.flUserAvatar1, R.id.flUserAvatar2})
//    public void onViewClicked(View v) {
//        if (info == null || VerificationUtils.isFastDoubleClick(v.getId())) {
//            return;
//        }
//        switch (v.getId()) {
//            //资讯更多/视频更多
//            case R.id.ivContentMore:
//            case R.id.ivContentMore1:
//            case R.id.ivContentMore2:
//                if (informationClickListener != null) {
//                    String imgUrl = ObjectUtils.isNotEmpty(info.getThumbnails()) ? info.getThumbnails().get(0).get360Url() : "";
//                    informationClickListener.onMoreClick(info, imgUrl, position);
//                }
//                break;
//
//            //用户头像|用户名|评论|创建时间
//            case R.id.flUserAvatar:
//            case R.id.flUserAvatar1:
//            case R.id.flUserAvatar2:
//            case R.id.tvNickOrCreateTime:
//            case R.id.tvNickOrCreateTime1:
//            case R.id.tvNickOrCreateTime2:
//                if (informationClickListener != null) {
//                    informationClickListener.onUserAvatarClick(info);
//                }
//                break;
//
//            default:
//                break;
//        }
//    }

    /**
     * 一些数值的显示规则
     * 1.	评论数
     * 2.	点赞/点踩数
     * 3.	视频播放数
     * 4.	粉丝数
     * 5.	关注数
     * 以上这些数值，超过1000是 用缩写“万”， 保留一位小数，不足0.1万的部分舍去，举例：10999,显示为1.0万；11001，显示为1.1万 。
     *
     * @param count
     * @return
     */
    public static String getString(long count) {
        if (count < 0) {
            return "0";
        } else if (count < 10000) {
            return String.valueOf(count);
        } else {
            double d = (double) count / 10000;
            BigDecimal bg = new BigDecimal(d);
            double d1 = bg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
            return d1 + "万";
        }
    }
}
