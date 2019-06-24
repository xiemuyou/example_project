package com.doushi.test.myproject.ui.main.video.widget

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.annotation.IntDef
import com.doushi.test.myproject.R

/**
 * @author xiemy2
 * @date 2019/6/24
 */
class VideoPlayButton : FrameLayout {

    private var ibStartOrPause: ImageButton? = null
    private var pbVideoLoading: ProgressBar? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    private fun init(context: Context) {
        val view = View.inflate(context, R.layout.view_video_buttom, this)
        ibStartOrPause = view.findViewById<ImageButton>(R.id.start)
        pbVideoLoading = view.findViewById<ProgressBar>(R.id.loading)
        setVideoState()
    }

    fun setVideoState(@VideoState state: Int = LOADING) {
        if (videoState == state) {
            return
        }
        when (state) {
            PLAY -> {
                pbVideoLoading?.visibility = View.GONE
                ibStartOrPause?.setBackgroundResource(R.drawable.video_play_to_pause_anim_list)
                val startAnim = ibStartOrPause?.background
                if (startAnim is AnimationDrawable) {
                    // 3. 启动动画
                    startAnim.start()
                    ibStartOrPause?.visibility = View.VISIBLE
                }
            }
            PAUSE -> {
                pbVideoLoading?.visibility = View.GONE
                ibStartOrPause?.setBackgroundResource(R.drawable.video_pause_to_play_anim_list)
                val startAnim = ibStartOrPause?.background
                if (startAnim is AnimationDrawable) {
                    startAnim.start()
                    ibStartOrPause?.visibility = View.VISIBLE
                }
            }
            LOADING -> {
                pbVideoLoading?.visibility = View.VISIBLE
                ibStartOrPause?.visibility = View.GONE
            }
            else -> {
                pbVideoLoading?.visibility = View.GONE
                ibStartOrPause?.visibility = View.GONE
            }
        }
        videoState = state
    }

    private var videoState: Int = LOADING

    companion object {
        internal const val PLAYING = -1
        internal const val PLAY = 0
        internal const val PAUSE = 1
        internal const val LOADING = 2
    }

    //视频状态,播放,暂停,加载
    @IntDef(PLAY, PAUSE, LOADING)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    internal annotation class VideoState
}
