package com.library.widgets;

import android.content.Context;
import android.os.Looper;
import androidx.annotation.StringRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.library.R;
import com.library.thread.AbstractSafeThread;
import com.library.thread.ThreadPool;
import com.library.util.ViewUtil;
import com.library.widgets.textstyleplus.StyleBuilder;

/**
 * @author zhangw
 */
public class ToastUtils {
    private static Toast toast;

    /**
     * 成功/失败
     */
    public enum ToastType {
        /**
         * 成功类型
         */
        SUCCESS_TYPE,
        /**
         * 失败类型
         */
        ERROR_TYPE
    }

    /**
     * @param context  上下文
     * @param info     字符串
     * @param showType 类型
     * @param value    增加值提示
     */
    public static void showToast(Context context, String info, ToastType showType, ValueAddModel value, int duration) {
        if (context == null || TextUtils.isEmpty(info)) {
            return;
        }
        LayoutInflater inflater = LayoutInflater.from(context);
        View customerToast = inflater.inflate(R.layout.customertoast, null);
        TextView tTitle = customerToast.findViewById(R.id.tvToastTitle);
        TextView tMessage = customerToast.findViewById(R.id.message);

        if (showType == ToastType.SUCCESS_TYPE) {
            //默认成功提示
            ViewUtil.setDrawable(context, tTitle, R.drawable.bs_dialog_sucess, ViewUtil.DrawablePosition.LEFT);
            ViewUtil.setTextSizeForDip(tTitle, 20f);
        } else { //失败提示
            ViewUtil.setTextSizeForDip(tTitle, 14f);
            ViewUtil.setDrawable(context, tTitle, 0, null);
        }

        if (toast == null) {
            tTitle.setText(info);
            toast = new Toast(context);
            toast.setView(customerToast);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            tTitle.setText(info);
            toast.setView(customerToast);
        }
        toast.setDuration(duration);
        if (value == null || !value.hasValue()) {
            tMessage.setVisibility(View.GONE);
        } else {
            tMessage.setVisibility(View.VISIBLE);
            createMessage(context, value, tMessage);
        }
        toast.show();
    }

    /**
     * 增值提示
     *
     * @param context  上下文
     * @param value    增值信息
     * @param tMessage msg
     */
    public static void createMessage(Context context, ValueAddModel value, TextView tMessage) {
        if (context == null) {
            return;
        }
        StyleBuilder sb = new StyleBuilder();
        //亲密度
        if (value.getIntimacy() > 0) {
            sb.text(context.getString(R.string.intimacy))
                    .addTextStyle("+" + value.getIntimacy())
                    .textColor(context.getResources().getColor(R.color.color_FF4444))
                    .commit();
        }
        //成长值
        if (value.getGrowvalue() > 0) {
            if (value.getIntimacy() > 0) {
                sb.text("  / ");
            }
            sb.text(context.getString(R.string.growvalue))
                    .addTextStyle("+" + value.getGrowvalue())
                    .textColor(context.getResources().getColor(R.color.color_FFA800))
                    .commit();
        }
        //积分
        if (value.getGlod() > 0) {
            if (value.getIntimacy() > 0 || value.getGrowvalue() > 0) {
                sb.text("  / ");
            }
            sb.text(context.getString(R.string.gold))
                    .addTextStyle("+" + value.getGlod())
                    .textColor(context.getResources().getColor(R.color.color_FFA800))
                    .commit();
        }
        sb.show(tMessage);
    }

    /**
     * 使用字符串资源创建Toast
     *
     * @param context 上下文
     * @param info    字符串资源id
     * @param value   增值
     */
    public static void showToast(Context context, int info, ValueAddModel value) {
        if (context == null) {
            return;
        }
        showToast(context, context.getString(info), ToastType.SUCCESS_TYPE, value, Toast.LENGTH_SHORT);
    }

    public static void showToast(Context context, int info, ValueAddModel value, int duration) {
        if (context == null) {
            return;
        }
        showToast(context, context.getString(info), ToastType.SUCCESS_TYPE, value, duration);
    }

    /**
     * 使用字符串创建Toast
     *
     * @param context 上下文
     * @param info    字符串
     * @param value   增值
     */
    public static void showToast(Context context, String info, ValueAddModel value) {
        if (context == null) {
            return;
        }
        showToast(context, info, ToastType.SUCCESS_TYPE, value, Toast.LENGTH_SHORT);
    }

    public static void showToast(Context context, String info, ValueAddModel value, int duration) {
        if (context == null) {
            return;
        }
        showToast(context, info, ToastType.SUCCESS_TYPE, value, duration);
    }

    /**
     * 使用字符串创建Toast
     *
     * @param context  上下文
     * @param info     字符串
     * @param showType 成果失败类型
     */
    public static void showToast(Context context, String info, ToastType showType) {
        if (context == null) {
            return;
        }
        showToast(context, info, showType, null, Toast.LENGTH_SHORT);
    }

    public static void showToast(Context context, String info, ToastType showType, int duration) {
        if (context == null) {
            return;
        }
        showToast(context, info, showType, null, duration);
    }

    /**
     * 使用字符串资源创建Toast
     *
     * @param context  上下文
     * @param info     字符串资源id
     * @param showType 成果失败类型
     */
    public static void showToast(Context context, int info, ToastType showType) {
        if (context == null) {
            return;
        }
        showToast(context, context.getString(info), showType, null, Toast.LENGTH_SHORT);
    }

    public static void showToast(Context context, int info, ToastType showType, int duration) {
        if (context == null) {
            return;
        }
        showToast(context, context.getString(info), showType, null, duration);
    }


    /**
     * 使用字符串创建Toast
     *
     * @param context 上下文对象
     * @param info    字符串
     */
    public static void showToast(Context context, String info) {
        if (context == null) {
            return;
        }
        showToast(context, info, ToastType.SUCCESS_TYPE, null, Toast.LENGTH_SHORT);
    }

    public static void showToast(Context context, String info, int duration) {
        if (context == null) {
            return;
        }
        showToast(context, info, ToastType.SUCCESS_TYPE, null, duration);
    }

    /**
     * 使用字符串资源创建Toast
     *
     * @param context 上下文对象
     * @param info    字符串资源ID
     */
    public static void showToast(Context context, @StringRes int info) {
        if (context == null) {
            return;
        }
        showToast(context, context.getString(info), ToastType.SUCCESS_TYPE, null, Toast.LENGTH_SHORT);
    }

    public static void showToast(Context context, @StringRes int info, int duration) {
        if (context == null) {
            return;
        }
        showToast(context, context.getString(info), ToastType.SUCCESS_TYPE, null, duration);
    }

    /**
     * 清除Toast
     */
    public static void cancelToast() {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
    }

    /**
     * 第三方库toast
     */
    public static void showThToast(final Context context, final MessageModel messageModel) {
        if (messageModel != null) {
            ThreadPool.execute(new AbstractSafeThread() {
                @Override
                public void deal() {
                    Looper.prepare();
                    ToastUtils.showToast(context, messageModel.toString(), ToastType.ERROR_TYPE);
                    Looper.loop();
                }
            });
        }
    }
}
