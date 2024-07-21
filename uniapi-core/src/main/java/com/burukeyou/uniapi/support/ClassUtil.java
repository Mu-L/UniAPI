package com.burukeyou.uniapi.support;

import lombok.extern.slf4j.Slf4j;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.*;

@Slf4j
public class ClassUtil {

    private ClassUtil(){}

    private static final int ALLOWED_MODES = MethodHandles.Lookup.PRIVATE | MethodHandles.Lookup.PROTECTED
            | MethodHandles.Lookup.PACKAGE | MethodHandles.Lookup.PUBLIC;

    private static Constructor<MethodHandles.Lookup> java8LookupConstructor;
    private static Method privateLookupInMethod;

    public static Type[] getSuperClassActualTypeArguments(Class<?> clz){
        Type superclass = clz.getGenericSuperclass();
        if (superclass instanceof ParameterizedType){
            return  ((ParameterizedType)superclass).getActualTypeArguments();
        }
        throw new IllegalArgumentException("can not find super class argument");
    }

    public static ParameterizedType getSuperInterfacesParameterizedType(Class<?> clazz,Class<?> genericInterfaceClass) {
        Class<?> current = clazz;
        ParameterizedType genericClassParameterizedType = null;
        while (current != null) {
            Type[] genericInterfaces = current.getGenericInterfaces();
            if (genericInterfaces.length <= 0){
                current = current.getSuperclass();
                continue;
            }

            for (Type genericInterface : genericInterfaces) {
                if (genericInterface instanceof ParameterizedType){
                    ParameterizedType parameterizedType =  ((ParameterizedType)genericInterface);
                    if (genericInterfaceClass.equals(parameterizedType.getRawType())){
                        genericClassParameterizedType = parameterizedType;
                    }
                }
            }

            if (genericClassParameterizedType != null){
                break;
            }
            current = current.getSuperclass();
        }
        return genericClassParameterizedType;
    }


    static {
        //先查询jdk9 开始提供的java.lang.invoke.MethodHandles.privateLookupIn方法,
        //如果没有说明是jdk8的版本.(不考虑jdk8以下版本)
        try {
            privateLookupInMethod = MethodHandles.class.getMethod("privateLookupIn", Class.class, MethodHandles.Lookup.class);
        } catch (NoSuchMethodException e) {
            privateLookupInMethod = null;
            log.info("There is no [java.lang.invoke.MethodHandles.privateLookupIn(Class, Lookup)] method in this version of JDK");
        }
        //jdk8
        //这种方式其实也适用于jdk9及以上的版本,但是上面优先,可以避免 jdk9 反射警告
        if (privateLookupInMethod == null) {
            try {
                java8LookupConstructor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class, int.class);
                java8LookupConstructor.setAccessible(true);
            } catch (NoSuchMethodException e) {
                //可能是jdk8 以下版本
                throw new IllegalStateException(
                        "There is neither 'privateLookupIn(Class, Lookup)' nor 'Lookup(Class, int)' method in java.lang.invoke.MethodHandles.",
                        e);
            }
        }
    }

    /**
     * java9中的MethodHandles.lookup()方法返回的Lookup对象
     * 有权限访问specialCaller != lookupClass()的类
     * 但是只能适用于接口, {@link java.lang.invoke.MethodHandles.Lookup#checkSpecialCaller}
     */
    public static MethodHandles.Lookup lookup(Class<?> callerClass) {
        //使用反射,因为当前jdk可能不是java9或以上版本
        if (privateLookupInMethod != null) {
            try {
                return (MethodHandles.Lookup) privateLookupInMethod.invoke(MethodHandles.class, callerClass, MethodHandles.lookup());
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
        //jdk 8
        try {
            return java8LookupConstructor.newInstance(callerClass, ALLOWED_MODES);
        } catch (Exception e) {
            throw new IllegalStateException("no 'Lookup(Class, int)' method in java.lang.invoke.MethodHandles.", e);
        }
    }

    public static MethodHandle getSpecialMethodHandle(Method parentMethod) {
        final Class<?> declaringClass = parentMethod.getDeclaringClass();
        MethodHandles.Lookup lookup = lookup(declaringClass);
        try {
            return lookup.unreflectSpecial(parentMethod, declaringClass);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
