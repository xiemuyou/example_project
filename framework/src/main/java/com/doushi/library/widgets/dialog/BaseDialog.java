package com.doushi.library.widgets.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.doushi.library.R;

import java.lang.ref.WeakReference;

/**
 * @author zhangw
 * @date 2017/10/18.
 */
public abstract class BaseDialog extends Dialog {

    private View dView;
    /**
     * 编辑资料页面需要点击阴影部分dialog消失
     * 提供外部调用root布局设置点击事件
     */
    private FrameLayout mFlRootView;

    public BaseDialog(Context context, int gravity) {
        this(context, R.style.CustomerDialog, gravity);
    }

    public View getRootView() {
        return dView;
    }

    private int mGravity;

    public BaseDialog(Context context, int style, int gravity) {
        super(context, style);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.setContentView(R.layout.dialog_base);
        this.mGravity = gravity;

        dView = getView();
        setContentView(dView);

        Window dialogWindow = getWindow();
        if (null != dialogWindow) {
            dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT);
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.windowAnimations = R.style.no_dialog_exit;
            dialogWindow.setAttributes(lp);
        }
    }

    /**
     * 获取页面布局
     *
     * @return 页面布局资源
     */

    public abstract View getView();

    public View getFlRootView() {
        return mFlRootView;
    }

    @Override
    public void setContentView(int layoutId) {
        setContentView(View.inflate(getContext(), layoutId, null));
    }

    @Override
    public void setContentView(View view) {
        mFlRootView = (FrameLayout) findViewById(R.id.fLRootView);
        if (mFlRootView == null || view == null) {
            return;
        }

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        if (mGravity != 0) {
            layoutParams.gravity = mGravity;
        }
        mFlRootView.addView(view, layoutParams);
        mFlRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mCancelable) {
                    showDialogWithAnim(false);
                }
            }
        });
    }

    private boolean mCancelable = true;

    @Override
    public void setCancelable(boolean flag) {
        super.setCancelable(flag);
        mCancelable = flag;
    }

    private Animation llBottomIn;
    private Animation llBottomOut;

    private void initAnimatorIn() {
        if (llBottomIn == null) {
            llBottomIn = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_bottom);
        }
        dView.startAnimation(llBottomIn);
    }

    private void initAnimatorOut() {
        if (llBottomOut == null) {
            llBottomOut = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_bottom);
        }
        dView.startAnimation(llBottomOut);
    }

    public void showDialogWithAnim(boolean flag) {
        if (flag) {
            initAnimatorIn();
            dView.setVisibility(View.VISIBLE);
            mHandler.sendEmptyMessage(SHOW_DIALOG);
        } else {
            initAnimatorOut();
            dView.setVisibility(View.INVISIBLE);
            mHandler.sendEmptyMessageDelayed(DISMISS_DIALOG, show_delayed);
        }
    }

    @Override
    public void dismiss() {
        if (mHandler != null) {
            mHandler.removeMessages(SHOW_DIALOG);
            mHandler.removeMessages(DISMISS_DIALOG);
        }
        super.dismiss();
    }

    private final static int SHOW_DIALOG = 0xFF;
    public final static int DISMISS_DIALOG = 0xFE;
    private final static int show_delayed = 300;

    /**
     * 实例化一个MyHandler对象
     */
    private DialogHandler mHandler = new DialogHandler(this);

    public DialogHandler getHandler() {
        if (mHandler == null) {
            mHandler = new DialogHandler(this);
        }
        return mHandler;
    }

    public static class DialogHandler extends Handler {

        WeakReference<BaseDialog> dialogWeakReference;
        BaseDialog dialog;

        DialogHandler(BaseDialog dialog) {
            dialogWeakReference = new WeakReference<>(dialog);
            this.dialog = dialogWeakReference.get();
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_DIALOG:
                    dialog.show();
                    break;

                case DISMISS_DIALOG:
                    dialog.dismiss();
                    break;

                default:
                    break;
            }
        }
    }
}