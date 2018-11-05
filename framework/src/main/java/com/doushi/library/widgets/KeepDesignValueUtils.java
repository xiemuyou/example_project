package com.doushi.library.widgets;

import com.blankj.utilcode.util.ScreenUtils;

import java.math.BigDecimal;

/**
 * Created by xuleyuan on 22/11/2017
 */

public class KeepDesignValueUtils {
    private static KeepDesignValueUtils instance;
    private static int DESIGN_WIDTH = 720; //设计稿宽度
    private static int DESIGN_HEIGHT = 1280; //设计稿高度

    private KeepDesignValueUtils() {
//        try {
//            ApplicationInfo info = FApplication.getFApplication().getPackageManager()
//                    .getApplicationInfo(FApplication.getFApplication().getPackageName(),
//                            PackageManager.GET_META_DATA);
//            DESIGN_WIDTH = info.metaData.getInt("designWidth");
//            DESIGN_HEIGHT = info.metaData.getInt("designHeight");
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }

    }


    public float calculatorValue(float val) {
        return (float) mul(val, div(ScreenUtils.getScreenWidth(), DESIGN_WIDTH, 10));
    }

    public int calculatorValue(int val) {
        return (int) mul(val, div(ScreenUtils.getScreenWidth(), DESIGN_WIDTH, 10));
    }


    public static KeepDesignValueUtils getInstance() {
        if (instance == null)
            instance = new KeepDesignValueUtils();
        return instance;
    }

    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }
}
