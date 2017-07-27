package com.trubuzz.trubuzz.utils;

import android.support.annotation.Size;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by king on 2017/7/25.
 */

public class UserStore {
    private static String filePath = "E:\\KING\\" + "user_store.yml";


    /**
     * 获取登录密码
     * @param key
     * @return 若未设置则返回 "" (空值)
     */
    public static String getLoginPassword(String key) {
        Object passwords = getPassword(key);
        if (passwords instanceof String) {
            return (String)passwords;
        }
        if (passwords instanceof List) {
            return (String) ((List) passwords).get(0);
        }
        return "";
    }

    /**
     * 获取交易密码
     * @param key
     * @return 若未设置则返回 "" (空值)
     */
    public static String getTradePassword(String key) {
        Object passwords = getPassword(key);
        if (passwords instanceof List) {
            if (((List) passwords).size() > 1) {
                return (String) ((List) passwords).get(1);
            }
        }
        return "";
    }
    /**
     * 从仓库中拿出密码
     * @param key
     * @return [login password ,trade password] 组合
     */
    public static Object getPassword(String key) {
        Map data = (Map) getData();
        if (data == null) return "";
        return data.get(key);
    }

    /**
     * 获取读取结果
     */
    private static Object getContact() {
        YamlReader reader = null;
        try {
            reader = new YamlReader(new FileReader(filePath));
            return reader.read();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    /**
     * 获取跟当前环境匹配的用户密码数据
     * @return
     */
    private static Object getData() {
        Object contact = getContact();
        if(contact == null || !(contact instanceof Map))    return null;

        Map read = (Map) contact;
        String name = "CN".toLowerCase();
        for (Object key : read.keySet()) {
            if (name.equals(key)) {
                return read.get(key);
            }
        }
        return null;
    }

    public static void updateLoginPassword(String user, String value) {
        List list = new ArrayList();
        list.add(value);
        list.add(null);
        updateValue(user ,list);
    }
    public static void updateTradePassword(String user, String value) {
        List list = new ArrayList();
        list.add(null);
        list.add(value);
        updateValue(user ,list);
    }
    /**
     * 更新value
     * @param key
     * @param value
     */
    private static void updateValue(String key, @Size(2)List value) {
        YamlWriter writer = null;
        try {
            Object contact = getContact();
            if(contact == null || !(contact instanceof Map))    return ;

            Map read = (Map) contact;
            String name = "CN".toLowerCase();
            for (Object cond : read.keySet()) {
                if (name.equals(cond)) {
                    Map data = (Map) read.get(cond);
                    // 更新值 , 若当前 key 不存在则为添加
                    Object old = data.get(key);
                    Object obj = exchangeValue(old, value);
                    data.put(key, obj);
                    break;
                }
            }

            writer = new YamlWriter(new FileWriter(filePath));
            writer.write(read);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(writer != null)
                try {
                    writer.close();
                } catch (YamlException e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 交换新旧值
     * @param old
     * @param values
     * @return
     */
    private static Object exchangeValue(Object old ,List values){
        if (old instanceof List) {
            List newVal = new ArrayList();
            for (int i=0; i< values.size(); i++) {
                if(values.get(i) == null){
                    if(i < ((List) old).size()){
                        newVal.add(i ,((List) old).get(i));
                    }else
                        newVal.add(i ,"$error");
                }

                else{    newVal.add(i ,values.get(i));}
            }
            return newVal;
        }
        if (old instanceof String) {
            if (values.get(1) == null) {
                return values.get(0);
            }
        }
        return values;
    }

}
