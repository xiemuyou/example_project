package com.doushi.library.util;

/**
 * 封装app信息
 *
 * @author zhangw
 */
public class AppIntroUtil {

    private final String TAG = "AppIntroUtil";
    /**
     * 首次安装启动
     */
    private static final int LMODE_NEW_INSTALL = 1;
    /**
     * 更新后第一次启动
     */
    private static final int LMODE_UPDATE = 2;
    /**
     * 再次启动
     */
    private static final int LMODE_AGAIN = 3;

    /**
     * 上次保存的版本号
     */
    public static final String LAST_VERSION = "localVersionCode";
    /**
     * 防止重复调用
     */
    private boolean isOpenMarked = false;
    /**
     * 启动-模式
     */
    private int launchMode = LMODE_AGAIN;

    /**
     * 单例
     */
    private static AppIntroUtil instance;

    private AppIntroUtil() {
        markOpenApp();
    }

    public static AppIntroUtil getInstance() {
        if (null == instance) {
            synchronized (AppIntroUtil.class) {
                if (null == instance) {
                    instance = new AppIntroUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 标记-打开app,用于产生-是否首次打开
     */
    public void markOpenApp() {
        int lastVersion = PreferencesUtils.getIntPreferences(LAST_VERSION, 0);
        //防止重复调用
        if (isOpenMarked) {
            return;
        }
        isOpenMarked = true;

        int nowVersion = AppInfoUtil.getAppVersionCode();
        LogUtil.d(TAG, "nowVersion:" + nowVersion);
        //首次启动
        if (lastVersion == 0) {
            launchMode = LMODE_NEW_INSTALL;
            PreferencesUtils.setIntPreferences(LAST_VERSION, nowVersion);
        } else if (nowVersion > lastVersion) {
            launchMode = LMODE_UPDATE;
            PreferencesUtils.setIntPreferences(LAST_VERSION, nowVersion);
        } else {
            launchMode = LMODE_AGAIN;
        }
    }

    /**
     * 获取当前启动模式
     *
     * @return
     */
    public int getLaunchMode() {
        return launchMode;
    }

    /**
     * 首次打开,新安装、覆盖(版本号不同)
     *
     * @return
     */
    public boolean isFirstOpen() {
        return launchMode != LMODE_AGAIN;
    }

    public int getIsFirstStartUp() {
        return isFirstOpen() ? 1 : 0;
    }

    /**
     * 是否首次安装
     *
     * @return
     */
    public boolean isFirstLaunch() {
        return launchMode == LMODE_NEW_INSTALL;
    }

    /**
     * 本地时间与系统时间差值
     */
    private static long betweenTime;

    public static long getServiceTime() {
        return System.currentTimeMillis() + betweenTime;
    }

    /**
     * 设置系统时间
     *
     * @param systime 请求接口获取的系统时间
     */
    public static void setSystime(long systime) {
        long cTime = System.currentTimeMillis();
        betweenTime = (systime * 1000) - cTime;
    }
}
