package com.doushi.test.myproject.base.component

import android.os.Bundle
import com.blankj.utilcode.util.ActivityUtils
import com.doushi.library.widgets.swipebacklayout.SlideBackHelper
import com.doushi.library.widgets.swipebacklayout.SlideConfig
import com.doushi.library.widgets.swipebacklayout.callbak.OnSlideListener
import com.doushi.library.widgets.swipebacklayout.widget.SlideBackLayout
import com.doushi.test.myproject.Application

abstract class BaseSwipeActivity : BaseActivity(), OnSlideListener {

    private var mSlideBackLayout: SlideBackLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSwipeBackFinish()
    }

    /**
     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
     */
    private fun initSwipeBackFinish() {
        try {
            if (ActivityUtils.getActivityList().size > 1) {
                mSlideBackLayout = SlideBackHelper.attach(
                        // 当前Activity
                        this, Application.getActivityHelper(),
                        // 参数的配置
                        SlideConfig.Builder()
                                // 屏幕是否旋转
                                .rotateScreen(true)
                                // 是否侧滑
                                .edgeOnly(false)
                                // 是否禁止侧滑
                                .lock(false)
                                // 侧滑的响应阈值，0~1，对应屏幕宽度*percent
                                .edgePercent(0.1f)
                                // 关闭页面的阈值，0~1，对应屏幕宽度*percent
                                .slideOutPercent(0.5f).create(),
                        // 滑动的监听
                        this)
            }
        } catch (ex: Throwable) {
        }
    }


    /**
     * 开启全局侧滑(当前边缘侧滑)
     */
    fun enableEdgeSlide(isEnable: Boolean) {
        mSlideBackLayout?.edgeOnly(isEnable)
    }

    /**
     * 是否禁止侧滑返回
     */
    fun lockSwipe(isEnable: Boolean) {
        mSlideBackLayout?.lock(isEnable)
    }

    override fun onSlide(percent: Float) {
    }

    override fun onOpen() {
    }

    override fun onClose(finishActivity: Boolean?) {
    }

    override fun onPointerCaptureChanged(hasCapture: Boolean) {
        super.onPointerCaptureChanged(hasCapture)
    }
}