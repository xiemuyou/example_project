package com.doushi.test.myproject.base.component;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.blankj.utilcode.util.ObjectUtils;
import com.doushi.library.widgets.ToastUtils;
import com.doushi.library.widgets.statusbar.StatusBarCompat;
import com.doushi.test.myproject.Application;
import com.doushi.test.myproject.base.listener.PresenterListener;
import com.doushi.test.myproject.base.mvp.Presenter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Activity 基类<>理论上所有Activity继承BaseActivity</>
 *
 * @author xiemy
 * @date 2018/2/28.
 */
public abstract class BaseActivity extends BaseToolbarActivity implements PresenterListener {

    /**
     * 绑定视图
     */
    protected Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView(savedInstanceState));
        unbinder = ButterKnife.bind(this);
        initEnv();
    }

    public View getContentView(@Nullable Bundle savedInstanceState) {
        return View.inflate(this, getLayoutId(savedInstanceState), null);
    }

    /**
     * 获取页面布局
     *
     * @param savedInstanceState savedInstanceState
     * @return 页面布局资源
     */
    public abstract
    @LayoutRes
    int getLayoutId(Bundle savedInstanceState);

    /**
     * 初始化数据
     */
    public abstract void initEnv();

    private List<Presenter> presenterList;

    @Override
    public void addPresenter(Presenter presenter) {
        if (presenter == null) {
            return;
        }
        if (presenterList == null) {
            presenterList = new ArrayList<>();
        }
        presenterList.add(presenter);
    }

    public void setStatusLight(int color) {
        StatusBarCompat.setStatusBarColor(this, color);
    }

    public void showNotice(@StringRes int errorMsg) {
        showNotice(errorMsg, ToastUtils.ToastType.SUCCESS_TYPE);
    }

    public void showNotice(String errorMsg) {
        showNotice(errorMsg, ToastUtils.ToastType.SUCCESS_TYPE);
    }

    public void showNotice(@StringRes int errorMsg, ToastUtils.ToastType showType) {
        showNotice(getString(errorMsg), showType);
    }

    public void showNotice(String errorMsg, ToastUtils.ToastType showType) {
        ToastUtils.showToast(this, errorMsg, showType);
    }

    public void toPage(Class<?> clz) {
        toPage(clz, -1);
    }

    public void toPage(Class<?> clz, int flags) {
        toPage(clz, null, flags);
    }

    public void toPage(Class<?> clz, Bundle bundle) {
        toPage(clz, bundle, -1);
    }

    /**
     * 跳转页面
     *
     * @param clz    跳转对象页面
     * @param bundle intent 内容
     * @param flags  启动模式<>默认-1 为 FLAG_ACTIVITY_CLEAR_TOP</>
     */
    public void toPage(Class<?> clz, Bundle bundle, int flags) {
        Intent intent = new Intent(this, clz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        if (flags == -1) {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        } else {
            intent.setFlags(flags);
        }
        hideSoftKeyboard();
        startActivity(intent);
    }

    /**
     * 关闭
     */
    public void closePage() {
        hideSoftKeyboard();
        finish();
    }

    /**
     * 打开软键盘
     */
    public void openSoftInput(@NonNull View v) {
        WeakReference<View> weakReference = new WeakReference<>(v);
        v.requestFocus();
        v.setFocusable(true);
        v.setFocusableInTouchMode(true);
        InputMethodManager inputManager = (InputMethodManager) Application.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.showSoftInput(weakReference.get(), InputMethodManager.SHOW_IMPLICIT);
        }
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            WeakReference<View> weakReference = new WeakReference<>(view);
            InputMethodManager inputMethodManager = ((InputMethodManager) Application.getContext().getSystemService(Context.INPUT_METHOD_SERVICE));
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(weakReference.get().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public static class ActivityHandler extends Handler {

        WeakReference<BaseActivity> activityWeakReference;

        public ActivityHandler(BaseActivity baseActivity) {
            activityWeakReference = new WeakReference<>(baseActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (activityWeakReference == null || activityWeakReference.get() == null) {
                return;
            }
            activityWeakReference.get().handleMessage(msg);
        }
    }

    public void handleMessage(Message msg) {
    }

    /**
     * 状态栏去掉灰色透明阴影（部分room）
     */
    protected void setStatusTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                //该参数指布局能延伸到NavigationBar，我们场景中不应加这个参数
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } catch (Exception e) {
                //nothing
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        if (ObjectUtils.isNotEmpty(presenterList)) {
            for (Presenter presenter : presenterList) {
                presenter.detachView();
            }
        }
    }
}
