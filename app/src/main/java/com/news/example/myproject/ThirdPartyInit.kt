package com.news.example.myproject

import android.app.Application
import android.util.Log
import com.blankj.utilcode.util.Utils
import com.library.global.FConstants
import com.library.util.AppIntroUtil
import com.lzy.okgo.OkGo
import com.lzy.okgo.interceptor.HttpLoggingInterceptor
import com.mob.MobSDK
import com.news.example.myproject.znet.RequestConfig
import com.tencent.smtt.sdk.QbSdk
import okhttp3.OkHttpClient
import org.litepal.LitePal
import java.util.concurrent.TimeUnit
import java.util.logging.Level

/**
 * 第三方初始化
 *
 * @author xiemy
 * @date 2017/4/18.
 */
internal class ThirdPartyInit {

    fun init(context: Application) {
        //初始化全局异常管理
        initCrashHandler(context)
        //初始化应用安装(新安装，更新安装，再次启动),并更新sp中版本信息
        AppIntroUtil.getInstance().markOpenApp()
        //Litepal数据库
        initLitePal(context)
        //okgo
        initOkGo(context)
        //内存泄漏
        initLeakCanary(context)
        //初始化 pcdn
        initPcdn(context)
        //初始化第三方登录分享
        initMob(context)
        //极光推送
        initJPush(context)
        //imageloader
        initImageLoader(context)
        // 初始化工具类 init it in the function of onCreate in ur Application
        Utils.init(context)
        //初始化腾讯X5
        initTencentX5(context)
    }

    /**
     * 阿里热修复
     *
     * @param context Application
     */
    fun initializeHotfix(context: Application) {
        //热修复
        //        SophixManager.getInstance().queryAndLoadNewPatch();
        //        String appVersion = AppInfoUtil.getAppVersionName();
        //        // initialize最好放在attachBaseContext最前面
        //        SophixManager.getInstance().setContext(context)
        //                .setAppVersion(appVersion)
        //                .setAesKey(null)
        //                .setEnableDebug(true)
        //                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
        //                    @Override
        //                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
        //                        // 补丁加载回调通知
        //                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
        //                            // 表明补丁加载成功
        //                            LogUtil.d(TAG, "表明补丁加载成功 : " + info);
        //                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
        //                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
        //                            // 建议: 用户可以监听进入后台事件, 然后调用killProcessSafely自杀，以此加快应用补丁，详见1.3.2.3
        //                            LogUtil.d(TAG, "表明新补丁生效需要重启. 开发者可提示用户或者强制重启 : " + info);
        //                        } else {
        //                            // 其它错误信息, 查看PatchStatus类说明
        //                            LogUtil.d(TAG, "其它错误信息 : " + info);
        //                        }
        //                    }
        //                }).initialize();
    }

    /**
     * 初始化 LitePal数据库
     * <>https://github.com/LitePalFramework/LitePal>
     */
    private fun initLitePal(context: Application) {
        LitePal.initialize(context)
    }

    /**
     * 初始化 okgo 文件下载
     */
    private fun initOkGo(context: Application) {
        //必须调用初始化
        //以下设置的所有参数是全局参数,同样的参数可以在请求的时候再设置一遍,那么对于该请求来讲,请求中的参数会覆盖全局参数
        //好处是全局参数统一,特定请求可以特别定制参数
        try {
            //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了

            val builder = OkHttpClient.Builder()
            //log相关
            val loggingInterceptor = HttpLoggingInterceptor("SanTong")
            //log打印级别，决定了log显示的详细程度
            loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY)
            loggingInterceptor.setColorLevel(Level.INFO)
            //log颜色级别，决定了log在控制台显示的颜色
            builder.addInterceptor(loggingInterceptor)

            //超时时间设置 //全局的读取超时时间
            builder.readTimeout(FConstants.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)
            //全局的写入超时时间
            builder.writeTimeout(FConstants.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)
            //全局的连接超时时间
            builder.connectTimeout(FConstants.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)

            //自动管理cookie（或者叫session的保持），以下几种任选其一就行
            //builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));            //使用sp保持cookie，如果cookie不过期，则一直有效
            //builder.cookieJar(new CookieJarImpl(new DBCookieStore(context)));              //使用数据库保持cookie，如果cookie不过期，则一直有效
            //builder.cookieJar(new CookieJarImpl(new DBCookieStore(context)));              //使用数据库保持cookie，如果cookie不过期，则一直有效
            //builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));            //使用内存保持cookie，app退出后，cookie消失

            // 其他统一的配置
            // 详细说明看GitHub文档：https://github.com/jeasonlzy/
            //必须调用初始化
            val config = RequestConfig()
            OkGo.getInstance().init(context)
                    //设置OkHttpClient
                    .setOkHttpClient(builder.build())
                    //全局统一缓存时间，默认永不过期，可以不传
                    .setCacheTime(config.cacheTime)
                    //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
                    .setRetryCount(config.retryCount).cacheMode = config.cacheMode
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 初始化异常捕获
     */
    private fun initCrashHandler(context: Application) {
        //        CrashHandler.getInstance().init(context);
    }

    /**
     * 仅用于测试
     * 初始化内存泄漏测试
     */
    private fun initLeakCanary(context: Application) {
        //        if (LeakCanary.isInAnalyzerProcess(context)) {
        //            // This process is dedicated to LeakCanary for heap analysis.
        //            // You should not init your app in this process.
        //            return;
        //        }
        //        LeakCanary.install(context);
    }

    /**
     * pcdn 初始化
     *
     * @param context Application上下文对象
     */
    private fun initPcdn(context: Application) {
        //        BaseUtils.init(context);
    }

    /**
     * 初始化第三方登录分享
     *
     * @param context Application上下文对象
     */
    private fun initMob(context: Application) {
        MobSDK.init(context)
    }

    /**
     * 初始化极光推送
     */
    private fun initJPush(context: Application) {
        //        //极光推送
        //        // 设置开启日志,发布时请关闭日志
        //        JPushInterface.setDebugMode(FConstants.debug);
        //        // 初始化 JPush
        //        JPushInterface.init(context);
    }

    /**
     * ImageLoader
     *
     * @param context Application上下文对象
     */
    private fun initImageLoader(context: Application) {
        //        int memoryCacheSize = (int) (Runtime.getRuntime().maxMemory() / 10);
        //        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
        //                .memoryCache(new LRULimitedMemoryCache(memoryCacheSize))
        //                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
        //                .tasksProcessingOrder(QueueProcessingType.LIFO)
        //                .build();
        //        // Initialize ImageLoader with configuration.
        //        ImageLoader.getInstance().init(config);
    }

    private fun initTencentX5(context: Application) {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。

        val cb = object : QbSdk.PreInitCallback {

            override fun onViewInitFinished(arg0: Boolean) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is $arg0")
            }

            override fun onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        }
        //x5内核初始化接口
        QbSdk.initX5Environment(context, cb)
    }

    companion object {

        private val TAG = "ThirdPartyInit"
    }
}
