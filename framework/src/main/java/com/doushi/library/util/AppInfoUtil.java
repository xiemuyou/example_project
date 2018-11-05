package com.doushi.library.util;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.doushi.library.FApplication;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

import io.reactivex.annotations.NonNull;

import static android.text.format.Formatter.formatIpAddress;

/**
 * app信息
 *
 * @author zhangw
 * @date 2017/4/19.
 */
public class AppInfoUtil {

    private static String TAG = "AppInfoUtil";

    /**
     * 安装指定路径下的Apk
     * <p>根据路径名是否符合和文件是否存在判断是否安装成功
     * <p>更好的做法应该是startActivityForResult回调判断是否安装成功比较妥当
     * <p>这里做不了回调，后续自己做处理
     */
    public static boolean installApp(String filePath) {
        if (filePath != null && filePath.length() > 4
                && filePath.toLowerCase().substring(filePath.length() - 4).equals(".apk")) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            File file = new File(filePath);
            if (file.exists() && file.isFile() && file.length() > 0) {
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                FApplication.getContext().startActivity(intent);
                return true;
            }
        }
        return false;
    }

    /**
     * 卸载指定包名的App
     * <p>这里卸载成不成功只判断了packageName是否为空
     * <p>如果要根据是否卸载成功应该用startActivityForResult回调判断是否还存在比较妥当
     * <p>这里做不了回调，后续自己做处理
     */
    public static boolean uninstallApp(String packageName) {
        if (!TextUtils.isEmpty(packageName)) {
            Intent intent = new Intent(Intent.ACTION_DELETE);
            intent.setData(Uri.parse("package:" + packageName));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            FApplication.getContext().startActivity(intent);
            return true;
        }
        return false;
    }

    /**
     * 打开指定包名的App
     */
    public static boolean openAppByPackageName(String packageName) {
        if (!TextUtils.isEmpty(packageName)) {
            PackageManager pm = FApplication.getContext().getPackageManager();
            Intent launchIntentForPackage = pm.getLaunchIntentForPackage(packageName);
            if (launchIntentForPackage != null) {
                FApplication.getContext().startActivity(launchIntentForPackage);
                return true;
            }
        }
        return false;
    }

    /**
     * 打开指定包名的App应用信息界面
     */
    public static boolean openAppInfo(String packageName) {
        if (!TextUtils.isEmpty(packageName)) {
            Intent intent = new Intent();
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.parse("package:" + packageName));
            FApplication.getContext().startActivity(intent);
            return true;
        }
        return false;
    }

    /**
     * 可用来做App信息分享
     */
    public static void shareAppInfo(String info) {
        if (!TextUtils.isEmpty(info)) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, info);
            FApplication.getContext().startActivity(intent);
        }
    }

    /**
     * 判断当前App处于前台还是后台
     * <p>需添加<uses-permission android:name="android.permission.GET_TASKS"/>
     * <p>并且必须是系统应用该方法才有效
     */
    public static boolean isAppBackground() {
        ActivityManager am = (ActivityManager) FApplication.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) {
            return false;
        }
        @SuppressWarnings("deprecation")
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(FApplication.getContext().getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取用户设备 ID
     *
     * @return 用户设备 ID
     */
    public static String getDeviceId() {
        return DeviceUuidFactory.getInstance().getDeviceUuid();
    }

    /**
     * 获取手机客户端IP地址
     *
     * @return
     */
    public static String getHostIp() {
        String hostIp;
        //wifi
        if (InternetUtil.NETWORK_WIFI == InternetUtil.getNetworkState(FApplication.getContext())) {
            //获取wifi服务
            WifiManager wifiManager = (WifiManager) FApplication.getContext()
                    .getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            //判断wifi是否开启
            if (wifiManager != null) {
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                int ipAddress = wifiInfo.getIpAddress();
                hostIp = formatIpAddress(ipAddress);
            } else {
                hostIp = getLocalIpAddress();
            }
        } else {
            hostIp = getLocalIpAddress();
        }
        return hostIp;
    }

    /**
     * @return 手机GPRS网络的IP
     */
    private static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    //获取IPv4的IP地址
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "0.0.0.0";
    }

    /**
     * 获取当前网络连接类型
     *
     * @return
     */
    public static String getNetworkState() {
        return InternetUtil.getStringNetworkState(FApplication.getContext());
    }

    /**
     * 获取友盟渠道名所需要的key,在 Manifest.xml中定义
     */
    private static final String UMENG_CHANNEL = "UMENG_CHANNEL";

    public static String getChannelId() {
        String channelId = "0";
        try {
            ApplicationInfo appInfo = FApplication.getContext().getPackageManager()
                    .getApplicationInfo(FApplication.getContext().getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo != null && appInfo.metaData != null) {
                channelId = appInfo.metaData.getString(UMENG_CHANNEL, channelId);
            }
        } catch (Exception e) {
            channelId = "0";
        }
        return channelId.trim();
    }

    /**
     * 获取版本型号
     *
     * @return 版本型号
     */
    public static String getPhoneModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getCarrier() {
        return android.os.Build.MANUFACTURER;
    }

    /**
     * 获取app当前versionName
     *
     * @return 当前versionName
     */
    public static String getAppVersionName() {
        String versionName;
        try {
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageManager packageManager = FApplication.getContext().getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(FApplication.getContext().getPackageName(), 0);
            versionName = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            versionName = "1.0.0";
        }
        return versionName;
    }

    /**
     * 获取当前versioncode
     *
     * @return 当前versioncode
     */
    public static int getAppVersionCode() {
        int versionCode;
        try {
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageManager packageManager = FApplication.getContext().getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(FApplication.getContext().getPackageName(), 0);
            versionCode = packInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            versionCode = 0;
        }
        return versionCode;
    }

    @SuppressLint("MissingPermission")
    public static String getImei(Context context) {
        String imei = "";
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager != null) {
                imei = telephonyManager.getDeviceId();
            }
        } catch (Exception e) {
            imei = "";
        }
        return imei;
    }

    /**
     * 获取通知权限
     *
     * @param context 上下文对象
     * @return 收到极光推送通知, 没有通知权限的手机获取通知权限
     */
    public static boolean geNotifyPermission(@NonNull Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return true;
        }
        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;
        Class appOpsClass;
      /* Context.APP_OPS_MANAGER */
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            if (appOpsClass == null) {
                return false;
            }
            Method checkOpNoThrowMethod = appOpsClass.getMethod("checkOpNoThrow", Integer.TYPE, Integer.TYPE, String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField("OP_POST_NOTIFICATION");
            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }
}
