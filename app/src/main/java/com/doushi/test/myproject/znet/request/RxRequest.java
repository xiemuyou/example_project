package com.doushi.test.myproject.znet.request;

import com.doushi.test.myproject.base.mvp.BasePresenter;
import com.doushi.test.myproject.base.mvp.BaseView;
import com.doushi.test.myproject.global.DefaultValue;
import com.doushi.test.myproject.model.BaseApiResponse;
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
 * @param <M>
 * @author xiemy
 * @date 2018/2/28
 */
public class RxRequest<M extends BaseApiResponse> {

    /**
     * 专用于有data返回的请求
     *
     * @param params      请求参数
     * @param api         请求接口
     * @param entityClass 返回对象类型
     * @param presenter   数据请求返回接口
     */
    public void doRequestData(Map<String, Object> params,
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
                        public void onNext(@NonNull M model) {
                            if (presenter != null && presenter.isViewAttached(model)) {
                                presenter.onLoadDataSuccess(api, model);
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            e.printStackTrace();            //请求失败
                            if (presenter != null && presenter.isViewAttached()) {
                                presenter.loadDataFail(api, DefaultValue.ERROR_MSG);
                            }
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else if (presenter != null && presenter.isViewAttached()) {
            presenter.loadDataFail(api, null);
        }
    }
}
