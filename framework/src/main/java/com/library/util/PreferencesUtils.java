package com.library.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.library.FApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.library.util.PreferencesUtils.PreferenceType.DEFAULT;

/**
 * Preferences 偏好设置工具类
 *
 * @author xiemy
 * @date 2018/2/28
 */
public class PreferencesUtils {

    public enum PreferenceType {

        //默认类型
        DEFAULT(0),
        //用户类型
        USER(1, "user");

        private int index;
        private String prefName;

        PreferenceType(int index) {
            this.index = index;
        }

        PreferenceType(int index, String prefName) {
            this.index = index;
            this.prefName = prefName;
        }

        public int getIndex() {
            return index;
        }

        public String getPrefName() {
            return prefName;
        }
    }

    /**
     * 示例 PreferencesUtils.setBooleanPreferences("key",false);
     * 保存boolean 类型偏好设置
     *
     * @param key   保存Key名称
     * @param value key对应的值
     * @return 保存是否成功
     */
    public static boolean setBooleanPreferences(String key, boolean value) {
        return setBooleanPreferences(DEFAULT, key, value);
    }

    /**
     * 示例 PreferencesUtils.setBooleanPreferences(PreferenceType.DEFAULT,"key",false);
     * 保存boolean 类型偏好设置
     *
     * @param type  首选项文件名 <>不传或为null,获取默认值</>
     * @param key   保存Key名称
     * @param value key对应的值
     * @return 保存是否成功
     */
    public static boolean setBooleanPreferences(PreferenceType type, String key, boolean value) {
        return edit(type).putBoolean(key, value).commit();
    }

    /**
     * 示例 PreferencesUtils.getBooleanPreferences("key",false);
     *
     * @param key          保存时写入的key值
     * @param defaultValue 返回错误或为空时默认的返回值
     * @return 获取key相应返回值, 返回错误或为空时默认返回值
     */
    public static boolean getBooleanPreferences(String key, boolean defaultValue) {
        return getBooleanPreferences(DEFAULT, key, defaultValue);
    }

    /**
     * 示例 PreferencesUtils.getBooleanPreferences(PreferenceType.DEFAULT,"key",false);
     *
     * @param type         首选项文件名 <>不传或为null,获取默认值</>
     * @param key          保存时写入的key值
     * @param defaultValue 返回错误或为空时默认的返回值
     * @return 获取key相应返回值, 返回错误或为空时默认返回值
     */
    public static boolean getBooleanPreferences(PreferenceType type, String key, boolean defaultValue) {
        return getPreferenceInstance(type).getBoolean(key, defaultValue);
    }

    /**
     * 示例 PreferencesUtils.setFloatPreferences("key",0.1);
     * 保存 float 类型偏好设置
     *
     * @param key   保存Key名称
     * @param value key对应的值
     * @return 保存是否成功
     */
    public static boolean setFloatPreferences(String key, float value) {
        return setFloatPreferences(DEFAULT, key, value);
    }

    /**
     * 示例 PreferencesUtils.setFloatPreferences(PreferenceType.DEFAULT,"key",0.1);
     * 保存 float 类型偏好设置
     *
     * @param type  首选项文件名 <>不传或为null,获取默认值</>
     * @param key   保存Key名称
     * @param value key对应的值
     * @return 保存是否成功
     */
    public static boolean setFloatPreferences(PreferenceType type, String key, float value) {
        return edit(type).putFloat(key, value).commit();
    }

    /**
     * 示例 PreferencesUtils.getFloatPreferences("key",0F);
     *
     * @param key          保存时写入的key值
     * @param defaultValue 返回错误或为空时默认的返回值
     * @return 获取key相应返回值, 返回错误或为空时默认返回值
     */
    public static float getFloatPreferences(String key, float defaultValue) {
        return getFloatPreferences(DEFAULT, key, defaultValue);
    }

    /**
     * 示例 PreferencesUtils.getFloatPreferences(PreferenceType.DEFAULT,"key",0F);
     *
     * @param type         首选项文件名 <>不传或为null,获取默认值</>
     * @param key          保存时写入的key值
     * @param defaultValue 返回错误或为空时默认的返回值
     * @return 获取key相应返回值, 返回错误或为空时默认返回值
     */
    public static float getFloatPreferences(PreferenceType type, String key, float defaultValue) {
        return getPreferenceInstance(type).getFloat(key, defaultValue);
    }

    /**
     * 示例 PreferencesUtils.setFloatPreferences("key",0);
     * 保存 int 类型偏好设置
     *
     * @param key   保存Key名称
     * @param value key对应的值
     * @return 保存是否成功
     */
    public static boolean setIntPreferences(String key, int value) {
        return setIntPreferences(DEFAULT, key, value);
    }

    /**
     * 示例 PreferencesUtils.setIntPreferences(PreferenceType.DEFAULT,"key",0);
     * 保存 int 类型偏好设置
     *
     * @param type  首选项文件名 <>不传或为null,获取默认值</>
     * @param key   保存Key名称
     * @param value key对应的值
     * @return 保存是否成功
     */
    public static boolean setIntPreferences(PreferenceType type, String key, int value) {
        return edit(type).putInt(key, value).commit();
    }

    /**
     * 示例 PreferencesUtils.getFloatPreferences("key",0);
     *
     * @param key          保存时写入的key值
     * @param defaultValue 返回错误或为空时默认的返回值
     * @return 获取key相应返回值, 返回错误或为空时默认返回值
     */
    public static int getIntPreferences(String key, int defaultValue) {
        return getIntPreferences(DEFAULT, key, defaultValue);
    }

    /**
     * 示例 PreferencesUtils.getIntPreferences(PreferenceType.DEFAULT,"key",0);
     *
     * @param type         首选项文件名 <>不传或为null,获取默认值</>
     * @param key          保存时写入的key值
     * @param defaultValue 返回错误或为空时默认的返回值
     * @return 获取key相应返回值, 返回错误或为空时默认返回值
     */
    public static int getIntPreferences(PreferenceType type, String key, int defaultValue) {
        return getPreferenceInstance(type).getInt(key, defaultValue);
    }

    /**
     * 示例 PreferencesUtils.setLongPreferences("key",1L);
     * 保存 long 类型偏好设置
     *
     * @param key   保存Key名称
     * @param value key对应的值 1L
     * @return 保存是否成功
     */
    public static boolean setLongPreferences(String key, long value) {
        return setLongPreferences(DEFAULT, key, value);
    }

    /**
     * 示例 PreferencesUtils.setLongPreferences(PreferenceType.DEFAULT,"key",1L);
     * 保存 long 类型偏好设置
     *
     * @param type  首选项文件名 <>不传或为null,获取默认值</>
     * @param key   保存Key名称
     * @param value key对应的值 1L
     * @return 保存是否成功
     */
    public static boolean setLongPreferences(PreferenceType type, String key, long value) {
        return edit(type).putLong(key, value).commit();
    }

    /**
     * 示例 PreferencesUtils.getLongPreferences("key",1L);
     *
     * @param key          保存时写入的key值
     * @param defaultValue 返回错误或为空时默认的返回值 1L
     * @return 获取key相应返回值, 返回错误或为空时默认返回值
     */
    public static long getLongPreferences(String key, long defaultValue) {
        return getLongPreferences(DEFAULT, key, defaultValue);
    }

    /**
     * 示例 PreferencesUtils.getLongPreferences(PreferenceType.DEFAULT,"key",1L);
     *
     * @param type         首选项文件名 <>不传或为null,获取默认值</>
     * @param key          保存时写入的key值
     * @param defaultValue 返回错误或为空时默认的返回值 1L
     * @return 获取key相应返回值, 返回错误或为空时默认返回值
     */
    public static long getLongPreferences(PreferenceType type, String key, long defaultValue) {
        return getPreferenceInstance(type).getLong(key, defaultValue);
    }

    /**
     * 示例 PreferencesUtils.setStringPreferences("key","value");
     * 保存 String 类型偏好设置
     *
     * @param key   保存Key名称
     * @param value key对应的值 "value"
     * @return 保存是否成功
     */
    public static boolean setStringPreferences(String key, String value) {
        return setStringPreferences(DEFAULT, key, value);
    }

    /**
     * 示例 PreferencesUtils.setStringPreferences(PreferenceType.DEFAULT,"key","value");
     * 保存 String 类型偏好设置
     *
     * @param type  首选项文件名 <>不传或为null,获取默认值</>
     * @param key   保存Key名称
     * @param value key对应的值 "value"
     * @return 保存是否成功
     */
    public static boolean setStringPreferences(PreferenceType type, String key, String value) {
        return edit(type).putString(key, value).commit();
    }

    /**
     * 示例 PreferencesUtils.getStringPreferences("key","value");
     *
     * @param key          保存时写入的key值
     * @param defaultValue 返回错误或为空时默认的返回值 "value"
     * @return 获取key相应返回值, 返回错误或为空时默认返回值
     */
    public static String getStringPreferences(String key, String defaultValue) {
        return getStringPreferences(DEFAULT, key, defaultValue);
    }

    /**
     * 示例 PreferencesUtils.getStringPreferences(PreferenceType.DEFAULT,"key","value");
     *
     * @param type         首选项文件名 <>不传或为null,获取默认值</>
     * @param key          保存时写入的key值
     * @param defaultValue 返回错误或为空时默认的返回值 "value"
     * @return 获取key相应返回值, 返回错误或为空时默认返回值
     */
    public static String getStringPreferences(PreferenceType type, String key, String defaultValue) {
        return getPreferenceInstance(type).getString(key, defaultValue);
    }

    /**
     * 示例
     * Map<String,Object> map = new HashMap<>();
     * map.put("key",o);
     * ...
     * Set<String> set = map.keySet();
     * PreferencesUtils.setSetPreferences("key",set);
     * 保存 Set<String> 类型偏好设置
     *
     * @param key   保存Key名称
     * @param value key对应的值 Set<String> set
     * @return 保存是否成功
     */
    @SuppressLint("NewApi")
    public static boolean setSetPreferences(String key, Set<String> value) {
        return setSetPreferences(DEFAULT, key, value);
    }

    /**
     * 示例
     * Map<String,Object> map = new HashMap<>();
     * map.put("key",o);
     * ...
     * Set<String> set = map.keySet();
     * PreferencesUtils.setSetPreferences("key",set);
     * 保存 Set<String> 类型偏好设置
     *
     * @param type  首选项文件名 <>不传或为null,获取默认值</>
     * @param key   保存Key名称
     * @param value key对应的值 Set<String> set
     * @return 保存是否成功
     */
    @SuppressLint("NewApi")
    public static boolean setSetPreferences(PreferenceType type, String key, Set<String> value) {
        return edit(type).putStringSet(key, value).commit();
    }

    /**
     * 示例
     * PreferencesUtils.getStringSetPreferences("key",null);
     *
     * @param key          保存时写入的key值
     * @param defaultValue 返回错误或为空时默认的返回值 null
     * @return 获取key相应返回值, 返回错误或为空时默认返回值
     */
    @SuppressLint("NewApi")
    public static Set<String> getStringSetPreferences(String key, Set<String> defaultValue) {
        return getStringSetPreferences(DEFAULT, key, defaultValue);
    }

    /**
     * 示例
     * PreferencesUtils.getStringSetPreferences("key",null);
     *
     * @param type         首选项文件名 <>不传或为null,获取默认值</>
     * @param key          保存时写入的key值
     * @param defaultValue 返回错误或为空时默认的返回值 null
     * @return 获取key相应返回值, 返回错误或为空时默认返回值
     */
    @SuppressLint("NewApi")
    public static Set<String> getStringSetPreferences(PreferenceType type, String key, Set<String> defaultValue) {
        return getPreferenceInstance(type).getStringSet(key, defaultValue);
    }

    /**
     * 删除偏好设置里对应 key 的值
     *
     * @param key 保存时写入key值
     * @return 删除是否成功
     */
    public static boolean remove(String key) {
        return remove(DEFAULT, key);
    }

    /**
     * 删除偏好设置里对应 key 的值
     *
     * @param type 首选项文件类型
     *             <>不传或为null,获取默认值</>
     * @param key  保存时写入key值
     * @return 删除是否成功
     */
    public static boolean remove(PreferenceType type, String key) {
        return edit(type).remove(key).commit();
    }

    /**
     * 清除默认的偏好设置
     */
    public static void clear() {
        clear(DEFAULT);
    }

    /**
     * 根据偏好设置类型删除
     *
     * @param type 偏好设置类型
     */
    public static void clear(PreferenceType type) {
        edit(type).clear();
    }

    private static List<SharedPreferences> preferencesList;

    private static SharedPreferences getPreferenceInstance(PreferenceType type) {
        SharedPreferences preference;
        if (type == null || DEFAULT == type) {
            preference = getPreference(0, null);
        } else {
            preference = getPreference(type.getIndex(), type.getPrefName());
        }
        return preference;
    }

    private static SharedPreferences getPreference(int index, String prefName) {
        if (preferencesList == null) {
            synchronized (PreferencesUtils.class) {
                if (preferencesList == null) {
                    int length = PreferenceType.values().length;
                    preferencesList = new ArrayList<>(length);
                    for (int i = 0; i < length; i++) {
                        setPreference(i, prefName);
                    }
                }
            }
        }
        return getPreference(index);
    }

    /**
     * 获取对应单例
     *
     * @param index
     * @return
     */
    private static SharedPreferences getPreference(int index) {
        return preferencesList.get(index);
    }

    private static void setPreference(int index, String prefName) {
        if (index == 0) {
            preferencesList.add(index, PreferenceManager.getDefaultSharedPreferences(FApplication.getContext()));
        } else {
            preferencesList.add(index, FApplication.getFApplication().getSharedPreferences(prefName, Context.MODE_PRIVATE));
        }
    }

    private static Editor edit(PreferenceType type) {
        return getPreferenceInstance(type).edit();
    }
}
