package com.library.widgets.dialog;

import android.content.Context;
import androidx.annotation.ColorRes;
import androidx.annotation.StringRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.library.R;
import com.library.util.LogUtil;

/**
 * 全局默认Dialog
 *
 * @author xiemy
 * @date 2017/4/28.
 */
public class DefaultPromptBox extends BaseDialog {

    static final String TAG = "DefaultPrompt";

    private boolean determineFlag;

    /**
     * 默认提示 Dialog
     *
     * @param context 上下文对象
     */
    public DefaultPromptBox(Context context) {
        super(context, Gravity.CENTER);
    }

    private void setDetermineFlag(boolean determineFlag) {
        this.determineFlag = determineFlag;
    }

    public boolean getDetermineFlag() {
        return determineFlag;
    }

    @Override
    public View getView() {
        return View.inflate(getContext(), R.layout.view_default_prompt, null);
    }

    public static class Builder {

        /**
         * 上下文对象
         */
        private Context context;
        /**
         * 提示内容
         */
        private TextView tvPromptContent;
        /**
         * 提示标题
         */
        private TextView tvPromptTitle;
        /**
         * 确认按钮
         */
        private TextView tvPromptDetermine;
        /**
         * 取消按钮
         */
        private TextView tvPromptCancel;
        /**
         * 确认按钮颜色
         */
        private
        @ColorRes
        int promptDetermineColor;
        /**
         * 取消按钮颜色
         */
        private
        @ColorRes
        int promptCancelColor;
        /**
         * 提示监听
         */
        private View.OnClickListener determineListener;
        /**
         * 取消监听
         */
        private View.OnClickListener cancelListener;
        /**
         * 提示内容
         */
        private String content;
        /**
         * 提示title
         */
        private String title;
        /**
         * 确定按钮文本
         */
        private String determine;
        /**
         * 取消按钮文本
         */
        private String cancel;
        /**
         * 显示取消按钮
         */
        private boolean showCancel = true;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setDetermineListener(View.OnClickListener determineListener) {
            this.determineListener = determineListener;
            return this;
        }

        public Builder setCancelListener(View.OnClickListener cancelListener) {
            this.cancelListener = cancelListener;
            return this;
        }

        public Builder setPromptContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setPromptContent(@StringRes int content) {
            this.content = context.getString(content);
            return this;
        }

        public Builder setPromptTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setPromptTitle(@StringRes int title) {
            this.title = context.getString(title);
            return this;
        }

        public Builder setPromptDetermine(String determine) {
            this.determine = determine;
            return this;
        }

        public Builder setPromptDetermine(@StringRes int determine) {
            this.determine = context.getString(determine);
            return this;
        }

        public Builder setPromptCancel(String cancel) {
            this.cancel = cancel;
            return this;
        }

        public Builder setPromptCancel(@StringRes int cancel) {
            this.cancel = context.getString(cancel);
            return this;
        }

        public Builder setPromptDetermineColor(@ColorRes int determineColor) {
            this.promptDetermineColor = determineColor;
            return this;
        }

        public Builder setPromptCancelColor(@ColorRes int cancelColor) {
            this.promptCancelColor = cancelColor;
            return this;
        }

        /**
         * 设置是否显示取消按钮
         *
         * @param showCancel 是否显示取消按钮
         */
        public Builder setCancelButton(boolean showCancel) {
            this.showCancel = showCancel;
            return this;
        }

        public DefaultPromptBox create() {
            return create(true);
        }

        public DefaultPromptBox create(boolean flag) {
            final DefaultPromptBox promptBox = new DefaultPromptBox(context);
            View dVIew = promptBox.getRootView();
            tvPromptTitle = dVIew.findViewById(R.id.tvPromptTitle);
            tvPromptContent = dVIew.findViewById(R.id.tvPromptContent);
            tvPromptDetermine = dVIew.findViewById(R.id.tvPromptDetermine);
            tvPromptDetermine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (determineListener != null) {
                        determineListener.onClick(v);
                        promptBox.setDetermineFlag(true);
                    }
                    promptBox.dismiss();
                }
            });

            tvPromptCancel = dVIew.findViewById(R.id.tvPromptCancel);
            if (showCancel) {
                tvPromptCancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (cancelListener != null) {
                            cancelListener.onClick(v);
                        }
                        promptBox.dismiss();
                    }
                });
            } else {
                tvPromptCancel.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(content)) {
                tvPromptContent.setText(content);
            }

            if (!TextUtils.isEmpty(title)) {
                tvPromptTitle.setText(title);
            }

            if (!TextUtils.isEmpty(determine)) {
                tvPromptDetermine.setText(determine);
            }

            if (!TextUtils.isEmpty(cancel)) {
                tvPromptCancel.setText(cancel);
            }

            if (promptDetermineColor != 0) {
                tvPromptDetermine.setTextColor(context.getResources().getColor(promptDetermineColor));
            }

            if (promptCancelColor != 0) {
                tvPromptCancel.setTextColor(context.getResources().getColor(promptCancelColor));
            }

            LogUtil.d(TAG, "点击关闭" + (!showCancel || flag));

            promptBox.setCancelable(!showCancel || flag);
            ViewGroup.LayoutParams layoutParams = dVIew.getLayoutParams();
            layoutParams.width = ScreenUtils.getScreenWidth() - SizeUtils.dp2px(90);
            layoutParams.height = (int) (ScreenUtils.getScreenHeight() * 0.25);
            dVIew.setLayoutParams(layoutParams);
            return promptBox;
        }

        public DefaultPromptBox show() {
            return show(true);
        }

        public DefaultPromptBox show(boolean flag) {
            DefaultPromptBox promptBox = create(flag);
            promptBox.show();
            return promptBox;
        }
    }
}
