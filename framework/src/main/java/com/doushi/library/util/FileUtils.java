package com.doushi.library.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.doushi.library.FApplication;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    private final static String TAG = "FileUtils";

    private final static String IMAGE_EXTENSION = ".jpg";
    private final static String VIDEO_EXTENSION = ".mp4";
    private final static String LOG_TEXT = ".log";
    private final static String APP_NAME = "sanTong";
    private final static String FILE_PREFIX = "st_";
    private static final File EXTERNAL_STORAGE_DIRECTORY = Environment.getExternalStorageDirectory();

    private static FileUtils instance;


    public static FileUtils getInstance() {
        if (null == instance) {
            synchronized (AppIntroUtil.class) {
                if (null == instance) {
                    instance = new FileUtils();
                }
            }
        }

        return instance;
    }

    /**
     * 文件保存位置
     */
    public enum FilePathType {
        /**
         * 图片
         */
        IMG("/img/"),

        /**
         * 消息
         */
        MSG("/msg/"),

        /**
         * 拍摄视频
         */
        RECORD_MP4("/record/"),

        /**
         * 下载视频
         */
        DOWNLOAD_MP4("/stVideo/"),

        /**
         * 下载视频
         */
        CACHE_VIDEO("/cacheVideo/"),

        /**
         * 崩溃日志
         */
        CRASH("/crash/"),

        /**
         * 永久文件
         */
        PERMANENT("/permanent/"),

        /**
         * 其他文件
         */
        OTHER("/other/"),

        /**
         * 临时文件
         */
        TEMP("/temp/"),

        /**
         * 日志记录
         */
        LOG("/stLog/");

        private String pathType;

        FilePathType(String pathType) {
            this.pathType = pathType;
        }
    }

    /**
     * 获取保存路径
     *
     * @param pathType 保存文件类型文件夹
     * @return 获取保存路径
     */
    public String getDesFilePath(FilePathType pathType) {
        if (pathType == FilePathType.TEMP
                || pathType == FilePathType.CRASH
                || pathType == FilePathType.MSG
                || pathType == FilePathType.DOWNLOAD_MP4
                || pathType == FilePathType.CACHE_VIDEO) { //临时文件，奔溃日志文件保存在android/data/包名 应用卸载后清除
            String storagePath = FApplication.getContext().getFilesDir() + "/" + pathType.pathType;
            File file = new File(storagePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            return storagePath;
        } else if (pathType == FilePathType.LOG) {
            //保存的日志文件
            String storagePath = FApplication.getContext().getExternalFilesDir(null) + "/" + pathType.pathType;
            File file = new File(storagePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            return storagePath;
        } else { //图片，拍摄的视频，永久文件，其他文件等
            String storagePath = EXTERNAL_STORAGE_DIRECTORY.getAbsolutePath() + "/" + APP_NAME + pathType.pathType;
            File file = new File(storagePath);
            if (!file.exists()) {
                if (!file.mkdirs()) {
                    storagePath = getPathInPackage(true, pathType);
                }
            }
            return storagePath;
        }
    }

    private String getPathInPackage(boolean grantPermissions, FilePathType pathType) {

        //手机不存在sdcard, 需要使用 data/data/name.of.package/files 目录
        String path = getDiskCacheDir(FApplication.getContext()) + "/" + APP_NAME + pathType.pathType;
        File file = new File(path);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.e(TAG, "在pakage目录创建CGE临时目录失败!");
                return null;
            }

            if (grantPermissions) {
                //设置隐藏目录权限.
                if (file.setExecutable(true, false)) {
                    Log.i(TAG, "Package folder is executable");
                }

                if (file.setReadable(true, false)) {
                    Log.i(TAG, "Package folder is readable");
                }

                if (file.setWritable(true, false)) {
                    Log.i(TAG, "Package folder is writable");
                }
            }
        }
        return path;
    }

    public static String createImagePath() {
        return FILE_PREFIX + System.currentTimeMillis() + IMAGE_EXTENSION;
    }

    /**
     * 保存的图片路径名称
     *
     * @param decPath 图片路径
     * @return 路径名称
     */
    public static String createImagePath(String decPath) {
        if (TextUtils.isEmpty(decPath)) {
            return createImagePath();
        }
        return decPath + IMAGE_EXTENSION;
    }

    /**
     * 保存视频路径名称
     *
     * @return 路径名称
     */
    public static String createVideoPath() {
        return FILE_PREFIX + System.currentTimeMillis() + VIDEO_EXTENSION;
    }

    /**
     * 保存视频路径名称
     *
     * @param name 路径名称
     * @return 路径名称
     */
    public static String createVideoPath(String name) {
        if (TextUtils.isEmpty(name)) {
            return createVideoPath();
        }
        return name + VIDEO_EXTENSION;
    }

    /**
     * 异常日志路径名称
     *
     * @return 路径名称
     */
    public static String createCrashLogPath() {
        String fileName = "crash-" + System.currentTimeMillis();
        return FILE_PREFIX + fileName + LOG_TEXT;
    }

    /**
     * 普通日志名称
     *
     * @return 日志名称
     */
    public static String createCommonLogPath() {
        String fileName = "common-" + System.currentTimeMillis();
        return FILE_PREFIX + fileName + LOG_TEXT;
    }

    /**
     * 日志
     *
     * @param logType 日志类型
     * @return 日志名称
     */
    public static String createCommonLogPath(String logType) {
        if (TextUtils.isEmpty(logType)) {
            return createCommonLogPath();
        }
        String fileName = logType + "-" + System.currentTimeMillis();
        return FILE_PREFIX + fileName + LOG_TEXT;
    }

    private String getDiskCacheDir(Context context) {
        String cachePath;
        if (context == null) {
            return "";
        }
        boolean externalCachePath = (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) && context.getExternalCacheDir() != null;
        if (externalCachePath) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }


    /**
     * 查询传过来的文件是否存在
     *
     * @param filePath 文件路径
     * @return true存在
     */
    public static boolean fileIsExists(String filePath) {
        try {
            File f = new File(filePath);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 删除文件
     *
     * @param file 需要删除的文件
     */
    public static void deletedFile(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }

        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
                return;
            }

            for (int i = 0; i < childFiles.length; i++) {
                deletedFile(childFiles[i]);
            }
            file.delete();
        }
    }

    public static List<String> readeFile(@NonNull File file, @NonNull String containsStr) {
        FileInputStream is = null;
        InputStreamReader inReader = null;
        BufferedReader bufReader = null;
        StringBuilder sb = new StringBuilder();
        List<String> result = new ArrayList<>(2);
        try {
            is = new FileInputStream(file);
            inReader = new InputStreamReader(is, "UTF-8");
            bufReader = new BufferedReader(inReader);
            String line;
            while ((line = bufReader.readLine()) != null) {
                if (line.contains(containsStr)) {
                    containsStr = line;
                }
                sb.append(line + "\n");
            }
            result.add(sb.toString());
            result.add(containsStr);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (inReader != null) {
                try {
                    inReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufReader != null) {
                try {
                    bufReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static String readeFile(File file) {
        ByteArrayOutputStream bos = null;
        FileInputStream is = null;
        try {
            is = new FileInputStream(file);
            bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            bos.close();
            is.close();
            return bos.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 往SD卡文件中写数据
     *
     * @param pageName 文件夹名
     * @param fileName 文件名
     * @param writeStr 数据内容
     * @throws IOException 异常
     */
    public static void writeFile(String pageName, String fileName, String writeStr) throws IOException {
        String filePath = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            filePath = Environment.getExternalStorageDirectory() + File.separator + pageName;
        } else {
            filePath = Environment.getRootDirectory() + File.separator + pageName;
        }

        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String fName = filePath + File.separator + fileName;
        writeSDFile(fName, writeStr);
    }

    /**
     * 往SD中的文件写内容
     *
     * @param fileName 文件名
     * @param writeStr 数据内容
     * @throws IOException 异常
     */
    public static void writeSDFile(String fileName, String writeStr) throws IOException {
        FileOutputStream fout = new FileOutputStream(fileName, false);
        byte[] bytes = writeStr.getBytes();
        fout.write(bytes);
        fout.close();
    }

    /**
     * 保存图片
     *
     * @param bitmap            需要保存的位图对象
     * @param captureBitmapPath 需要保存位图路径
     */
    public void saveBitmap(Bitmap bitmap, String captureBitmapPath) {
        FileOutputStream fOut = null;
        File extStorage;
        if (bitmap == null || captureBitmapPath == null) {
            return;
        }
        if (isExternalStorageAvailableAndWriteable()) {
            extStorage = new File(captureBitmapPath);
            try {
                if (!extStorage.exists()) {
                    extStorage.createNewFile();
                }
                fOut = new FileOutputStream(extStorage);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fOut != null) {
                    try {
                        fOut.flush();
                        fOut.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 判断外部存储是否可用,可写
     *
     * @return true可用
     */
    private static boolean isExternalStorageAvailableAndWriteable() {
        boolean externalStorageAvailable;
        boolean externalStorageWriteable;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            externalStorageAvailable = externalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            externalStorageAvailable = true;
            externalStorageWriteable = false;
        } else {
            externalStorageAvailable = externalStorageWriteable = false;
        }
        return externalStorageAvailable && externalStorageWriteable;
    }
}
