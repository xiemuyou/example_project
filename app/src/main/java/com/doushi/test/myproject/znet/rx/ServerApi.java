/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.doushi.test.myproject.znet.rx;

import android.text.TextUtils;

import com.blankj.utilcode.util.EncryptUtils;
import com.doushi.library.global.FConstants;
import com.doushi.library.thread.ZZThreadPool;
import com.doushi.library.util.AppInfoUtil;
import com.doushi.library.util.JsonUtil;
import com.doushi.library.util.LogUtil;
import com.doushi.test.myproject.global.Constants;
import com.doushi.test.myproject.znet.RequestConfig;
import com.doushi.test.myproject.znet.RequestCookie;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.base.BodyRequest;
import com.lzy.okgo.request.base.Request;
import com.lzy.okrx2.adapter.ObservableBody;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import io.reactivex.Observable;

/**
 * 网络请求
 *
 * @author xiemy
 * @date 2018/2/28.
 */
public class ServerApi {

    private static final String REQ_KAY = "reqkey";
    private static final String COOKIE = "Cookie";
    private static final String KEY = "dsst@2017";

    /**
     * GET请求
     *
     * @param url            请求URL
     * @param map            请求参数
     * @param type           返回数据类型,默认String
     * @param internetConfig 是否读取缓存,默认为application OkGo 配置
     * @param <T>            返回参数泛型
     * @return RxJava Observable对象
     * @throws UnsupportedEncodingException 编码转换异常
     */
    public static <T> Observable<T> getData(String url, Map<String, Object> map, Type type, RequestConfig internetConfig)
            throws UnsupportedEncodingException {
        url = addUserParams(url);
        StringBuilder sb = new StringBuilder(url);
        if (map != null) {
            Set<String> keys = map.keySet();
            Iterator<String> iterator = keys.iterator();
            sb.append('&');
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = java.net.URLEncoder.encode(String.valueOf(map.get(key)), "utf-8");
                sb.append(key).append('=').append(value).append('&');
            }
            url = sb.substring(0, sb.length() - 1);
        }
        Request<T, ? extends Request> request = OkGo.get(url);
        String cookie = getRequestKey(url);
        request.headers(COOKIE, cookie);
        if (internetConfig != null) {
            //全局统一缓存时间，默认永不过期，可以不传
            request.cacheTime(internetConfig.getCacheTime())
                    //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
                    .retryCount(internetConfig.getRetryCount())
                    //请求网络失败后读取缓存
                    .cacheMode(internetConfig.getCacheMode());
        }
        request.converter(new JsonConvert<T>(type));
        return request.adapt(new ObservableBody<T>());
    }

    /**
     * post请求
     *
     * @param url            请求Url
     * @param param          请求参数
     * @param clazz          参数类型<>默认String</>
     * @param internetConfig 是否读取缓存,默认为application OkGO 配置
     * @param <T>            返回参数泛型
     * @return RxJava Observable对象
     */
    public static <T> Observable<T> postData(String url, Map<String, Object> param, Class<T> clazz, RequestConfig internetConfig) {
        param.put("ver", AppInfoUtil.getAppVersionName());
        String req = JsonUtil.objetcToJson(param);
        url = addUserParams(url);
        BodyRequest<T, ? extends BodyRequest> request = OkGo.post(url);
        String cookie = getRequestKey(url, req);
        request.headers(COOKIE, cookie).upJson(req);
        if (internetConfig != null) {
            //全局统一缓存时间，默认永不过期，可以不传
            request.cacheTime(internetConfig.getCacheTime())
                    //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
                    .retryCount(internetConfig.getRetryCount())
                    //请求网络失败后读取缓存
                    .cacheMode(internetConfig.getCacheMode());
        }
        request.converter(new JsonConvert<>(clazz));
        return request.adapt(new ObservableBody<T>());
    }

    /**
     * 崩溃日志即时上报
     *
     * @param url   崩溃日志上报URL
     * @param param 上报参数
     */
    public static void reportCrashLog(String url, Map<String, Object> param) {
        final String req = JsonUtil.objetcToJson(param);
        final String postUrl = addUserParams(url);
        ZZThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(postUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    // 提交模式
                    conn.setRequestMethod("POST");
                    //连接超时 单位毫秒
                    conn.setConnectTimeout(3000);
                    //读取超时 单位毫秒
                    conn.setReadTimeout(2000);
                    // 发送POST请求必须设置如下两行
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    // 获取URLConnection对象对应的输出流
                    //这里可以写一些请求头的东东...
                    String cookie = getRequestKey(postUrl, req);
                    conn.setRequestProperty(COOKIE, cookie);
                    //文本信息
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Charset", "UTF-8");

                    PrintWriter printWriter = new PrintWriter(conn.getOutputStream());
                    // 发送请求参数 post的参数 xx=xx&yy=yy
                    printWriter.write(req);
                    // flush输出流的缓冲
                    printWriter.flush();
                    //开始获取数据
                    BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    int len;
                    byte[] arr = new byte[1024];
                    while ((len = bis.read(arr)) != -1) {
                        bos.write(arr, 0, len);
                        bos.flush();
                    }
                    bos.close();
                    // 返回字符串
                    String msg = new String(bos.toByteArray());
                    LogUtil.d("PostUtil = " + msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 添加用户参数
     *
     * @param url 请求连接包含了ver值 : https://api.3tong.com?ver=1.1.1
     * @return 添加了用户参数的请求数据的url
     */
    private static String addUserParams(String url) {
        StringBuilder sbUrl = new StringBuilder(url);
        /*
          用户第一次使用,不存在游客ID,跟用户ID 默认填 0
          默认填 0 的情况正常逻辑只可能出现在 LoginByToken
          没有登录用户,游客用户ID也会保存在getUid()
         */
        int uid = RxHttpHelper.sharedInstance().getUid();
        sbUrl.append('?').append("ver=").append(AppInfoUtil.getAppVersionName()).append('&')
                .append(FConstants.MY_UID).append('=').append(uid).append('&')
                .append(FConstants.TOKEN).append('=').append(RxHttpHelper.sharedInstance().getToken());
        return sbUrl.toString();
    }

    public static <T> Observable<T> statistical(String url, String postData, Class<T> clazz) {
        BodyRequest<T, ? extends BodyRequest> request = OkGo.post(url);
        String cookie = getRequestKey(url, postData);
        request.headers(COOKIE, cookie).upJson(postData);
        request.converter(new JsonConvert<>(clazz));
        return request.adapt(new ObservableBody<T>());
    }

    private static String getRequestKey(String url) {
        return getRequestKey(url, null);
    }

    /**
     * 获取RequestKey对象
     *
     * @param url      请求url
     * @param postData post请求需要传参数
     * @return RequestKey对象
     */
    private static String getRequestKey(String url, String postData) {
        RequestCookie rc = new RequestCookie();
        rc.setTs((System.currentTimeMillis() / 1000) - FConstants.SERVER_DIFFERENCE_TIME);
        rc.setDevtype(2);
        rc.setMid(AppInfoUtil.getDeviceId());
        rc.setOsver(AppInfoUtil.getSystemVersion());
        rc.setDid_jg(Constants.JG_DEVICE_ID);
        rc.setSum(getSum(url, rc, postData));
        rc.setNet(AppInfoUtil.getNetworkState());
        int chId;
        try {
            chId = Integer.parseInt(AppInfoUtil.getChannelId());
        } catch (ClassCastException e) {
            chId = 0;
        }
        rc.setChid(chId);
        return REQ_KAY + '=' + JsonUtil.objetcToJson(rc);
    }

    /**
     * RequestCookie md5加密
     *
     * @param url      请求url
     * @param rc       RequestCookie对象
     * @param postData post请求需要传的参数
     * @return 加密后的MD5字符串
     */
    private static String getSum(String url, RequestCookie rc, String postData) {
        String result = url.substring(url.indexOf("//") + 2, url.length());
        result = result.substring(result.indexOf("/"), result.length());
        StringBuilder sbReq = new StringBuilder();
        sbReq.append(result)
                .append(KEY)
                .append(rc.getDevtype())
                .append(rc.getOsver())
                .append(rc.getMid())
                .append(String.valueOf(rc.getTs()));
        if (!TextUtils.isEmpty(postData)) {
            sbReq.append(postData);
        }
        return EncryptUtils.encryptMD5ToString(sbReq.toString());
    }
}
