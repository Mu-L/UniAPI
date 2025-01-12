package com.burukeyou.uniapi.support.arg;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author caizhihao
 */
public abstract class AbstractParam implements Param {

    protected static Object[] castObjectToArray(Object value) {
        return (Object[]) value;
    }

    @Override
    public boolean isValueNotExist() {
        Class<?> type = getType();
        Object value = getValue();
        if (value == null){
            return true;
        }
        if (String.class.equals(type)){
            if (StringUtils.isBlank(value.toString())){
                return true;
            }
        }

        if (type.isArray() && castObjectToArray(value).length <= 0){
            return  true;
        }

        if (Collection.class.isAssignableFrom(type) &&  ((Collection<?>)value).isEmpty()){
           return true;
        }

        if (Map.class.isAssignableFrom(type) &&  ((Map<?,?>)value).isEmpty()){
           return true;
        }

        return false;
    }

    @Override
    public boolean isValueExist() {
        return !isValueNotExist();
    }

    @Override
    public boolean isNormalValue() {
        Class<?> type = getType();
        if (isObject() || isCollection() || Map.class.isAssignableFrom(type)){
            return false;
        }
        if (type.isPrimitive()){
            return true;
        }
        return true;
    }

    @Override
    public boolean isObject() {
        Class<?> type = getType();
        ClassLoader classLoader = type.getClassLoader();
        if (type.isPrimitive() || type.isEnum()){
            return false;
        }
        if (type.isArray() || Collection.class.isAssignableFrom(type)){
            return false;
        }
        if (classLoader == this.getClass().getClassLoader()){
            return true;
        }
        return false;
    }

    @Override
    public boolean isCollection() {
        Class<?> type = getType();
        if (type.isArray()  || Collection.class.isAssignableFrom(type)){
            return true;
        }
        return false;
    }

    @Override
    public boolean isCollection(Class<?> elementClass) {
        Class<?> clz = getType();
        if (clz.isArray() && elementClass.isAssignableFrom(clz.getComponentType())){
            return true;
        }

        if (Collection.class.isAssignableFrom(clz)) {
            // 能拿到泛型
            Type genericType = getGenericType();
            if (genericType instanceof ParameterizedType) {
                ParameterizedType paramType = (ParameterizedType) genericType;
                Type[] typeArguments = paramType.getActualTypeArguments();
                if (typeArguments.length == 1 && typeArguments[0] instanceof Class) {
                    Class<?> elementType = (Class<?>) typeArguments[0];
                    if (elementClass.isAssignableFrom(elementType)){
                        return true;
                    }
                }
            }

            // 拿不到泛型根据具体值
            Object value = getValue();
            if (value != null){
                Object elementValue = getCollectionFirstValue((Collection<?>) value);
                if (elementValue != null && elementClass.isAssignableFrom(elementValue.getClass())){
                    return true;
                }
            }
        }

        return false;
    }


    private Object getCollectionFirstValue(Collection<?> value) {
        Object elementValue = null;
        Iterator<?> iterator = value.iterator();
        if (iterator.hasNext()){
            elementValue = iterator.next();
        }
        return elementValue;
    }

    @Override
    public <T> List<T> castListValue(Class<T> elementClass) {
        if (!isCollection(elementClass)){
            throw new ClassCastException("cant not cast to List<" + elementClass.getSimpleName() + "> type for " + getGenericType());
        }

        Object value = getValue();
        if (value == null){
            return null;
        }

        List<T> result;
        Class<?> clz = getType();
        if (clz.isArray()) {
            T[] dataArr = (T[]) value;
            result = Arrays.asList(dataArr);
        }else {
            result = new ArrayList<>((Collection<T>)value);
        }

        return result;
    }
}
