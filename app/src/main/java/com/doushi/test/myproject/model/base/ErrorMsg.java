package com.doushi.test.myproject.model.base;

import com.doushi.test.myproject.global.DefaultValue;

/**
 * @author xiemy1
 * @date 2018/5/18
 */
public class ErrorMsg {
    /**
     * 网络异常
     */
    public static final int NET_ERROR_CODE = -1;
    /**
     * 数据异常
     */
    public static final int DATA_ERROR_CODE = -2;
    /**
     * 成功
     */
    public static final int SUCCESS_CODE = 200;

    public static String checkErrorCode(int errorCode) {
        String msg = DefaultValue.ERROR_MSG;
        switch (errorCode) {
            //成功
            case SUCCESS_CODE:
                msg = "ok";
                break;

            default:
                break;
        }
        return msg;
    }
}
