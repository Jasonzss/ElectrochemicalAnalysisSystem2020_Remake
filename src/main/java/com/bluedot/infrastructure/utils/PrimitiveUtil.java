package com.bluedot.infrastructure.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 基本类型工具类
 * @author Jason
 * @creationDate 2023/06/26 - 23:42
 */
public class PrimitiveUtil {
    private static final Map<Class<?>, Class<?>> map = new HashMap<>();

    static {
        map.put(int.class, Integer.class);
        map.put(boolean.class, Boolean.class);
        map.put(long.class, Long.class);
        map.put(byte.class, Byte.class);

        map.put(short.class, Short.class);
        map.put(char.class, Character.class);
        map.put(double.class, Double.class);
        map.put(float.class, Float.class);
    }

    /**
     * 判断两个对象是否为同类型的，如果是基本类型则换做包装类作比较
     * @param a 参数一
     * @param b 参数二
     * @return 是否为同一类型的对象
     */
    public static boolean isSameType(Object a, Object b){
        return isSameType(a.getClass(), b.getClass());
    }

    public static boolean isSameType(Class<?> classA, Class<?> classB){
        return classA == classB || map.getOrDefault(classA, null) == classB || map.getOrDefault(classB, null) == classA;
    }
}
