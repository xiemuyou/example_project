package com.doushi.test.myproject.base.mvp;

import androidx.annotation.NonNull;
import android.text.TextUtils;

import com.doushi.test.myproject.base.listener.PresenterListener;
import com.doushi.test.myproject.model.base.BaseApiResponse;
import com.doushi.test.myproject.global.DefaultValue;
import com.doushi.test.myproject.model.base.ErrorMsg;
import com.doushi.test.myproject.znet.InterfaceConfig;

import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * MVP 业务处理层基类，
 * 请求数据，调用UI逻辑
 *
 * @author xiemy
 * @date 2018/2/28
 */
public abstract class BasePresenter<V extends BaseView> implements Presenter<V> {

    public static final String TAG = BasePresenter.class.getSimpleName();

    private V mMvpView;

    private CompositeDisposable compositeDisposable;

    /**
     * 不需要view的构造方法
     */
    public BasePresenter() {
    }

    public BasePresenter(@NonNull V view) {
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
    public void attachView(V mvpView) {
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

    public boolean isViewAttached(String res, InterfaceConfig.HttpHelperTag tag, Map<String, Object> params) {
        if (isViewAttached()) {
            if (!TextUtils.isEmpty(res)) {
                return true;
            } else {
                loadDataFail(tag, ErrorMsg.DATA_ERROR_CODE, params, DefaultValue.ERROR_MSG);
            }
        }
        return false;
    }

    public boolean isViewAttached(BaseApiResponse res, InterfaceConfig.HttpHelperTag tag, Map<String, Object> params) {
        if (isViewAttached()) {
            if (res != null) {
                if (res.isSuccess()) {
                    return true;
                } else {
                    loadDataFail(tag, ErrorMsg.DATA_ERROR_CODE, params, DefaultValue.ERROR_MSG);
                }
            } else {
                loadDataFail(tag, ErrorMsg.DATA_ERROR_CODE, params, DefaultValue.ERROR_MSG);
            }
        }
        return false;
    }

    public V getMvpView() {
        return mMvpView;
    }

    /**
     * 成功加载数据
     *
     * @param apiTag   api接口
     * @param modelRes 返回数据
     * @param params   请求参数
     */
    public abstract void onLoadDataSuccess(InterfaceConfig.HttpHelperTag apiTag, BaseApiResponse modelRes, Map<String, Object> params);

    public void onLoadDataSuccess(InterfaceConfig.HttpHelperTag apiTag, String res, Map<String, Object> params) {

    }

    public void loadDataFail(@NonNull InterfaceConfig.HttpHelperTag apiTag, int errorCode, Map<String, Object> params, String errorMsg) {
        if (isViewAttached()) {
            errorMsg = !TextUtils.isEmpty(errorMsg) ? errorMsg : DefaultValue.ERROR_MSG;
            mMvpView.loadDataFail(apiTag, errorMsg);
        }
    }
}
