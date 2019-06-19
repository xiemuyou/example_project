package com.doushi.test.myproject.znet.rx

import android.annotation.SuppressLint
import com.doushi.library.global.FConstants
import com.doushi.test.myproject.base.mvp.BasePresenter
import com.doushi.test.myproject.model.base.BaseApiResponse
import com.doushi.test.myproject.model.base.ErrorMsg
import com.doushi.test.myproject.znet.InterfaceConfig

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.HashMap

/**
 * RxJava数据请求实现类
 *
 * @author xiemy
 * @date 2018/2/28
 */
class RxRequestCallback {

    /**
     * 专用于有data返回的请求
     *
     *
     * 当前方法已经废弃,可以用以下方法代替
     * new RxRequestCallback().doRequestData(...)
     *
     *
     * @param params      请求参数
     * @param api         请求接口
     * @param presenter   数据请求返回接口
     */
//    fun doRequestString(params: Map<String, Any>, api: InterfaceConfig.HttpHelperTag, presenter: BasePresenter<*>?) {
//        methodRequest(params, api, String::class.java).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(object : Observer<String> {
//
//                    override fun onSubscribe(@NonNull d: Disposable) {
//                        presenter?.addDisposable(d)
//                    }
//
//                    override fun onNext(@NonNull res: String) {
//                        if (presenter != null && presenter.isViewAttached(res, api, params)) {
//                            presenter.onLoadDataSuccess(api, res, params)
//                        }
//                    }
//
//                    override fun onError(@NonNull e: Throwable) {
//                        e.printStackTrace()            //请求失败
//                        if (presenter != null && presenter.isViewAttached) {
//                            presenter.loadDataFail(api, ErrorMsg.NET_ERROR_CODE, params, "")
//                        }
//                    }
//
//                    override fun onComplete() {}
//                })
//    }

    /**
     * 专用于有data返回的请求
     *
     *
     * 当前方法已经废弃,可以用以下方法代替
     * new RxRequestCallback().doRequestData(...)
     *
     *
     * @param params      请求参数
     * @param api         请求接口
     * @param entityClass 返回对象类型
     * @param presenter   数据请求返回接口
     */
//    fun <M : BaseApiResponse<*>> doRequestData(params: Map<String, Any>, api: InterfaceConfig.HttpHelperTag,
//                                               entityClass: Class<M>, presenter: BasePresenter<*>?) {
//        methodRequest(params, api, entityClass).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(object : Observer<M> {
//
//                    override fun onSubscribe(@NonNull d: Disposable) {
//                        presenter?.addDisposable(d)
//                    }
//
//                    override fun onNext(@NonNull res: M) {
//                        if (presenter != null && presenter.isViewAttached(res, api, params)) {
//                            presenter.onLoadDataSuccess(api, res, params)
//                        }
//                    }
//
//                    override fun onError(@NonNull e: Throwable) {
//                        e.printStackTrace()            //请求失败
//                        if (presenter != null && presenter.isViewAttached) {
//                            presenter.loadDataFail(api, ErrorMsg.NET_ERROR_CODE, params, "")
//                        }
//                    }
//
//                    override fun onComplete() {}
//                })
//    }

    fun request(params: MutableMap<String, Any>? = null, api: InterfaceConfig.HttpHelperTag, presenter: BasePresenter<*>?) {
        request(params, api, String::class.java, presenter)
    }

    /**
     * 专用于有data返回的请求
     *
     * @param params      请求参数
     * @param api         请求接口
     * @param entityClass 返回对象类型
     * @param presenter   数据请求返回接口
     */
    fun <M : Any> request(params: MutableMap<String, Any>? = null, api: InterfaceConfig.HttpHelperTag, entityClass: Class<M>?, presenter: BasePresenter<*>?) {
        methodRequest(params, api, entityClass).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<M> {

                    override fun onSubscribe(@NonNull d: Disposable) {
                        presenter?.addDisposable(d)
                    }

                    override fun onNext(@NonNull res: M) {
                        if (presenter != null) {
                            if (res is BaseApiResponse<*>) {
                                if (presenter.isViewAttached(res, api, params)) {
                                    presenter.onLoadDataSuccess(api, res, params)
                                }
                            } else if (res is String) {
                                presenter.onLoadDataSuccess(api, res.toString(), params)
                            }
                        }
                    }

                    override fun onError(@NonNull e: Throwable) {
                        e.printStackTrace()            //请求失败
                        if (presenter != null && presenter.isViewAttached) {
                            presenter.loadDataFail(api, ErrorMsg.NET_ERROR_CODE, params, "")
                        }
                    }

                    override fun onComplete() {}
                })
    }

    /**
     * 专用于有data返回的请求
     *
     * @param params 请求参数
     * @param api    请求接口
     */
    @SuppressLint("CheckResult")
    private fun <T> methodRequest(params: MutableMap<String, Any>? = null, api: InterfaceConfig.HttpHelperTag, entityClass: Class<T>?): Observable<T> {
        return if (InterfaceConfig.METHOD_GET == api.method) {
            ServerApi.postData<T>(api.apiUrl, addUserParams(params), entityClass, api.config)
        } else {
            ServerApi.getData<T>(api.apiUrl, addUserParams(params), entityClass, api.config)
        }
    }

    /**
     * 添加用户参数
     *
     * @param map 源参数数据
     * @return 添加了用户参数的请求数据
     */
    @Suppress("NAME_SHADOWING")
    private fun addUserParams(map: MutableMap<String, Any>?): Map<String, Any> {
        var map = map
        if (map == null) {
            map = HashMap()
        }

        /*
          用户第一次使用,不存在游客ID跟用户ID,默认填 0
          默认填 0 的情况正常逻辑只可能出现在 LoginByToken
          没有登录用户,游客用户ID也会保存在getUid()
         */
        val uid = RxHttpHelper.sharedInstance().uid
        map[FConstants.MY_UID] = uid
        map[FConstants.TOKEN] = RxHttpHelper.sharedInstance().token
        return map
    }
}