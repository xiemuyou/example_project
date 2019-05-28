package com.doushi.test.myproject.znet.request;

import com.doushi.test.myproject.base.mvp.BasePresenter;
import com.doushi.test.myproject.model.base.BaseApiResponse;
import com.doushi.test.myproject.model.base.ErrorMsg;
import com.doushi.test.myproject.znet.InterfaceConfig;
import com.doushi.test.myproject.znet.rx.RxAllService;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * RxJava数据请求实现类
 *
 * @author xiemy
 * @date 2018/2/28
 */
public class RxRequestCallback {

    /**
     * 专用于有data返回的请求
     * <p>
     * 当前方法已经废弃,可以用以下方法代替
     * new RxRequestCallback().doRequestData(...)
     * </P>
     *
     * @param params      请求参数
     * @param api         请求接口
     * @param presenter   数据请求返回接口
     */
    public void doRequestString(final Map<String, Object> params, final InterfaceConfig.HttpHelperTag api, final BasePresenter presenter) {
        Observable<String> videoSortObservable = RxAllService.sharedInstance().serviceQueryByObj(params, api);
        if (videoSortObservable != null) {
            videoSortObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {

                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            if (presenter != null) {
                                presenter.addDisposable(d);
                            }
                        }

                        @Override
                        public void onNext(@NonNull String res) {
                            if (presenter != null && presenter.isViewAttached(res, api, params)) {
                                presenter.onLoadDataSuccess(api, res, params);
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            e.printStackTrace();            //请求失败
                            if (presenter != null && presenter.isViewAttached()) {
                                presenter.loadDataFail(api, ErrorMsg.NET_ERROR_CODE, params, "");
                            }
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        } else if (presenter != null && presenter.isViewAttached()) {
            presenter.loadDataFail(api, ErrorMsg.NET_ERROR_CODE, params, "");
        }
    }

    /**
     * 专用于有data返回的请求
     * <p>
     * 当前方法已经废弃,可以用以下方法代替
     * new RxRequestCallback().doRequestData(...)
     * </P>
     *
     * @param params      请求参数
     * @param api         请求接口
     * @param entityClass 返回对象类型
     * @param presenter   数据请求返回接口
     */
    public <M extends BaseApiResponse> void doRequestData(final Map<String, Object> params,
                                                          final InterfaceConfig.HttpHelperTag api,
                                                          final Class<M> entityClass,
                                                          final BasePresenter presenter) {
        Observable<M> videoSortObservable =
                RxAllService.sharedInstance().serviceQueryByObj(params, api, entityClass);
        if (videoSortObservable != null) {
            videoSortObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<M>() {

                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            if (presenter != null) {
                                presenter.addDisposable(d);
                            }
                        }

                        @Override
                        public void onNext(@NonNull M res) {
                            if (presenter != null && presenter.isViewAttached(res, api, params)) {
                                presenter.onLoadDataSuccess(api, res, params);
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            e.printStackTrace();            //请求失败
                            if (presenter != null && presenter.isViewAttached()) {
                                presenter.loadDataFail(api, ErrorMsg.NET_ERROR_CODE, params, "");
                            }
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        } else if (presenter != null && presenter.isViewAttached()) {
            presenter.loadDataFail(api, ErrorMsg.NET_ERROR_CODE, params, "");
        }
    }
}
