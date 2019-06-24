package com.library.widgets.canrefresh;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.blankj.utilcode.util.SizeUtils;
import com.library.R;
import com.library.util.LogUtil;

/**
 * @author xiemy
 */
public class ClassicRefreshView extends FrameLayout implements CanRefresh {

    private ImageView imageView;

    private int currentFrame = 0;

    private AnimationDrawable fowardAnim;
    private int marginTop = SizeUtils.dp2px(10);

    public ClassicRefreshView(Context context) {
        this(context, null);
    }

    public ClassicRefreshView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClassicRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View v = LayoutInflater.from(getContext()).inflate(R.layout.layout_classic_refresh, null);
        addView(v);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        imageView = findViewById(R.id.ivRefresh);
    }

    public void setCurrentFrame(int y) {
        if (y > 0) {
            currentFrame = y;
            FrameLayout.LayoutParams layoutParams = (LayoutParams) imageView.getLayoutParams();
            layoutParams.setMargins(0, marginTop, 0, currentFrame);
            imageView.setLayoutParams(layoutParams);
        } else {
            FrameLayout.LayoutParams layoutParams = (LayoutParams) imageView.getLayoutParams();
            layoutParams.setMargins(0, marginTop, 0, currentFrame / 5);
            imageView.setLayoutParams(layoutParams);
        }
        currentFrame = y;
        switch (currentFrame) {
            case 1:
                imageView.setBackgroundResource(R.drawable.pull_01);
                break;
            case 2:
                imageView.setBackgroundResource(R.drawable.pull_02);
                break;
            case 3:
                imageView.setBackgroundResource(R.drawable.pull_03);
                break;
            case 4:
                imageView.setBackgroundResource(R.drawable.pull_04);
                break;
            case 5:
                imageView.setBackgroundResource(R.drawable.pull_05);
                break;
            case 6:
                imageView.setBackgroundResource(R.drawable.pull_06);
                break;
            case 7:
                imageView.setBackgroundResource(R.drawable.pull_07);
                break;
            case 8:
                imageView.setBackgroundResource(R.drawable.pull_08);
                break;
            case 9:
                imageView.setBackgroundResource(R.drawable.pull_09);
                break;
            case 10:
                imageView.setBackgroundResource(R.drawable.pull_10);
                break;
            case 11:
                imageView.setBackgroundResource(R.drawable.pull_11);
                break;
            case 12:
                imageView.setBackgroundResource(R.drawable.pull_12);
                break;
            case 13:
                imageView.setBackgroundResource(R.drawable.pull_13);
                break;
            case 14:
                imageView.setBackgroundResource(R.drawable.pull_14);
                break;
            case 15:
                imageView.setBackgroundResource(R.drawable.pull_15);
                break;
            case 16:
                imageView.setBackgroundResource(R.drawable.pull_16);
                break;
            case 17:
                imageView.setBackgroundResource(R.drawable.pull_17);
                break;
            case 18:
                imageView.setBackgroundResource(R.drawable.pull_18);
                break;
            case 19:
                imageView.setBackgroundResource(R.drawable.pull_19);
                break;
            case 20:
                imageView.setBackgroundResource(R.drawable.pull_20);
                break;
            case 21:
                imageView.setBackgroundResource(R.drawable.pull_21);
                break;
            case 22:
                imageView.setBackgroundResource(R.drawable.pull_22);
                break;
            case 23:
                imageView.setBackgroundResource(R.drawable.pull_23);
                break;
            case 24:
                imageView.setBackgroundResource(R.drawable.pull_24);
                break;
            case 25:
                imageView.setBackgroundResource(R.drawable.pull_25);
                break;
            case 26:
                imageView.setBackgroundResource(R.drawable.pull_26);
                break;
            case 27:
                imageView.setBackgroundResource(R.drawable.pull_27);
                break;
            case 28:
                imageView.setBackgroundResource(R.drawable.pull_28);
                break;
            case 29:
                imageView.setBackgroundResource(R.drawable.pull_29);
                break;
            case 30:
                imageView.setBackgroundResource(R.drawable.pull_30);
                break;
            case 31:
                imageView.setBackgroundResource(R.drawable.pull_31);
                break;
            case 32:
                imageView.setBackgroundResource(R.drawable.pull_32);
                break;
            case 33:
                imageView.setBackgroundResource(R.drawable.pull_33);
                break;
            case 34:
                imageView.setBackgroundResource(R.drawable.pull_34);
                break;
            case 35:
                imageView.setBackgroundResource(R.drawable.pull_35);
                break;
            case 36:
                imageView.setBackgroundResource(R.drawable.pull_36);
                break;
            case 37:
                imageView.setBackgroundResource(R.drawable.pull_37);
                break;
            case 38:
                imageView.setBackgroundResource(R.drawable.pull_38);
                break;
            case 39:
                imageView.setBackgroundResource(R.drawable.pull_39);
                break;
            case 40:
                imageView.setBackgroundResource(R.drawable.pull_40);
                break;
            case 41:
                imageView.setBackgroundResource(R.drawable.pull_41);
                break;
            case 42:
                imageView.setBackgroundResource(R.drawable.pull_42);
                break;
            case 43:
                imageView.setBackgroundResource(R.drawable.pull_43);
                break;
            case 44:
                imageView.setBackgroundResource(R.drawable.pull_44);
                break;
            case 45:
                imageView.setBackgroundResource(R.drawable.pull_45);
                break;
            case 46:
                imageView.setBackgroundResource(R.drawable.pull_46);
                break;
            case 47:
                imageView.setBackgroundResource(R.drawable.pull_47);
                break;
            case 48:
                imageView.setBackgroundResource(R.drawable.pull_48);
                break;
            case 49:
                imageView.setBackgroundResource(R.drawable.pull_49);
                break;
            case 50:
                imageView.setBackgroundResource(R.drawable.pull_50);
                break;
            default:
                break;
        }
    }

    @Override
    public void onReset() {
    }

    @Override
    public void onPrepare() {
        LogUtil.d("");
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onRelease() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(currentFrame, 25);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentFrame = (int) animation.getAnimatedValue();
                setCurrentFrame(currentFrame);
            }
        });

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                imageView.setBackgroundResource(R.drawable.refresh_anim_list);
                fowardAnim = ((AnimationDrawable) imageView.getBackground());
                fowardAnim.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.setDuration(300);
        valueAnimator.start();
    }

    @Override
    public void onPositionChange(float currentPercent) {
    }

    @Override
    public void setIsHeaderOrFooter(boolean isHead) {
        LogUtil.e("");
    }
}
