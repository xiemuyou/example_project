package com.library.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author yang
 */
public class JsonUtil {

    /**
     * Json转对象
     */
    public static Object jsonToObject(String json, Class<?> classOfT) {
        Object o;
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        o = gson.fromJson(json, classOfT);
        if (null == o) {
            try {
                o = classOfT.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return o;
    }

    /**
     * JSON转对象  Expose
     * http://blog.csdn.net/u013628152/article/details/50481889
     *
     * @param json
     * @param classOfT
     * @return
     */
    public static Object jsonToObjectExpose(String json, Class<?> classOfT) {
        Object o;
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setDateFormat("yyyy-MM-dd HH:mm:ss").excludeFieldsWithoutExposeAnnotation().create();
        o = gson.fromJson(json, classOfT);
        if (null == o) {
            try {
                o = classOfT.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return o;
    }

    /**
     * Json转对象
     */
    public static Object jsonToObject(String json, Type type) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return gson.fromJson(json, type);
    }

    /**
     * json解析回ArrayList,参数为new TypeToken<ArrayList<T>>() {},必须加泛型
     */
    public static List<?> jsonToList(String json, TypeToken<?> token) {
        return (List<?>) jsonToObject(json, token.getType());
    }

    /**
     * 对象转Json
     */
    public static String objetcToJson(Object object) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create();
        return gson.toJson(object);
    }

    public static JsonArray getJsonArray(List<?> list) {

        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(list, new TypeToken<List<?>>() {
        }.getType());

        if (!element.isJsonArray()) {
// fail appropriately
//            throw new i("错误的类型");
        }

        JsonArray jsonArray = element.getAsJsonArray();
        return jsonArray;
    }
}
