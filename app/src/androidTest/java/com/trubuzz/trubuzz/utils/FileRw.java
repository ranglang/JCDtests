package com.trubuzz.trubuzz.utils;

import android.util.Log;

import com.trubuzz.trubuzz.constant.Conf;
import com.trubuzz.trubuzz.constant.Env;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import static com.trubuzz.trubuzz.constant.Env.TAG;

/**
 * Created by king on 2017/6/26.
 */

public class FileRw {

    /**
     * 将内容写入指定的属性文件 ,
     * 此方法为覆盖性写入 , 不支持更改或更新值
     *
     * @param fileName
     * @param property
     */
    public static void saveProperties(String fileName, List<Kvp<String, String>> property) {
        String propertiesPath = Env.filesDir + fileName;
        Properties prop = new Properties();
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(propertiesPath);
            for (Kvp<String, String> p : property) {
                prop.setProperty(p.getKey(), p.getValue());
            }
            prop.store(out, null);
        } catch (IOException e) {
            System.err.println("Failed to open app.properties file");
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 从属性文件中读取指定的key 的内容
     *
     * @param fileName
     * @param key
     * @return
     */
    public static String readProperty(String fileName, String key) {
        String propertiesPath = Env.filesDir + fileName;
        Properties prop = new Properties();
        FileInputStream in = null;
        String value = "";
        try {
            in = new FileInputStream(propertiesPath);
            prop.load(in);
            value = prop.getProperty(key);
            Log.i(TAG, String.format("readProperty: %s = %s", key, value));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    public static Kvp<String, String> getLogCookie(String fileName){
        String cvalue = readProperty(fileName, Conf.ad_log_cookie_key);
        if (cvalue.isEmpty()) {
            return Conf.ad_log_default_cookie;
        }
        return new Kvp<>(Conf.ad_log_cookie_key, cvalue);
    }
}