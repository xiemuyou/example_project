package com.news.example.myproject.ui.search.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 搜索适配器
 * Created by xiemy on 2017/5/19.
 */
public class SearchAdapter1 extends RecyclerView.Adapter<RecyclerView.ViewHolder>/* implements
        VideoItemView, VideoRecyclerView.OnVideoPlayerListener, RewardView,
        PlaybackControlLayer.FullscreenCallback, VideoFrameLayout.OnVideoPlayStateChangedListener*/ {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

//    private static final String TAG = "SearchAdapter1";
//
//    private BaseActivity context;
////    private VideoItemPresenter itemPresenter;
//    private List<Object> searchDataList;
//    private boolean showMoreUser = false;
//    //打赏礼物
////    private RewardPresenter rewardPresenter;
////    private OnRewardGiftsListener giftsListener;
//
//    private final int USER_INTO = 0;
//    private final int VIDEO_HEAD = 1;
//    private final int VIDEO_INFO = 2;
//    private boolean autoPlay = false;
//
//    public SearchAdapter1(BaseActivity context, List<Object> searchDataList, VideoItemPresenter itemPresenter,
//                         OnRewardGiftsListener giftsListener) {
//        this.context = context;
//        this.searchDataList = searchDataList;
//        this.itemPresenter = itemPresenter;
//        this.itemPresenter.attachView(this);
//        this.rewardPresenter = new RewardPresenter(this);
//        this.giftsListener = giftsListener;
//    }
//
//    private View.OnClickListener moreUserListener;
//
//    public void setOnMoreUserListener(View.OnClickListener moreUserListener) {
//        this.moreUserListener = moreUserListener;
//    }
//
//    @Override
//    public int getItemCount() {
//        return searchDataList != null ? searchDataList.size() : 0;
//    }
//
//    private PlaybackControlLayer.FullscreenCallback fullscreenBack;
//    private RecyclerView recyclerView;
//
//    public void setFullscreenBack(RecyclerView recyclerView, PlaybackControlLayer.FullscreenCallback fullscreenBack) {
//        this.recyclerView = recyclerView;
//        this.fullscreenBack = fullscreenBack;
//    }
//
//    public void updateDataList(List<Object> searchDataList, boolean showMoreUser, String searchStr) {
//        if (searchDataList != null && !searchDataList.isEmpty()) {
//            this.searchDataList = searchDataList;
//            this.showMoreUser = showMoreUser;
//            this.searchStr = searchStr;
//            notifyDataSetChanged();
//        }
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        Object searchObj = searchDataList.get(position);
//        if (searchObj instanceof UserInfo) {
//            return USER_INTO;
//
//        } else if (searchObj instanceof VideoDetails) {
//            return VIDEO_INFO;
//
//        } else {
//            return VIDEO_HEAD;
//        }
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        RecyclerView.ViewHolder viewHolder = null;
//        View itemView;
//        switch (viewType) {
//            case USER_INTO:
//                itemView = LayoutInflater.from(context).inflate(R.layout.item_common_users, null);
//                viewHolder = new UserInfoViewHolder(itemView);
//                break;
//
//            case VIDEO_HEAD:
//                itemView = LayoutInflater.from(context).inflate(R.layout.item_search_video_head, null);
//                viewHolder = new SearchVideoHeadViewHolder(itemView);
//                break;
//
//            case VIDEO_INFO:
//                itemView = LayoutInflater.from(context).inflate(R.layout.item_video_details, null);
//                viewHolder = new MuteVideoViewHolder(itemView);
//                break;
//        }
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        if (holder instanceof UserInfoViewHolder) {
//            UserInfoViewHolder userHolder = (UserInfoViewHolder) holder;
//            UserInfo userInfo = (UserInfo) searchDataList.get(position);
//            if (userInfo != null) {
//                userHolder.flSearchUserHead.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
//                setUserInfo(userHolder, userInfo);
//            }
//
//        } else if (holder instanceof SearchVideoHeadViewHolder) {
//            SearchVideoHeadViewHolder headHolder = (SearchVideoHeadViewHolder) holder;
//            String videoCount = context.getString(R.string.search_video_count, String.valueOf(searchDataList.get(position)));
//            headHolder.tvVideoCount.setText(videoCount);
//
//        } else {
//            MuteVideoViewHolder videoHolder = (MuteVideoViewHolder) holder;
//            VideoDetails video = (VideoDetails) searchDataList.get(position);
//            if (video != null) {
//                setVideoDetails(videoHolder, video);
//            }
//        }
//    }
//
//    private void setUserInfo(final UserInfoViewHolder uHolder, final UserInfo userInfo) {
//        ImageLoadUtils.setSimpleCircleHeadIcon(context, userInfo.getHead(), DefaultValue.HEAD, uHolder.ivCommonUserHead);
//        uHolder.ivCommonUserHead.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new JumpOtherHomeEmp(context).jumpOtherHomeActivity(userInfo.getUid());
//            }
//        });
//        setSearchNick(uHolder.tvCommonName, userInfo.getNick());
//        if (!TextUtils.isEmpty(userInfo.getSign())) {
//            uHolder.tvCommonSign.setText(userInfo.getSign());
//        }
//        uHolder.tvMoreUser.setText(showMoreUser ? context.getString(R.string.to_view_more) : "");
//        uHolder.flSearchUserHead.setEnabled(showMoreUser);
//        uHolder.flSearchUserHead.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (moreUserListener != null) {
//                    moreUserListener.onClick(v);
//                }
//            }
//        });
//        switch (userInfo.getUserRelation()) {
//            case 0: //相互没有关注
//            case 2: //别人关注我
//                uHolder.ivCommonRight.setBackgroundResource(R.mipmap.video_focus);
//                break;
//
//            case 1: //我关注别人
//            case 3: //相互关注
//                uHolder.ivCommonRight.setBackgroundResource(R.mipmap.video_un_focus);
//                break;
//        }
//
//        uHolder.ivCommonRight.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!VerificationUtils.isFastDoubleClick(500)) {
//                    operationPosition = uHolder.getAdapterPosition();
//                    operationHolder = uHolder;
//                    focusUser(userInfo.getUserRelation(), userInfo.getUid());
//                }
//            }
//        });
//    }
//
//    private String searchStr;
//
//    private void setSearchNick(TextView tvSearchNick, String nick) {
//        if (!TextUtils.isEmpty(searchStr) && !TextUtils.isEmpty(nick)) {
//            StyleBuilder sb = new StyleBuilder();
//            SparseBooleanArray array = StringUtils.getPositions(searchStr, nick);
//            String[] ss = nick.split("");
//            for (int i = 0; i < array.size(); i++) {
//                if (array.get(array.keyAt(i))) {
//                    sb.addTextStyle(String.valueOf(ss[i]))
//                            .textColor(context.getResources().getColor(R.color.goldColor))
//                            .commit();
//                } else {
//                    sb.text(String.valueOf(ss[i]));
//                }
//            }
//            sb.show(tvSearchNick);
//        }
//    }
//
//    private RecyclerView.ViewHolder operationHolder;
//    private static int operationPosition;
//    //当前播放 position
//    private static int playPosition = -1;
//    //视频宽度
//    private static final int itemViewWidth = Screen.getInstance().widthPixels - DipUtil.dipToPixels(10) * 2;
//
//    private void setVideoDetails(final MuteVideoViewHolder holder, final VideoDetails video) {
//
//        holder.parent.setTag(R.id.video_holder, holder);
//        holder.parent.setTag(R.id.video_details, video);
//
//        ImageLoadUtils.setSimpleCircleHeadIcon(context, video.getUserHead(), DefaultValue.HEAD, holder.ivUserHead);
//
//        holder.tvUserNick.setText(video.getUserNick()); //用户名
//        holder.tvVideoPlayCount.setText(context.getString(R.string.video_play_count
//                , String.valueOf(video.getPlayCount()))); //视频播放次数
//
//        if (video.getUserRelation() == 0 || video.getUserRelation() == 2) { //没有粉丝,显示添加粉丝按钮
//            holder.tvNo_Fans.setText("");
//            holder.tvNo_Fans.setBackgroundResource(R.mipmap.video_focus);
//        } else {//第几号粉丝
//            holder.tvNo_Fans.setBackgroundResource(0);
//            holder.tvNo_Fans.setText(context.getString(
//                    R.string.video_No_fans,
//                    String.valueOf(video.getFansNum())));
//        }
//        holder.tvNo_Fans.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!VerificationUtils.isFastDoubleClick(500)) {
//                    operationHolder = holder;
//                    focusUser(video.getUserRelation(), video.getUid());
//                }
//            }
//        });
//
//        setSearchNick(holder.tvVideoDescribe, video.getDes()); //视频描述
//        holder.tvVideoDescribe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new JumpVideoDetailEmp(context).jumpVideoDetailActivity(video, 0);
//            }
//        });
//
//        if (video.getRewardTotal() > 0) {
//            holder.llRewardList.setRewardList(video.getRewardList(), video.getRewardTotal());
//            holder.llRewardList.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, RewardListActivity.class);
//                    intent.putExtra(Constants.VID, video.getVid());
//                    context.startActivity(intent);
//                }
//            });
//        } else {
//            holder.llRewardList.removeAllViews();
//            holder.llRewardList.setRewardText(context.getString(R.string.video_reward_hint));
//        }
//
//        //视频打赏夺宝
//        holder.tvRewardIndiana.setOnClickListener(new View.OnClickListener() { //视频打赏夺宝
//            @Override
//            public void onClick(View v) {
//                List<RewardGifts> rewardList = rewardPresenter.getRewardList();
//                if (rewardList != null && !rewardList.isEmpty()) {
//                    operationPosition = holder.getAdapterPosition();
//                    rewardBox = new VideoRewardBox(context, admireListener, rewardList);
//                    if (UserOperationUtil.whetherLogin()) {
//                        rewardBox.setBiAmount(UserOperationUtil.getUserInfo().getBiAmount());
//                    }
//                    rewardBox.show();
//                } else {
//                    rewardPresenter.getRewardGiftList();
//                }
//            }
//        });
//        List<VideoComment> hotCommentList = video.getHotCommentList();
//        if (hotCommentList != null && hotCommentList.size() > 0) {
//            VideoComment hotComment = hotCommentList.get(0);
//            holder.tvVideoHotComment.setVisibility(View.VISIBLE);
//            //创建一个 SpannableString对象
//            String nickName = StringUtils.replaceStr(hotComment.getNick(), "...", 7);
//            SpannableString sp = new SpannableString(nickName + "：" + hotComment.getContent());
//            sp.setSpan(new ForegroundColorSpan(
//                            context.getResources().getColor(R.color.user_nick_color)),
//                    0, nickName.length() + 1,
//                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//
//            holder.tvVideoHotComment.setText(sp);
//            String hComment = holder.tvVideoHotComment.getText().toString();
//            holder.tvVideoHotComment.setText(StringUtils.getEmojiStr(hComment));
//        } else {
//            holder.tvVideoHotComment.setVisibility(View.GONE);
//        }
//        holder.tvVideoAllComment.setText(context.getString(
//                R.string.video_total_comment,
//                String.valueOf(video.getCommentCount()))); //评论总数
//        holder.tvVideoAllComment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new JumpVideoDetailEmp(context).jumpVideoDetailActivity(video, 0);
//            }
//        });
//        //下载视频
//        holder.tvVideoDownload.setOnClickListener(new View.OnClickListener() { //下载视频
//            @Override
//            public void onClick(View v) {
//                if (!VerificationUtils.isFastDoubleClick(500)) {
//                    if (UserOperationUtil.whetherLogin()) {
//                        DownUtil downUtil = new DownUtil(context);
//                        downUtil.checkDownCount(5, video); //目前下载次数不限制
//                    } else {
//                        showLogin();
//                    }
//                }
//            }
//        });
//
//        //视频点赞
//        if (video.getLikeFlag() == 0) {
//            ViewUtil.setTextColor(context, holder.tvVideoThumbTotal, R.color.common_gray_text_color);
//            ViewUtil.setDrawable(context, holder.tvVideoThumbTotal, R.mipmap.video_thumb_unselect_small, ViewUtil.DrawablePosition.LIFT);
//        } else {
//            ViewUtil.setTextColor(context, holder.tvVideoThumbTotal, R.color.goldColor);
//            ViewUtil.setDrawable(context, holder.tvVideoThumbTotal, R.mipmap.video_thumb_select_small, ViewUtil.DrawablePosition.LIFT);
//        }
//        holder.tvVideoThumbTotal.setText(String.valueOf(video.getLikeCount())); //点赞总数
//        holder.tvVideoThumbTotal.setOnClickListener(new View.OnClickListener() { //下载视频
//            @Override
//            public void onClick(View v) {
//                if (!VerificationUtils.isFastDoubleClick(500)) {
//                    operationHolder = holder;
//                    if (itemPresenter != null) {
//                        if (video.getLikeFlag() == 0) {
//                            itemPresenter.likeVideo(video.getVid());
//
//                            //统计视频点赞
//                            new StatisticalUtil.Builder()
//                                    .setE(Module.LIKE.type)
//                                    .setV(String.valueOf(video.getVid()))
//                                    .statistics();
//                        } else {
//                            itemPresenter.unLikeVideo(video.getVid());
//                        }
//                    }
//                }
//            }
//        });
//
//        //视频分享
//        holder.tvVideoShareTotal.setText(String.valueOf(video.getShareCount())); //分享总数
//        holder.tvVideoShareTotal.setOnClickListener(new View.OnClickListener() { //下载视频
//            @Override
//            public void onClick(View v) {
//                if (!VerificationUtils.isFastDoubleClick(500)) {
//                    operationHolder = holder;
//                    ShareDialog shareDialog = new ShareDialog(context, new ShareUtil.OnShareListener() {
//                        @Override
//                        public void shareSuccess(String type) {
//                            holder.tvVideoShareTotal.setText(String.valueOf(video.addShareCount())); //分享总数
//                        }
//
//                        @Override
//                        public void shareFailed(String type) {
//                        }
//                    });
//                    shareDialog.setShareContent(video);
//                    shareDialog.show();
//                }
//            }
//        });
//
//
//        /*  ============================================= 视频播放器 ====================================== */
//        holder.vflVideoContent.setVideoDetail(context, video, itemViewWidth, true, itemPresenter);
//        autoPlay = UserOperationUtil.getIsWifiPlay(); //用户选择了wifi自动播放
//        if ((video.getPlayState() == PlayState.READY || //当前状态是预备播放状态
//                (firstAutoPlay && holder.getAdapterPosition() == playPosition))) { //第一个自动播放且是第一个
//            play(holder, video, true);
//        }
//
//        /*  ============================================= 视频播放器 ======================================  */
//    }
//
//    @Override
//    public void videoPlayer(VideoViewHolder lastHolder, VideoViewHolder holder, VideoDetails video) {
//        if (holder != null && holder instanceof MuteVideoViewHolder) {
//            MuteVideoViewHolder mHolder = (MuteVideoViewHolder) holder;
//            LogUtil.d(TAG, "当前播放视频位置 = " + mHolder.getAdapterPosition());
//            boolean firstPlay = !VideoPlayControl.getVpControlInstance().isVideoPlayer();
//            if (firstPlay || playPosition != mHolder.getAdapterPosition()) {
//                VideoPlayControl.getVpControlInstance().release();
//                play(mHolder, video, firstPlay);
//            }
//        }
//    }
//
//    //首次进入自动播放
//    private boolean firstAutoPlay = true;
//    private VideoFrameLayout vflVideoContent;
//
//    private void play(MuteVideoViewHolder holder, VideoDetails video, boolean firstPlay) {
//        if (firstPlay || playPosition != holder.getAdapterPosition()) {
//            firstAutoPlay = false;
//            if (vflVideoContent != null) {
//                vflVideoContent.setState(PlayState.RELEASE);
//            }
//            playPosition = holder.getAdapterPosition();
//            if (VideoUtil.isAutoPlay(context)) {
//                holder.vflVideoContent.setVideoPlayStateChangedListener(this);
//                holder.vflVideoContent.videoPlay(0);
//            }
//        }
//        vflVideoContent = holder.vflVideoContent;
//        //异步查询保存
//        saveVideo(video);
//    }
//
//    /**
//     * 异步查询video并保存到本地
//     *
//     * @param video
//     */
//    private void saveVideo(final VideoDetails video) {
//        video.setPlayTimeStamp(System.currentTimeMillis());
//        video.saveOrUpdateAsync(" vid = ? ", video.getVid() + "").listen(new SaveCallback() {
//            @Override
//            public void onFinish(boolean success) {
//                LogUtil.d(TAG, video.getVid());
//            }
//        });
//    }
//
//    private VideoRewardBox rewardBox;
//    private RewardGifts gifts;
//    private VideoRewardBox.VideoAdmireListener admireListener = new VideoRewardBox.VideoAdmireListener() {
//
//        @Override
//        public void buyDouPrice() {
//            if (UserOperationUtil.whetherLogin()) {
//                Bundle bundle = new Bundle();
//                UserInfo userInfo = UserOperationUtil.getUserInfo();
//                bundle.putInt(Constants.BEAN_COIN_COUNT, userInfo.getBiAmount());
//                Intent intent = new Intent(context, MineWealthActivity.class);
//                intent.putExtras(bundle);
//                context.startActivityForResult(intent, IntConstant.MINE_WEALTH);
//            } else {
//                showLogin();
//            }
//        }
//
//        @Override
//        public void giftsGive(RewardGifts gifts) {
//            if (UserOperationUtil.whetherLogin()) {
//                UserInfo user = UserOperationUtil.getUserInfo();
//                if (gifts != null && user.getBiAmount() >= gifts.getPrice()
//                        && searchDataList.get(operationPosition) instanceof VideoDetails) {
//
//                    VideoDetails video = (VideoDetails) searchDataList.get(operationPosition);
//                    if (video != null && rewardBox != null) {
//                        rewardBox.dismiss();
//                        SearchAdapter1.this.gifts = gifts;
//                        rewardPresenter.rewardVideo(video.getVid(), gifts.getGiftid());
//                        DialogUtils.showProgressDialog(context, R.string.is_reward);
//                    }
//                } else { //余额不足提示
//                    ToastUtils.showToast(context, R.string.admire_lack_of_balance_hint);
//                }
//            } else {
//                rewardBox.dismiss();
//                showLogin();
//            }
//        }
//    };
//
//    @Override
//    public boolean onVideoPlayStateChanged(PlayState state) {
//        return false;
//    }
//
//    /**
//     * 关注用户
//     *
//     * @param userRelation 关注状态
//     * @param uid          关注对象 UID
//     */
//    private void focusUser(int userRelation, int uid) {
//        if (UserOperationUtil.whetherLogin() && itemPresenter != null) {
//            if (HttpHelper.sharedInstance().getUid() != uid) {
//                if (userRelation == 0 || userRelation == 2) {
//                    itemPresenter.attentionUser(uid);
//                } else {
//                    Intent intent = new Intent(context, FansRankActivity.class);
//                    intent.putExtra(Constants.UID, uid);
//                    context.startActivity(intent);
//                }
//            }
//        } else {
//            showLogin();
//        }
//    }
//
//    private int padding = DipUtil.dipToPixels(10);
//    private int[] lastPositions = new int[2];
//
//    @Override
//    public void onGoToFullscreen(int[] originalPos) {
//        MuteVideoViewHolder videoHolder = getViewHolderByPosition(operationPosition);
//        if (videoHolder != null) {
////            videoHolder.flVideoPlayer.getLayoutParams().width = Screen.getInstance().widthPixels;
////            videoHolder.flVideoPlayer.getLayoutParams().height = Screen.getInstance().heightPixels;
//            videoHolder.llVideoItemView.setPadding(0, 0, 0, 0);
////            videoHolder.ivVideoItemRoundedCornersBg.setBackgroundResource(R.color.transparent);
//            LinearLayoutManager linearManager;
//            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
//            if (layoutManager instanceof LinearLayoutManager) {
//                linearManager = (LinearLayoutManager) layoutManager;
//                View topView = linearManager.getChildAt(0);             //获取可视的第一个view
//                if (topView != null) {
//                    lastPositions[0] = topView.getTop();                    //获取与该view的顶部的偏移量
//                    lastPositions[1] = linearManager.getPosition(topView);  //得到该View的数组位置
//                }
//                //如果当前可见最后条目,大于等于总条目,
//                //添加padding用于横屏跳到视频播放器顶部
//                if (linearManager.findLastVisibleItemPosition() >= getItemCount() - 1) {
//                    recyclerView.setPadding(0, 0, 0, Screen.getInstance().widthPixels);
//                } else {
//                    recyclerView.setPadding(0, 0, 0, 0);
//                }
//            }
//            if (fullscreenBack != null) {
//                fullscreenBack.onGoToFullscreen(originalPos);
//            }
//        }
//    }
//
//    @Override
//    public void onReturnFromFullscreen() {
//        recyclerView.setPadding(0, 0, 0, 0);
//        MuteVideoViewHolder videoHolder = getViewHolderByPosition(operationPosition);
//        if (videoHolder != null) {
////            videoHolder.ivVideoItemRoundedCornersBg.setBackgroundResource(R.drawable.item_white_rounded_corners6_bg);
////            videoHolder.flVideoPlayer.getLayoutParams().width = Screen.getInstance().widthPixels - padding * 2;
////            videoHolder.flVideoPlayer.getLayoutParams().height = Screen.getInstance().heightPixels / 3;
//            videoHolder.llVideoItemView.setPadding(padding, 0, padding, 0);
//            //恢复全屏之前位置
//            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
//            if (layoutManager instanceof LinearLayoutManager) {
//                LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
//                linearManager.scrollToPositionWithOffset(lastPositions[1], lastPositions[0]);
//            }
//            if (fullscreenBack != null) {
//                fullscreenBack.onReturnFromFullscreen();
//            }
//        }
//    }
//
//    /**
//     * 根据位置获取该下标 ViewHolder
//     *
//     * @param position 要查询的下标
//     * @return 视频ItemViewHolder
//     */
//    private MuteVideoViewHolder getViewHolderByPosition(int position) {
//        MuteVideoViewHolder vView;
//        if (recyclerView != null) {
//            for (int i = 0; i < recyclerView.getChildCount(); ++i) {
//                View childAt = recyclerView.getChildAt(i);
//                RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(childAt);
//                if (viewHolder instanceof MuteVideoViewHolder) {
//                    vView = (MuteVideoViewHolder) viewHolder;
//                    if (vView.getAdapterPosition() == position) {
//                        return vView;
//                    }
//                }
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public void likeVideo() {
//        if (operationHolder != null && operationHolder instanceof MuteVideoViewHolder) {
//            MuteVideoViewHolder holder = (MuteVideoViewHolder) operationHolder;
//            VideoDetails video = (VideoDetails) holder.parent.getTag(R.id.video_details);
//            if (video != null) {
//                video.setLikeFlag(1);
//                ViewUtil.setTextColor(context, holder.tvVideoThumbTotal, R.color.goldColor);
//                ViewUtil.setDrawable(context, holder.tvVideoThumbTotal,
//                        R.mipmap.video_thumb_select_small, ViewUtil.DrawablePosition.LIFT);
//                holder.tvVideoThumbTotal.setText(String.valueOf(video.addLikeCount()));
//
//                //统计视频点赞
//                new StatisticalUtil.Builder()
//                        .setE(Module.LIKE.type)
//                        .setV(String.valueOf(video.getVid()))
//                        .statistics();
//            }
//        }
//    }
//
//    @Override
//    public void unLikeVideo() {
//        if (operationHolder != null && operationHolder instanceof MuteVideoViewHolder) {
//            MuteVideoViewHolder holder = (MuteVideoViewHolder) operationHolder;
//            VideoDetails video = (VideoDetails) holder.parent.getTag(R.id.video_details);
//            if (video != null) {
//                video.setLikeFlag(0);
//                ViewUtil.setTextColor(context, holder.tvVideoThumbTotal, R.color.common_gray_text_color);
//                ViewUtil.setDrawable(context, holder.tvVideoThumbTotal,
//                        R.mipmap.video_thumb_unselect_small, ViewUtil.DrawablePosition.LIFT);
//                holder.tvVideoThumbTotal.setText(String.valueOf(video.reductionLikeCount()));
//            }
//        }
//    }
//
//    @Override
//    public void attentionUser(int fansNum) {
//        if (operationHolder != null) {
//            if (operationHolder instanceof MuteVideoViewHolder) {
//                MuteVideoViewHolder holder = (MuteVideoViewHolder) operationHolder;
//                ViewUtil.setBackgroundColor(context, holder.tvNo_Fans, R.color.transparent);
//                holder.tvNo_Fans.setText(context.getString(
//                        R.string.video_No_fans, String.valueOf(fansNum)));
//            } else if (operationHolder instanceof UserInfoViewHolder) {
//                UserInfoViewHolder holder = (UserInfoViewHolder) operationHolder;
//                holder.ivCommonRight.setBackgroundResource(R.mipmap.video_un_focus);
//                if (searchDataList.get(operationPosition) != null) {
//                    if (searchDataList.get(operationPosition) instanceof UserInfo) {
//                        ((UserInfo) searchDataList.get(operationPosition)).setUserRelation(1);
//                    }
//                }
//            }
//        }
//    }
//
//    @Override
//    public void unAttentionUser() {
//        if (operationHolder instanceof UserInfoViewHolder) {
//            UserInfoViewHolder holder = (UserInfoViewHolder) operationHolder;
//            holder.ivCommonRight.setBackgroundResource(R.mipmap.video_focus);
//            if (searchDataList.get(operationPosition) != null) {
//                if (searchDataList.get(operationPosition) instanceof UserInfo) {
//                    ((UserInfo) searchDataList.get(operationPosition)).setUserRelation(0);
//                }
//            }
//        }
//    }
//
//    @Override
//    public void getVideoDetailsInfoSuccess(VideoDetails video) {
//        if (video != null) {
//            video.setPlayState(PlayState.READY);
//            searchDataList.remove(operationPosition);
//            searchDataList.add(operationPosition, video);
//            notifyItemChanged(operationPosition, video);
//        }
//    }
//
//    @Override
//    public void getRewardUserList(List<RewardUser> rewardUsers) {
//
//    }
//
//    @Override
//    public void getRewardGiftList(RewardGiftResponse giftRes) {
//
//    }
//
//    @Override
//    public void rewardVideo(int biAmount) {
//        UserInfo user = UserOperationUtil.getUserInfo();
//        user.setBiAmount(biAmount);
//        UserOperationUtil.saveUser(user);
//        if (giftsListener != null) {
//            giftsListener.onRewardGifts(gifts);
//        }
//
//        sendStatisticalReward();
//        if (gifts != null && gifts.getPrice() >= 10) //单价大于10豆币的为夺宝
//            sendStatisticalIndiana(String.valueOf(gifts.getGiftid()));
//        DialogUtils.dismiss();
//    }
//
//    /**
//     * 统计成功打赏
//     */
//    private void sendStatisticalReward() {
//        VideoDetails video = (VideoDetails) searchDataList.get(operationPosition);
//        if (video != null) {
//            Map<Custom, Object> custom = new HashMap<>();
//            custom.put(Custom.s0004, video.getVid());
//            custom.put(Custom.s0022, video.getUid());
//            custom.put(Custom.s0023, video.getUserNick());
//            new StatisticalUtil.Builder()
//                    .setE(Module.REWARD_USER.type)
//                    .setV("2") //打赏视频
//                    .setCustomDim(custom)
//                    .statistics();
//        }
//    }
//
//    /**
//     * 礼物被打赏次数
//     * @param giftId 礼物id
//     */
//    private void sendStatisticalIndiana(String giftId) {
//        new StatisticalUtil.Builder()
//                .setE(Module.REWARD_GIFTS_COUNT.type)
//                .setV(giftId) //礼物id
//                .statistics();
//    }
//
//    @Override
//    public void failedRewardVideo() {
//
//    }
//
//    @Override
//    public void loadDataFail(String errorInfo) {
//        DialogUtils.dismiss();
//        ToastUtils.showToast(context, errorInfo, ToastUtils.ToastType.ERROR_TYPE);
//    }
//
//    private void showLogin() {
//        LoginBox loginBox = new LoginBox(context);
//        loginBox.setRequestCode(IntConstant.LOGIN);
//        loginBox.show();
//    }
//
//    public void videoPause() {
//        VideoPlayControl.getVpControlInstance().pause();
//        MuteVideoViewHolder playHolder = getViewHolderByPosition(playPosition);
//        if (playHolder != null) {
//            playHolder.vflVideoContent.videoPause();
//        }
//    }
//
//    public void videoPlay() {
//        if (VideoPlayControl.getVpControlInstance().getState() == PlayState.PAUSE) {
//            MuteVideoViewHolder playHolder = getViewHolderByPosition(playPosition);
//            if (playHolder != null) {
//                playHolder.vflVideoContent.videoPlayer();
//            }
//        }
//    }
//
//    public void videoRelease() {
//        notifyItemChanged(playPosition);
//        VideoPlayControl.getVpControlInstance().release();
//        MuteVideoViewHolder playHolder = getViewHolderByPosition(playPosition);
//        if (playHolder != null) {
//            playHolder.vflVideoContent.release();
//        }
//    }
//
//    /**
//     * 全屏切换
//     */
//    public void setFullscreen(boolean shouldBeFullscreen) {
////        VideoPlayControl.getVpControlInstance().setFullscreen(shouldBeFullscreen);
//    }
//
//    public void detachViews() {
//        if (rewardPresenter != null) {
//            rewardPresenter.detachView();
//        }
//    }
}
