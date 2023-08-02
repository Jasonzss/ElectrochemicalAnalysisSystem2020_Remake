package com.bluedot.infrastructure.utils;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jason
 * @creationDate 2023/06/26 - 16:02
 */
public class PojoUtil {
    /**
     * 将updated中的非null字段注入到target中进行覆盖，如果需要使用null覆盖的话需要手动覆盖
     * 另外不推荐pojo内部属性使用基本类型，这样基本类型的默认值可能会被当做修改值用于覆盖原值
     * @param updated 修改后的部分字段组成的对象
     * @param target 待修改的对象
     * @param <T> 修改对象类型
     */
    public static <T> void updatePojo(T updated, T target){
        Map<String, Method> getters = getGetters(updated.getClass());
        Map<String, Method> setters = getSetters(target.getClass());
        getters.forEach((k,v) -> {
            try {
                Object value = v.invoke(updated);

                if(value != null){
                    Method setter = setters.get(k);
                    ReflectUtil.invoke(target, setter, value);
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 获取指定类型指定字段的get方法
     * @param field 指定字段
     * @param clazz 指定类型
     * @param forceReflect 是否使用强制反射获取方法
     * @return get方法
     */
    public static Method getGetter(Field field, Class<?> clazz, boolean forceReflect){
        try {
            String name = generateGetterName(field);
            return forceReflect ? clazz.getDeclaredMethod(name) : clazz.getMethod(name);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取指定类型指定字段的set方法
     * @param field 指定字段
     * @param clazz 指定类型
     * @param forceReflect 是否使用强制反射获取方法
     * @return set方法
     */
    public static Method getSetter(Field field, Class<?> clazz, boolean forceReflect){
        try {
            String name = generateSetterName(field);
            Class<?> type = field.getType();
            return forceReflect ? clazz.getDeclaredMethod(name, type) : clazz.getMethod(name, type);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取指定类型的所有get方法
     * @param clazz 指定类型
     * @return 字段名称和对应get方法的映射集合
     */
    public static Map<String, Method> getGetters(Class<?> clazz){
        return getGetters(clazz, false);
    }
    public static Map<String, Method> getGetters(Class<?> clazz, boolean forceReflect){
        Map<String, Method> getters = new HashMap<>();
        Field[] declaredFields = clazz.getDeclaredFields();
        for(Field f : declaredFields){
            getters.put(f.getName(), getGetter(f, clazz, forceReflect));
        }

        return getters;
    }

    public static Map<String, Method> getSetters(Class<?> clazz){
        return getSetters(clazz, false);
    }
    public static Map<String, Method> getSetters(Class<?> clazz, boolean forceReflect){
        Map<String, Method> getters = new HashMap<>();
        Field[] declaredFields = clazz.getDeclaredFields();
        for(Field f : declaredFields){
            getters.put(f.getName(), getSetter(f, clazz, forceReflect));
        }

        return getters;
    }

    private static String generateGetterName(Field field){
        if(field.getType() == boolean.class || field.getType() == Boolean.class){
            return "is"+StrUtil.upperFirst(field.getName());
        }else {
            return "get"+StrUtil.upperFirst(field.getName());
        }
    }

    private static String generateSetterName(Field field){
        return "set"+StrUtil.upperFirst(field.getName());
    }
}
