package com.doushi.library.util;

import android.os.Build;
import android.util.Log;

import com.doushi.library.global.FConstants;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 打印LOG日志
 *
 * @author xiemy
 * @date 2018/4/20
 */
public class LogUtil {

    private static final String DEFAULT_TAG = "SanTong";

    public static boolean debug = FConstants.debug;

    public static void info(Object obj) {
        if (debug) {
            info(JsonUtil.objetcToJson(obj));
        }
    }

    public static void info(String str) {
        if (debug) {
            Log.i("info", buildMessage(str));
        }
    }

    public static void d(Object obj) {
        if (debug) {
            d(DEFAULT_TAG, JsonUtil.objetcToJson(obj));
        }
    }

    public static void d(String tag, Object obj) {
        if (debug) {
            d(tag, JsonUtil.objetcToJson(obj));
        }
    }

    public static void d(String msg) {
        d(DEFAULT_TAG, buildMessage(msg));
    }

    public static void d(String tag, String msg) {
        if (debug) {
            Log.d(tag, buildMessage(msg));
        }
    }

    public static void i(String msg) {
        i(DEFAULT_TAG, msg);
    }

    public static void i(String tag, String msg) {
        if (debug) {
            Log.i(tag, buildMessage(msg));
        }
    }

    public static void w(String msg) {
        if (debug) {
            w(DEFAULT_TAG, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (debug) {
            Log.w(tag, buildMessage(msg));
        }
    }

    public static void w(Throwable tr) {
        w(DEFAULT_TAG, buildMessage(""), tr);
    }

    public static void w(String tag, Throwable tr) {
        if (debug) {
            w(tag, buildMessage(""), tr);
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (debug) {
            Log.w(tag, buildMessage(msg), tr);
        }
    }

    public static void e(String msg) {
        if (debug) {
            e(DEFAULT_TAG, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (debug) {
            Log.e(tag, msg);
        }
    }

    public static void e(Throwable tr) {
        e(DEFAULT_TAG, buildMessage(""), tr);
    }

    public static void e(String tag, Throwable tr) {
        if (debug) {
            Log.e(tag, DEFAULT_TAG, tr);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (debug) {
            Log.e(tag, buildMessage(msg), tr);
        }
    }

    private static String buildMessage(String msg) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        int index = 4;
        String className = stackTrace[index].getFileName();
        String methodName = stackTrace[index].getMethodName();
        int lineNumber = stackTrace[index].getLineNumber();
        methodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ (").append(className).append(":").append(lineNumber).append(")#").append(methodName).append(" ] ");
        if (msg == null) {
            msg = "Log with null Object";
        }
        stringBuilder.append(msg);
        return stringBuilder.toString();
    }

    /**
     * 日志记录是否打开
     */
    public static boolean logControl = true;
    /**
     * 日志等级
     * a）Detail，最详细的级别，用于调试
     * b）Info，记录行为事件
     * c）Error，发生错误时用
     */
    public static final int DETAIL_LOG_LEVEL = 1;
    public static final int INFO_LOG_LEVEL = 2;
    public static final int ERROR_LOG_LEVEL = 3;

    /**
     * 日志类型
     * a）调用服务端接口时
     * b）用户行为
     * c）客户端异常逻辑
     * d）调用第三方组件接口
     */
    public static final int SERVER_LOG_TYPE = 11;
    public static final int USER_LOG_TYPE = 12;
    public static final int CLIENT_LOG_TYPE = 13;
    public static final int LIB_LOG_TYPE = 14;

    /**
     * 保存日志
     *
     * @param savePathStr   目录路径
     * @param saveFileNameS 文件名
     * @param saveDataStr   日期时间
     * @param logLevel      日志等级 {@link #DETAIL_LOG_LEVEL 最详细的级别，用于调试,#INFO_LOG_LEVEL 记录行为事件 ,#ERROR_LOG_LEVEL 发生错误时用}
     */
    public static void saveLog(String savePathStr, String saveFileNameS, String saveDataStr, int logLevel) {
        Map<String, String> paramsMap = new HashMap<>();
        //获取versionName,versionCode
        paramsMap.put("versionName", AppInfoUtil.getAppVersionName());
        paramsMap.put("versionCode", String.valueOf(AppInfoUtil.getAppVersionCode()));
        StringBuffer sb = new StringBuffer(DateUtil.getCurrentTime(DateUtil.FULL_DATE_PATTERN) + ":");
        //详细的日志
        if (logLevel == DETAIL_LOG_LEVEL) {
            //获取所有系统信息
            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    paramsMap.put(field.getName(), field.get(null).toString());
                } catch (Exception e) {
                    Log.e(DEFAULT_TAG, "an error occured when collect crash info", e);
                }
            }
        }

        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }
        sb.append(saveDataStr);

        FileWriter fw = null;
        PrintWriter pw = null;
        try {
            File folder = new File(savePathStr);
            if (folder.exists() == false || folder.isDirectory() == false) {
                folder.mkdirs();
            }
            File f = new File(folder, saveFileNameS);
            fw = new FileWriter(f, true);

            pw = new PrintWriter(fw);
            pw.print(saveDataStr + "\r\n");
            pw.flush();
            fw.flush();
        } catch (Exception e) {
            Log.e(DEFAULT_TAG, "an error occured while writing file...", e);
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    Log.e(DEFAULT_TAG, "an error occured while close fileWriter...", e);
                }
            }
            if (pw != null) {
                pw.close();
            }
        }
    }

    /**
     * @param log      日志信息
     * @param logLevel 日志等级
     */
    public static void saveLog(String log, int logLevel) {
        if (logControl) {
            String directoryPath = FileUtils.getInstance().getDesFilePath(FileUtils.FilePathType.LOG);
            String fileName = FileUtils.createCommonLogPath();
            File path = new File(directoryPath);
            saveLog(path.getPath(), fileName, log, logLevel);
        }
    }

    /**
     * @param tr       日志信息
     * @param logLevel 日志等级
     */
    public static void saveLog(Throwable tr, int logLevel) {
        if (logControl) {
            String directoryPath = FileUtils.getInstance().getDesFilePath(FileUtils.FilePathType.LOG);
            String fileName = FileUtils.createCommonLogPath();
            File path = new File(directoryPath);
            saveLog(path.getPath(), fileName, saveInfo(tr), logLevel);
        }
    }

    /**
     * @param ex       日志信息
     * @param logLevel 日志等级
     */
    public static void saveLog(Exception ex, int logLevel) {
        if (logControl) {
            String directoryPath = FileUtils.getInstance().getDesFilePath(FileUtils.FilePathType.LOG);
            String fileName = FileUtils.createCommonLogPath();
            File path = new File(directoryPath);

            Throwable cause = ex.getCause();
            String result = saveInfo(cause);
            saveLog(path.getPath(), fileName, result, logLevel);
        }
    }

    /**
     * 获取详细信息
     *
     * @param ex ex
     * @return errorMessage
     */
    private static String saveInfo(Throwable ex) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        return writer.toString();
    }
}
