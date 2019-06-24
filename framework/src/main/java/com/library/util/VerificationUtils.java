package com.library.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.IdRes;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证工具类
 *
 * @author xiemy1
 */
public class VerificationUtils {

    private static String TAG = "VerificationUtils";
    private static long lastClickTime;
    private static long lastViewId;

    /**
     * 非空验证
     *
     * @param et EditText 控件
     * @return 空返回false，非空返回true
     */
    public static boolean isEmpty(EditText et) {
        return et == null || isEmpty(et.getText().toString());
    }

    /**
     * 非空验证
     *
     * @param tv TextView 控件
     * @return 空返回false，非空返回true
     */
    public static boolean isEmpty(TextView tv) {
        return tv != null && isEmpty(tv.getText().toString());
    }

    public static boolean isEmpty(String str) {
        return !(str != null && str.trim().length() > 0);
    }

    /**
     * 验证手机号码
     *
     * @param tv
     * @return [0-9]{5,9}
     */
    public static boolean isMobileNO(TextView tv) {
        return tv != null && isMobileNO(tv.getText().toString());
    }

    /**
     * 验证手机号码
     *
     * @param et
     * @return [0-9]{5,9}
     */
    public static boolean isMobileNO(EditText et) {
        return et != null && isMobileNO(et.getText().toString());
    }

    private static Pattern MOBILE_NO_PATTERN = Pattern.compile("^1([\\d]{10})$");

    public static boolean isMobileNO(String mobiles) {
        boolean flag;
        try {
            Matcher m = MOBILE_NO_PATTERN.matcher(mobiles);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    private static Pattern WECHAT_NUMBER_PATTERN = Pattern.compile("^[a-zA-Z\\d_]{5,}$");

    public static boolean verifyWechatNumber(String weixin) {
        boolean flag;
        try {
            Matcher m = WECHAT_NUMBER_PATTERN.matcher(weixin);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 密码验证
     *
     * @param pwd
     * @return
     */
    public static boolean isPassword(String pwd) {
        String reg = "^[^\\s]{6,16}$";
        Pattern pattern = Pattern.compile(reg);
        return pattern.matcher(pwd).matches();
    }

    /**
     * �?测是否安�? apk
     *
     * @param context
     * @param packageName 应用包名
     * @return true安装 false未安装
     */
    public static boolean isInstallApk(Context context, String packageName) {
        try {
            // �?测是否包含包�?
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(packageName,
                    PackageManager.GET_ACTIVITIES);
            if (info != null) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isPackageAvilible(Context context, String packageName) {
        // 获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null && pinfo.size() > 0) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static final long MILLIS_TIME = 1000;

    /**
     * 防暴力点击方�?
     */
    public static boolean isFastDoubleClick(@IdRes int viewId) {
        return isFastDoubleClick(MILLIS_TIME, viewId);
    }

    public static boolean isFastDoubleClick(long millisTime, @IdRes int viewId) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < millisTime && lastViewId == viewId) {
            return true;
        }
        lastClickTime = time;
        lastViewId = viewId;
        return false;
    }

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < MILLIS_TIME) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * �?查用户是否开取了该权�?
     *
     * @param context    上下文对�?
     * @param permission 权限类型
     * @return 是否授权了改权限
     */
    public static boolean checkPermission(Context context, String permission) {
        return PackageManager.PERMISSION_GRANTED == context.getPackageManager().checkPermission(permission, context.getPackageName());
    }

    /**
     * 判断是否为平板
     *
     * @return true为平板
     */
    public static boolean isPad(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        double x = 0;
        double y = 0;
        if (wm != null) {
            Display display = wm.getDefaultDisplay();
            DisplayMetrics dm = new DisplayMetrics();
            display.getMetrics(dm);
            x = Math.pow(dm.widthPixels / dm.xdpi, 2);
            y = Math.pow(dm.heightPixels / dm.ydpi, 2);
        }
        // 屏幕尺寸
        double screenInches = Math.sqrt(x + y);
        // 大于6尺寸则为Pad
        return screenInches >= 6.0;
    }

    /**
     * 首先进行入参检查防止出现空指针异常
     * 如果两个参数都为空，则返回true
     * 如果有一项为空，则返回false
     * 接着对第一个list进行遍历，如果某一项第二个list里面没有，则返回false
     * 还要再将两个list反过来比较，因为可能一个list是两一个list的子集
     * 如果成功遍历结束，返回true
     *
     * @param l0 list1
     * @param l1 list2
     * @return 列表内容是否相等
     */
    public static boolean isListEquals(List l0, List l1) {
        if (l0 == l1) {
            return true;
        }
        if (l0 == null || l1 == null) {
            return false;
        }
        if (l0.size() != l1.size()) {
            return false;
        }
        for (Object o : l0) {
            if (!l1.contains(o)) {
                return false;
            }
        }
        for (Object o : l1) {
            if (!l0.contains(o)) {
                return false;
            }
        }
        return true;
    }
}
