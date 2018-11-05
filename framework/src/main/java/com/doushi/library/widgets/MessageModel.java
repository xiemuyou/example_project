package com.doushi.library.widgets;

/**
 * @author zhangw
 *         2018/1/12.
 *         zhangwei@kingnet.com
 *         message
 */
public class MessageModel {
    /**
     * 关于客户端的错误码提示： 
     * 1）客户端给用户提示异常的提示文案后面加上（错误码）
     * 2）错误码分3类：a）服务端返回的错误码，用正整数，如101
     * b）客户端内部的错误码，用负整数，如-102
     * c）客户端集成的第三方组件的错误码，用2个字母的前缀+第三方组件的错误码，如阿里云pcdn sdk的错误码用“pn1001”，友盟sdk的错误码用“ym1002”
     */
    /**
     * pcdn
     */
    public static final String PCDN = "pn";
    /**
     * 友盟
     */
    public static final String UMENG = "ym";
    /**
     * 极光
     */
    public static final String JPUSH = "jg";
    /**
     * 七牛
     */
    public static final String QINIU = "qn";
    /**
     * mob分享 第三方登录
     */
    public static final String MOB_SHARE = "mb";
    /**
     * 支付宝支付
     */
    public static final String APLIAY = "al";
    /**
     * 微信支付
     */
    public static final String WECHAT = "wx";

    public static final String PCDN_ERROR = "PCDN错误";
    public static final String UMENG_ERROR = "友盟错误";
    public static final String JPUSH_ERROR = "极光推送错误";
    public static final String QINIU_ERROR = "七牛上传错误";
    public static final String MOB_SHARE_ERROR = "Mob错误";
    public static final String APLIAY_ERROR = "支付宝支付错误";
    public static final String WECHAT_ERROR = "微信支付错误";

    private String error;
    private String thType;
    private String errorCode;

    public MessageModel(String error, String thType, String errorCode) {
        this.error = error;
        this.thType = thType;
        this.errorCode = errorCode;
    }

    public String getError() {
        return null == error ? "" : error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getThType() {
        return thType == null ? "" : thType;
    }

    public void setThType(String thType) {
        this.thType = thType;
    }

    public String getErrorCode() {
        return errorCode == null ? "" : errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getError());
        sb.append("(")
                .append(getThType())
                .append(getErrorCode())
                .append(")");
        return sb.toString();
    }
}
