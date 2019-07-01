package com.library.widgets.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.library.R;

/**
 * @author livvy
 * @date 18-5-17
 */

public abstract class BaseDialogFragment extends DialogFragment {

    private float dimAmount = 0.60f;
    private int gravity = Gravity.CENTER;
    private OnDialogDismissListener dismissListener;
    private Context context;

    public BaseDialogFragment() {
        this(0.60f, Gravity.CENTER);
    }

    public BaseDialogFragment(float dimAmount, int gravity) {
        this.dimAmount = dimAmount;
        this.gravity = gravity;
    }

    public abstract void showDialog();

    @Override
    public void onStart() {
        super.onStart();
        removeDiver();
        initWindows();
    }

    protected void initWindows() {
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams windowParams = window.getAttributes();
            windowParams.dimAmount = dimAmount;
            windowParams.gravity = gravity;
            windowParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            windowParams.windowAnimations = R.style.dialog_fragment_center_anim;
            window.setAttributes(windowParams);
            window.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.shape_transparent));
        }
    }

    private void removeDiver() {
        try {
            int dividerId = getResources().getIdentifier("android:id/titleDivider", null, null);
            View divider = getDialog().findViewById(dividerId);
            divider.setBackgroundColor(Color.TRANSPARENT);
        } catch (Exception e) {
            //上面的代码，是用来去除Holo主题的蓝色线条
        }
    }

    /**
     * dialog 关闭监听
     */
    public interface OnDialogDismissListener {

        /**
         * dialog 关闭
         */
        void onDismiss();
    }

    public void setDialogDismissListener(OnDialogDismissListener dismissListener) {
        this.dismissListener = dismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (dismissListener != null) {
            dismissListener.onDismiss();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
