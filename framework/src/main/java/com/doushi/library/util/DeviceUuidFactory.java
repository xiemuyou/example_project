package com.doushi.library.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.doushi.library.FApplication;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * @author jeme.
 */
public class DeviceUuidFactory {
    private static final String PREFS_FILE = "device_id.xml";
    private static final String PREFS_DEVICE_ID = "device_id";
    private static final String PREFS_UUID = "device_uuid";
    private UUID uuid;
    private static DeviceUuidFactory instance;
    private static String sUUID = "";
    private Context mContext;

    public static DeviceUuidFactory getInstance() {
        if (instance == null) {
            instance = new DeviceUuidFactory();
        }
        return instance;
    }

    private DeviceUuidFactory() {
        mContext = FApplication.getContext();
        if (uuid == null) {
            synchronized (DeviceUuidFactory.class) {
                if (uuid == null) {
                    final SharedPreferences prefs = mContext.getSharedPreferences(PREFS_FILE, 0);
                    final String id = prefs.getString(PREFS_DEVICE_ID, null);
                    if (id != null) {
                        uuid = UUID.fromString(id);
                    } else {
                        final String androidId = DeviceUtils.getAndroidID();
                        try {
                            //9774d56d682e549c 主流bug，有些厂商生产的每台设备都会产生此id，应排除
                            if (!"9774d56d682e549c".equals(androidId)) {
                                uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
                            } else {
                                final String deviceId = getDeviceID();
                                uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")) : UUID.randomUUID();
                            }
                        } catch (UnsupportedEncodingException e) {
                            throw new RuntimeException(e);
                        }
                        prefs.edit().putString(PREFS_DEVICE_ID, uuid.toString()).commit();
                    }
                }
            }
        }
    }

    public String getDeviceUuid() {
        if (!TextUtils.isEmpty(sUUID)) {
            return sUUID;
        }
        final SharedPreferences prefs = mContext.getSharedPreferences(PREFS_FILE, 0);
        String prefUuid = prefs.getString(PREFS_UUID, "");
        if (!TextUtils.isEmpty(prefUuid)) {
            return prefUuid;
        }
        String filePageName = FileUtils.getInstance().getDesFilePath(FileUtils.FilePathType.PERMANENT);
        String fileName = "unique";
        File file = new File(filePageName, fileName);
        String did = FileUtils.readeFile(file);
        if (TextUtils.isEmpty(did)) {
            did = "_" + EncryptUtils.encryptMD5ToString(
                    uuid.toString() + getDeviceID() + DeviceUtils.getAndroidID());
            prefs.edit().putString(PREFS_UUID, did).apply();
            try {
                FileUtils.writeFile(filePageName, fileName, did);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        sUUID = did;
        return sUUID;
    }

    /**
     * 获取deviceId
     *
     * @return 设备DeviceID
     */
    private String getDeviceID() {
        String deviceId = "";
        try {
            deviceId = PhoneUtils.getDeviceId();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return deviceId;
    }
}
