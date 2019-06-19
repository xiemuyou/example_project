package com.doushi.test.myproject.znet.rx;import com.doushi.library.global.FConstants;import com.doushi.test.myproject.znet.InterfaceConfig;import com.doushi.test.myproject.znet.RequestConfig;import java.util.HashMap;import java.util.Map;import io.reactivex.Observable;import io.reactivex.annotations.NonNull;/** * @author xiemy1 */public class RxAllService {    private volatile static RxAllService sharedInstance;    public static RxAllService sharedInstance() {        if (sharedInstance == null) {            synchronized (RxAllService.class) {                if (sharedInstance == null) {                    sharedInstance = new RxAllService();                }            }        }        return sharedInstance;    }    @SuppressWarnings("unchecked")    public Observable<String> serviceQueryByObj(Map<String, Object> map, @NonNull InterfaceConfig.HttpHelperTag tag) {        return serviceQueryByObj(map, tag, String.class);    }    public <T> Observable<T> serviceQueryByObj(Map<String, Object> map, @NonNull InterfaceConfig.HttpHelperTag tag, Class<T> clazz) {        Observable<T> observable = null;        try {            observable = requestWithObj(map, tag, clazz);        } catch (Exception e) {            e.printStackTrace();        }        return observable;    }    /**     * 发送数据请求     *     * @param param 请求参数,用map作为参数键值对     * @param tag   接口标签<>每一个接口对应一个标签,通过标签拼接获取请求类型,接口连接等参数</>     * @param clazz 请求服务器返回参数类型,不传默认为字符串类型     * @return RxJava Observable 对象     * @throws Exception 请求可能产生的异常     */    private <T> Observable<T> requestWithObj(final Map<String, Object> param, final InterfaceConfig.HttpHelperTag tag, Class<T> clazz) throws Exception {//        Map<String, Object> resultMap = InterfaceConfig.METHOD_GET;//                //.interfaceConfigByHttpTag(tag);//        String url = (String) resultMap.get(InterfaceConfig.INSTANCE.getINTERFACE_URL_STR());//        String queryTypeStr = (String) resultMap.get(InterfaceConfig.INSTANCE.getINTERFACE_QUERY_TYPE());//        RequestConfig internetConfig = null;//        Object obj = resultMap.get(InterfaceConfig.INSTANCE.getREQUEST_CONFIG());//        if (obj instanceof RequestConfig) {//            internetConfig = (RequestConfig) resultMap.get(InterfaceConfig.INSTANCE.getREQUEST_CONFIG());//        }//        if (InterfaceConfig.INSTANCE.getINTERFACE_QUERY_TYPE_POST().equals(queryTypeStr)) {//            return ServerApi.postData(url, addUserParams(param), clazz, internetConfig);//        } /*else if (tag == InterfaceConfig.HttpHelperTag.HTTPHelperTag_ReportCrashLog) {            ServerApi.reportCrashLog("", addUserParams(param));//            return null;//        } */ else {//            return ServerApi.getData(url, param, clazz, internetConfig);//        }        return null;    }    private void test(Map<String, Object> map) {        Map<String, Object> map1 = addUserParams(map);    }    /**     * 添加用户参数     *     * @param map 源参数数据     * @return 添加了用户参数的请求数据     */    private Map<String, Object> addUserParams(Map<String, Object> map) {        if (map == null) {            map = new HashMap<>();        }        /*          用户第一次使用,不存在游客ID跟用户ID,默认填 0          默认填 0 的情况正常逻辑只可能出现在 LoginByToken          没有登录用户,游客用户ID也会保存在getUid()         */        int uid = RxHttpHelper.sharedInstance().getUid();        map.put(FConstants.MY_UID, uid);        map.put(FConstants.TOKEN, RxHttpHelper.sharedInstance().getToken());        return map;    }}