package com.library.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * 获取网络连接的工具类
 *
 * @author xiemy
 * @date 2016/9/1.
 */
public class InternetUtil {

    /**
     * INTERNET
     * 没有网络连接
     */
    public static final int NETWORK_NONE = 0;
    /**
     * wifi连接
     */
    public static final int NETWORK_WIFI = 1;
    /**
     * 手机网络数据连接类型
     */
    public static final int NETWORK_2G = 2;
    public static final int NETWORK_3G = 3;
    public static final int NETWORK_4G = 4;
    public static final int NETWORK_MOBILE = 5;

    /**
     * 获取当前网络连接类型
     *
     * @param context
     * @return
     */
    public static int getNetworkState(Context context) {
        //获取系统的网络服务
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        //如果当前没有网络
        if (null == connManager) {
            return NETWORK_NONE;
        }

        //获取当前网络类型，如果为空，返回无网络
        NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();
        if (activeNetInfo == null || !activeNetInfo.isAvailable()) {
            return NETWORK_NONE;
        }

        // 判断是不是连接的是不是wifi
        NetworkInfo wifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (null != wifiInfo) {
            NetworkInfo.State state = wifiInfo.getState();
            boolean isWifi = null != state && (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING);
            if (isWifi) {
                return NETWORK_WIFI;
            }
        }

        // 如果不是wifi，则判断当前连接的是运营商的哪种网络2g、3g、4g等
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (null != networkInfo) {
            NetworkInfo.State state = networkInfo.getState();
            String strSubTypeName = networkInfo.getSubtypeName();
            if (null != state) {
                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                    switch (activeNetInfo.getSubtype()) {
                        //如果是2g类型
                        // 联通2g
                        case TelephonyManager.NETWORK_TYPE_GPRS:
                            // 电信2g
                        case TelephonyManager.NETWORK_TYPE_CDMA:
                            // 移动2g
                        case TelephonyManager.NETWORK_TYPE_EDGE:
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN:
                            return NETWORK_2G;
                        //如果是3g类型
                        // 电信3g
                        case TelephonyManager.NETWORK_TYPE_EVDO_A:
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                            return NETWORK_3G;
                        //如果是4g类型
                        case TelephonyManager.NETWORK_TYPE_LTE:
                            return NETWORK_4G;
                        default:
                            //中国移动 联通 电信 三种3G制式
                            if (strSubTypeName.equalsIgnoreCase("TD-SCDMA") ||
                                    strSubTypeName.equalsIgnoreCase("WCDMA") ||
                                    strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                                return NETWORK_3G;
                            } else {
                                return NETWORK_MOBILE;
                            }
                    }
                }
            }
        }
        return NETWORK_NONE;
    }

    /**
     * 获取当前网络连接类型
     *
     * @param context 上下文
     * @return 网络连接类型
     */
    public static String getStringNetworkState(Context context) {
        String netSateStr = "";
        int netSate = getNetworkState(context);
        switch (netSate) {
            case NETWORK_WIFI:
                netSateStr = "wifi";
                break;

            case NETWORK_2G:
                netSateStr = "2G";
                break;

            case NETWORK_3G:
                netSateStr = "3G";
                break;

            case NETWORK_4G:
                netSateStr = "4G";
                break;

            default:
                break;
        }
        return netSateStr;
    }
}
