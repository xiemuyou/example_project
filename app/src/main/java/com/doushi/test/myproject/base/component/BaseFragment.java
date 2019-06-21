package com.doushi.test.myproject.base.component;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ObjectUtils;
import com.doushi.library.widgets.ToastUtils;
import com.doushi.test.myproject.base.listener.PresenterListener;
import com.doushi.test.myproject.base.mvp.Presenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Fragment 基类<>理论上所有Fragment继承BaseFragment</>
 *
 * @author xiemy
 * @date 2018/2/28.
 */
public abstract class BaseFragment extends SupportFragment implements PresenterListener {

    /**
     * 视图绑定
     */
    private Unbinder unbinder;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(getLayoutId(savedInstanceState), container, false);
        unbinder = ButterKnife.bind(this, view);
        initEnv();
        return view;
    }

    /**
     * 初始化数据
     */
    public abstract void initEnv();

    /**
     * 获取布局xmlID
     *
     * @param savedInstanceState 用于保存状态
     * @return layout 布局地址
     */
    public abstract @LayoutRes
    int getLayoutId(Bundle savedInstanceState);

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

    /**
     * 跳转页面
     *
     * @param clz 跳转对象页面
     */
    public void toPage(Class<?> clz) {
        toPage(clz, null);
    }

    public void toPage(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(_mActivity, clz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void toPageForResult(Class<?> clz, int requestCode) {
        toPageForResult(clz, null, requestCode, -1);
    }

    public void toPageForResult(Class<?> clz, int requestCode, int flags) {
        toPageForResult(clz, null, requestCode, flags);
    }

    public void toPageForResult(Class<?> clz, Bundle extras, int requestCode) {
        toPageForResult(clz, extras, requestCode, -1);
    }

    public void toPageForResult(Class<?> clz, Bundle extras, int requestCode, int flags) {
        Intent intent = new Intent(_mActivity, clz);
        if (extras != null) {
            intent.putExtras(extras);
        }
        if (flags == -1) {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        } else {
            intent.setFlags(flags);
        }
        startActivityForResult(intent, requestCode);
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
        ToastUtils.showToast(_mActivity, errorMsg, showType);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //取消,释放Toast
        ToastUtils.cancelToast();
        //解除绑定的视图
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
