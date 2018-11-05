package com.doushi.library.util;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

import java.util.HashMap;
import java.util.IllegalFormatCodePointException;

/**
 * @author xuleyuan
 * @date 2017/11/2
 */
public class AnimatorUtils {

    public static final int LEFT_DIRECTION = 0;
    public static final int UP_DIRECTION = 1;
    public static final int RIGHT_DIRECTION = 2;
    public static final int DOWN_DIRECTION = 3;

    private View currentView;
    private int currentDelayTime = 0;
    private int totalDelayTime = 0;
    private HashMap<String, Animator> animatorList;

    public AnimatorUtils makeAnimation(View v) {
        this.currentView = v;
        animatorList = new HashMap<>();
        return this;
    }

    public AnimatorUtils setEndListener(final String tag, final CurrentEndListener currentEndListener) {
        Animator animator = animatorList.get(tag);
        if (animator == null) {
            throw new RuntimeException("CANT'T FIND THIS TAG ,  YOU SHOULD SET IT BEFORE IN ADD ANIMATOR  METHOD");
        }
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                currentEndListener.onEnd(tag);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        return this;
    }

    public AnimatorUtils addAlphaAnimation(final int time, final float fromAlpha, final float toAlpha, final Interpolator interpolator) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(fromAlpha, toAlpha)
                .setDuration(time);
        if (interpolator != null) {
            valueAnimator.setInterpolator(interpolator);
        }
        valueAnimator.setStartDelay(currentDelayTime);
        totalDelayTime = time;
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (currentView != null) {
                    currentView.setAlpha((Float) animation.getAnimatedValue());
                }
            }
        });
        animatorList.put(String.valueOf(time), valueAnimator);
        valueAnimator.start();
        return this;
    }

    public AnimatorUtils addAlphaAnimation(String tag, final Interpolator interpolator, final int time, final float fromAlpha, final float toAlpha) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f)
                .setDuration(time);
        if (interpolator != null) {
            valueAnimator.setInterpolator(interpolator);
        }
        valueAnimator.setStartDelay(currentDelayTime);
        totalDelayTime = time;
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (currentView != null) {
                    currentView.setAlpha((Float) animation.getAnimatedValue());
                }
            }
        });
        animatorList.put(String.valueOf(tag), valueAnimator);
        valueAnimator.start();
        return this;
    }

    public AnimatorUtils after() {
        currentDelayTime += totalDelayTime;
        totalDelayTime = 0;
        return this;
    }

    public AnimatorUtils addWidthChangeAnimator(int time, int currentWidth, int finalWidth, Interpolator interpolator) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(currentWidth, finalWidth)
                .setDuration(time);
        valueAnimator.setStartDelay(currentDelayTime);
        totalDelayTime = time;
        if (interpolator != null) {
            valueAnimator.setInterpolator(interpolator);
        }
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (currentView != null) {
                    ViewGroup.LayoutParams layoutParams = currentView.getLayoutParams();
                    layoutParams.width = (int) animation.getAnimatedValue();
                    currentView.setLayoutParams(layoutParams);
                }
            }
        });
        animatorList.put(String.valueOf(time), valueAnimator);
        valueAnimator.start();
        return this;
    }

    public AnimatorUtils addWidthChangeAnimator(String tag, int time, int currentWidth, int finalWidth, Interpolator interpolator) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(currentWidth, finalWidth)
                .setDuration(time);
        valueAnimator.setStartDelay(currentDelayTime);
        totalDelayTime = time;
        if (interpolator != null) {
            valueAnimator.setInterpolator(interpolator);
        }
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (currentView != null) {
                    ViewGroup.LayoutParams layoutParams = currentView.getLayoutParams();
                    layoutParams.width = (int) animation.getAnimatedValue();
                    currentView.setLayoutParams(layoutParams);
                }
            }
        });
        animatorList.put(String.valueOf(tag), valueAnimator);
        valueAnimator.start();
        return this;
    }

    public AnimatorUtils addHeightChangeAnimator(String tag, int time, int currentHeight, int finalHeight, Interpolator interpolator) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(currentHeight, finalHeight)
                .setDuration(time);
        valueAnimator.setStartDelay(currentDelayTime);
        totalDelayTime = time;
        if (interpolator != null) {
            valueAnimator.setInterpolator(interpolator);
        }
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (currentView != null) {
                    ViewGroup.LayoutParams layoutParams = currentView.getLayoutParams();
                    layoutParams.height = (int) animation.getAnimatedValue();
                    if (currentView != null) {
                        currentView.setLayoutParams(layoutParams);
                    }
                }
            }
        });
        animatorList.put(String.valueOf(tag), valueAnimator);
        valueAnimator.start();
        return this;
    }

    public AnimatorUtils addHeightChangeAnimator(int time, int currentHeight, int finalHeight, Interpolator interpolator) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(currentHeight, finalHeight)
                .setDuration(time);
        valueAnimator.setStartDelay(currentDelayTime);
        totalDelayTime = time;
        if (interpolator != null) {
            valueAnimator.setInterpolator(interpolator);
        }
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (currentView != null) {
                    ViewGroup.LayoutParams layoutParams = currentView.getLayoutParams();
                    layoutParams.height = (int) animation.getAnimatedValue();
                    currentView.setLayoutParams(layoutParams);
                }
            }
        });

        animatorList.put(String.valueOf(time), valueAnimator);
        valueAnimator.start();
        return this;
    }

    /**
     * 平移动画
     *
     * @param time         动画时间
     * @param Direction    方向 GO_LEFT,GO_UP,GO_RIGHT,GO_DOWN
     * @param length       移动距离
     * @param interpolator 插值器
     * @return
     */
    public AnimatorUtils addTranslate(int time, int Direction, int length, Interpolator interpolator) {

        PropertyValuesHolder animatorHolder;

        switch (Direction) {
            case LEFT_DIRECTION:
                animatorHolder = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, -length);
                break;

            case UP_DIRECTION:
                animatorHolder = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, -length);
                break;

            case RIGHT_DIRECTION:
                animatorHolder = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, length);
                break;

            case DOWN_DIRECTION:
                animatorHolder = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, length);
                break;

            default:
                throw new RuntimeException("YOU MUST SET A DIRECTION FROM LEFT_DIRECTION , RIGHT_DIRECTION , UP_DIRECTION , DOWN_DIRECTION");
        }

        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(currentView, animatorHolder);
        objectAnimator.setStartDelay(currentDelayTime);
        totalDelayTime = time;
        objectAnimator.setDuration(time);
        objectAnimator.start();
        return this;
    }

    /**
     * 平移动画
     *
     * @param time         动画时间
     * @param Direction    方向 GO_LEFT,GO_UP,GO_RIGHT,GO_DOWN
     * @param length       移动距离
     * @param interpolator 插值器
     * @return
     */
    public AnimatorUtils addTranslate(String tag, int time, int Direction, int length, Interpolator interpolator) {
        PropertyValuesHolder animatorHolder;
        switch (Direction) {
            case LEFT_DIRECTION:
                animatorHolder = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, -length);
                break;

            case UP_DIRECTION:
                animatorHolder = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, -length);
                break;

            case RIGHT_DIRECTION:
                animatorHolder = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, length);
                break;

            case DOWN_DIRECTION:
                animatorHolder = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, length);
                break;

            default:
                throw new RuntimeException("YOU MUST SET A DIRECTION FROM LEFT_DIRECTION , RIGHT_DIRECTION , UP_DIRECTION , DOWN_DIRECTION");
        }

        final ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(currentView, animatorHolder);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (objectAnimator.getTarget() == null || currentView == null) {
                    objectAnimator.cancel();
                }
            }
        });
        objectAnimator.setDuration(time);
        objectAnimator.setStartDelay(currentDelayTime);
        totalDelayTime = time;
        objectAnimator.start();
        animatorList.put(tag, objectAnimator);
        return this;
    }
}
