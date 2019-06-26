package com.news.example.myproject.widgets.reward;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.SizeUtils;
import com.library.thread.AbstractSafeThread;
import com.library.thread.ThreadPool;
import com.library.util.ImageLoadUtils;
import com.news.example.myproject.R;
import com.news.example.myproject.global.DefaultValue;
import com.news.example.myproject.model.search.RewardGifts;

/**
 * 打赏礼物
 *
 * @author xiemy
 * @date 2017/6/2
 */
public class RewardGiftsLinearLayout extends LinearLayout {

    private ImageView ivVideoAdmirePic;
    private TextView tvVideoAdmireGifts;

    public RewardGiftsLinearLayout(Context context) {
        this(context, null);
    }

    public RewardGiftsLinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    private void init(Context context) {
        removeAllViews();
        ivVideoAdmirePic = new ImageView(getContext());
        LayoutParams layoutParams = new LayoutParams(SizeUtils.dp2px(165F), SizeUtils.dp2px(75F));
        layoutParams.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        addView(ivVideoAdmirePic, layoutParams);

        tvVideoAdmireGifts = new TextView(getContext());
        layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        tvVideoAdmireGifts.setBackgroundResource(R.drawable.bg_text_view_rectangle_64000000_45dp);
        int padding = SizeUtils.dp2px(5);
        tvVideoAdmireGifts.setPadding(padding, padding, padding, padding);
        tvVideoAdmireGifts.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 11);
        tvVideoAdmireGifts.setTextColor(ContextCompat.getColor(context, R.color.white));
        addView(tvVideoAdmireGifts, layoutParams);
    }

    public void startRewardGiftAnimation(@NonNull final RewardGifts gifts, @NonNull final OnRewardAnimationEndListener rewardAnimationEndListener) {
//        int dp160 = SizeUtils.px2dp(160F);
//        int dp70 = SizeUtils.px2dp(70F);
        new ImageLoadUtils(getContext()).commonRoundImage(gifts.getImg(), ivVideoAdmirePic, SizeUtils.dp2px(6f), DefaultValue.RADIUS_BACKGROUND);
        tvVideoAdmireGifts.setText(gifts.getName());
        Animation admireGiftsAnim = AnimationUtils.loadAnimation(getContext(), R.anim.video_admire_gifts_pic);
        // 如果设置为true，控件动画结束时，将保持动画最后时的状态
        admireGiftsAnim.setFillAfter(true);
        admireGiftsAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ThreadPool.execute(new AbstractSafeThread() {
                    @Override
                    public void deal() {
                        try {
                            Thread.sleep(1000);
                            rewardAnimationEndListener.onRewardAnimationEnd(gifts);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ivVideoAdmirePic.startAnimation(admireGiftsAnim);
        Animation admireGiftsAnimDec = AnimationUtils.loadAnimation(getContext(), R.anim.video_admire_gifts_dec);
        admireGiftsAnimDec.setFillAfter(true);
        tvVideoAdmireGifts.startAnimation(admireGiftsAnimDec);
    }

    public interface OnRewardAnimationEndListener {
        void onRewardAnimationEnd(RewardGifts gifts);
    }
}
