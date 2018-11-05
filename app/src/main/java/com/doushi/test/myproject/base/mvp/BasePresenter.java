package com.doushi.test.myproject.base.mvp;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.doushi.test.myproject.base.listener.PresenterListener;
import com.doushi.test.myproject.model.BaseApiResponse;
import com.doushi.test.myproject.global.DefaultValue;
import com.doushi.test.myproject.znet.InterfaceConfig;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * MVP 业务处理层基类，
 * 既能调用UI逻辑，
 * 又能请求数据，该层为纯Java类，
 * 不涉及任何Android API
 *
 * @author xiemy
 * @date 2018/2/28
 */
public abstract class BasePresenter<T extends BaseView> implements Presenter<T> {

    private static final String TAG = "BasePresenter";

    private T mMvpView;

    private CompositeDisposable compositeDisposable;

    public BasePresenter(@NonNull T view) {
        attachView(view);
        if (view instanceof PresenterListener) {
            ((PresenterListener) view).addPresenter(this);
        }
    }

    public void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    @Override
    public void attachView(T mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        mMvpView = null;
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public boolean isViewAttached(BaseApiResponse response) {
        if (isViewAttached()) {
            if (response != null) {
                if (response.getErrcode() == 0) {
                    return true;
                } else {
                    Log.e(TAG, String.valueOf(response.getErrcode()) + response.getErrmsg());
                    loadDataFail(response.getErrmsg());
                }
            } else {
                loadDataFail();
            }
        }
        return false;
    }

    public T getMvpView() {
        return mMvpView;
    }

    /**
     * 成功加载数据
     *
     * @param apiTag   api接口
     * @param modelRes 返回数据
     */
    public abstract void onLoadDataSuccess(InterfaceConfig.HttpHelperTag apiTag, BaseApiResponse modelRes);

    public void loadDataFail() {
        loadDataFail(DefaultValue.ERROR_MSG);
    }

    public void loadDataFail(String errorMsg) {
        loadDataFail(null, errorMsg);
    }

    public void loadDataFail(InterfaceConfig.HttpHelperTag apiTag, String errorMsg) {
        if (isViewAttached()) {
            errorMsg = !TextUtils.isEmpty(errorMsg) ? errorMsg : DefaultValue.ERROR_MSG;
            mMvpView.loadDataFail(errorMsg);
        }
    }
}
