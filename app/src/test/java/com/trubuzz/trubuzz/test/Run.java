package com.trubuzz.trubuzz.test;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;
import com.trubuzz.trubuzz.utils.AdminUtilU;
import com.trubuzz.trubuzz.utils.God;
import com.trubuzz.trubuzz.utils.Param;
import com.trubuzz.trubuzz.utils.UserStore;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by king on 2016/10/20.
 */
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Run {
    boolean a;
    HtmlParser htmlParser = new HtmlParser();
    AdminUtilU au = new AdminUtilU();
    String fn = "E:\\KING\\user_store.yml";

    @Test
    public void brun(){
        String s = "sdlfj.jljsfd,";
        String[] split = s.split("\\.");

        System.out.println(Arrays.toString(split));
    }

    public void updateString(String s) {
        s = new String(new StringBuilder("aaa"));
    }
    /**
     * 获取一个随机整数
     * @param max
     * @param min
     * @return
     */
    public static int getRandomInt(int max, int min) {
        if(max == 0){
            return 0;
        }
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }

    public static String getNumberFromString(String str, int max) {
        String regEx = String.format("\\d{%s}",max);
        Pattern p = Pattern.compile(regEx);
        Matcher matcher = p.matcher(str);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }
    public static String getNumberFromString(String str) {
        Pattern p = Pattern.compile("[0-9]\\d*");
        Matcher matcher = p.matcher(str);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }
    public static String getRandomLoginPwd(int length) {
        char number = (char) getRandomInt(57, 48);
        char upChar = (char) getRandomInt(90, 65);
        char lowChar = (char) getRandomInt(112, 97);
        char[] chars = {number, upChar, lowChar};
        return Arrays.toString(chars);
    }
    public void arun(){
        UserStore.updateLoginPassword("11899990001" ,"12321");
        String loginPassword = UserStore.getLoginPassword("11899990001");
        System.out.println(loginPassword);

    }
//    @Test
    public void xrun() throws NoSuchMethodException {
        InputStream in = null;
        try {
            in = new FileInputStream(new File(fn));
            YamlReader reader = new YamlReader(new InputStreamReader(in));
            Map contact = (Map) reader.read();
            if (contact != null) {
                Map map = (Map) contact.get("cn");
                for (Object key : map.keySet()) {
                    if (key.equals("11899990001")) {
                        map.put(key, "1233");
                        break;
                    }
                }
            }
            rwyml(fn, contact);

        } catch (Exception e) {
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
    }
    public void rwyml(String file , Object o) throws IOException {
        YamlWriter writer = new YamlWriter(new FileWriter(fn));
        writer.write(o);
        writer.close();
    }
    public static Object[] list2array(List list) {
        int size = list.size();
        if (size < 1) {
            return null;
        }
        Object[] es =  new Object[size];
        for(int i=0; i<size ; i++) {
            es[i] = list.get(i);
        }
        return es;
    }
    private String formatString(String s ,String p_s) {
//        String[] split = s.split(p_s);
//        System.out.println(split.length);
//        String[] ss = split[1].split(",");
//        String format = String.format(split[0], ss);
//        System.out.println(format);
        return "";
    }
    public void tc(){
    }
    public void ts(){
    }
    public void tt(){
        String login_url = "https://tb2devadmin.trubuzz.com/login";
        au.getNowCookie(login_url);
    }
    public void tr(){
        String str = "demo@qq.com";
        String pattern = ".*o.?";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        System.out.println(m.matches());
    }

    private void random(int max , int min){
        Random r = new Random();
        for (int i = 0;i<20;i++) {
            int s = r.nextInt(max-min+1)+min;
            System.out.println(s);
        }
    }
    public static String conversionScale(long i , int scale){
        char[] chars = new char[scale];
        char ch = 'A';
        char nu = '0';
        long m = 0;  //商
        long y = 0;  //余
        int index = chars.length;
        long c = 0;  //差

        do{
            m = i / scale;
            y = i % scale;
            c = y - 10;
            chars[--index] = (char) (c < 0 ? nu + y : ch + c);
            i = m;
        }while (m!=0);

        return new String(chars ,index ,chars.length-index);
    }

    private void j16(){
        long d = new Date().getTime();
        long l = 31536000000l;
        long y = (366l * 24l * 60l * 60l *1000l) * 47l;
        long m = 31l*24l*60l*60l*1000l*5l;
        System.out.println(l);
        System.out.println("d = "+d);
        System.out.println("y = "+y);
        System.out.println("m = "+m);

        System.out.println(Long.toHexString(d));
        System.out.println(Long.toHexString(y));
        System.out.println(Long.toHexString(m));
    }
    private Map tm(){
        Map map = new HashMap();
        Map map1 = new HashMap();
        map1.put("hello" , 1);
        Map map2 = null;

        map.putAll(map2);
        System.out.println(1);
        return map1;
    }
    private String[] getParamsName(Method method){
        Annotation[][] ass = method.getParameterAnnotations();
        String[] parameterNames = new String[ass.length];
        int i = 0;
        for(Annotation[] as : ass){
            for(Annotation a : as){
                if (a instanceof Param){
                    parameterNames[i++] = ((Param)a).value();
                }
            }
        }
        return parameterNames;
    }
}

