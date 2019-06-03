package com.doushi.test.myproject.znet;import android.text.TextUtils;import android.util.Log;import com.doushi.library.global.FConstants;import com.doushi.library.model.PortData;import com.doushi.library.util.AppInfoUtil;import com.doushi.test.myproject.BuildConfig;/** * 请求配置 * * @author xiemy * @date 2017/7/27 */public class HttpConfig {    //https://www.jianshu.com/p/e6f072839282    //https://github.com/jokermonn/-Api/blob/master/Time.md    //https://www.wanandroid.com/openapis    //http://myersguo.cn/2017/03/01/toutiao-interface.html    public static String TAG = "HttpConfig";    /**     * https://is.snssdk.com/article/category/get_extra/v1/     */    private static final String API = "https://is.snssdk.com";    public static int PORT = PortData.PORT_80;    /**     * 开发环境(用于开发人员调试切换)     */    private static final String DEV_RELEASE = API;    /**     * 测试环境(用于测试人员调试切换)     */    private static final String TEST_RELEASE = API;    /**     * 线上预发布环境     */    private static final String PRE_RELEASE = API;    /**     * 正式环境     */    private static final String RELEASE_URL = API;    private static String rootUrl;    static String getRootUrl() {        FConstants.debug = BuildConfig.DEBUG;        if (!FConstants.debug) {            if (!TextUtils.isEmpty(rootUrl)) {                return rootUrl;            }            rootUrl = getEnvironmentUrl();        } else {            rootUrl = getDebugUrl();        }        return rootUrl;    }    private static String getEnvironmentUrl() {        StringBuffer url;        int eType = getEnvironmentType();        switch (eType) {            //测试            case DEV:            case CESHI:                url = new StringBuffer(TEST_RELEASE).append("/");                break;            //预发布            case PRE:                url = new StringBuffer(PRE_RELEASE).append("/");                break;            //正式环境            default:                url = new StringBuffer(RELEASE_URL).append("/");                break;        }        return url.toString();    }    private static String getDebugUrl() {        StringBuffer url = new StringBuffer(DEV_RELEASE);        switch (PORT) {            case PortData.PORT_81:                url.append(":81/");                break;            case PortData.PORT_82:                url.append(":82/");                break;            case PortData.PORT_83:                url.append(":83/");                break;            case PortData.PORT_84:                url.append(":84/");                break;            case PortData.PORT_85:                url.append(":85/");                break;            case PortData.PORT_86:                url.append(":86/");                break;            case PortData.PORT_87:                url.append(":87/");                break;            case PortData.PORT_88:                url.append(":88/");                break;            //测试环境            case PortData.CESHI:                url = new StringBuffer(TEST_RELEASE).append("/");                break;            //线上环境            case PortData.PRE:                url = new StringBuffer(PRE_RELEASE).append("/");                break;            //正式环境            case PortData.FORMAL:                url = new StringBuffer(RELEASE_URL).append("/");                break;            //默认80            default:                url.append("/");                break;        }        Log.d(TAG, url.toString());        return url.toString();    }    /**     * 获取html链接地址     *     * @return html链接地址     */    public static String getHtmlRootUrl() {        FConstants.debug = BuildConfig.DEBUG;        String app = "/app/";        if (!FConstants.debug) {            StringBuffer url;            int eType = getEnvironmentType();            switch (eType) {                //测试                case DEV:                case CESHI:                    url = new StringBuffer("http://ceshi.www.3tong.com");                    break;                //正式环境                default:                    url = new StringBuffer("http://www.3tong.com");                    break;            }            return url.append(app).toString();        } else {            return "http://dev.www.3tong.com" + app;        }    }    public static final int DEV = 0;    public static final int CESHI = 1;    private static final int PRE = 2;    private static final int RELEASE = 3;    public static int getEnvironmentType() {        //环境类型        int eType;        //默认为正式环境        int vEndCode = 2;        try {            String vName = AppInfoUtil.getAppVersionName();            if (TextUtils.isEmpty(vName)) {                return vEndCode;            }            vEndCode = Integer.parseInt(vName.substring(vName.length() - 1));        } catch (Exception e) {            vEndCode = 2;        }        switch (vEndCode) {            case 1:            case 3:            case 5:            case 7:            case 9:                eType = FConstants.debug ? DEV : CESHI;                break;            case 0:            case 4:                eType = PRE;                break;            default:                eType = RELEASE;                break;        }        return eType;    }}