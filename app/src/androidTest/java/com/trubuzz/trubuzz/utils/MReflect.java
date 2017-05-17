package com.trubuzz.trubuzz.utils;

import java.lang.reflect.Field;

/**
 * Created by king on 16/11/23.
 */

public class MReflect {

    /**
     * 获取对象的所有定义的属性
     * @param object
     * @return
     */
    public static Field[] getDecFields(Object object){
        Class clz = object.getClass();
        Field [] fields = clz.getDeclaredFields();
        Field.setAccessible(fields , true);
        return fields;
    }

    /**
     * 匹配指定属性
     * @param fields
     * @param fieldName
     * @return
     */
    public static Field matcherField(Field[] fields , String fieldName){
        for(Field field : fields){
            if(field.getName().equals(fieldName))
                return field ;
        }
        return null;
    }

    /**
     * 获取属性的值
     * @param field
     * @param object
     * @return
     * @throws IllegalAccessException
     */
    public static Object getFieldObject(Field field , Object object) throws IllegalAccessException {
        return field.get(object);
    }
    public static Object getFieldObject(String fieldName , Object object) throws IllegalAccessException {
        return matcherField(getDecFields(object) , fieldName).get(object);
    }

    /**
     * 通过 Class 获取对象
     * @param clz
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T> T getObject(Class<T> clz) throws IllegalAccessException, InstantiationException {
        return clz.newInstance();
    }
    public static <T> T getObject(Class<T> clz ,Object ...obj) throws IllegalAccessException, InstantiationException {
        return null;
    }
}
