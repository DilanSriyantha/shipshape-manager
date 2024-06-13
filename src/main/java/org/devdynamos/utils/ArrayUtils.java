package org.devdynamos.utils;

import org.devdynamos.interfaces.FilterIteratorCallback;
import org.devdynamos.interfaces.FindIteratorCallback;
import org.devdynamos.interfaces.MapIteratorCallback;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ArrayUtils {
    public static <T> int indexOf(T[] array, T targetObject){
        int index = -1;
        for (int i = 0; i < array.length; i++) {
            if(array[i].equals(targetObject)){
                index = i;
                break;
            }
        }

        return index;
    }

    public static <T> int indexOf(List<T> list, T targetObject){
        @SuppressWarnings("unchecked")
        T[] array = list.toArray((T[])new Object[0]);

        return indexOf(array, targetObject);
    }

    // this method accepts only class types
    // so if you want to map integers then you have to use Integer[] instead of primitive type;
    public static <T> T[] map(T[] array, MapIteratorCallback<T> callback){
        @SuppressWarnings("unchecked")
        T[] mappedArray = (T[]) Array.newInstance(array[0].getClass(), array.length);

        for (int i = 0; i < array.length; i++) {
            T element = callback.execute(array[i]);
            if(element != null){
                mappedArray[i] = element;
            }
        }

        return mappedArray;
    }

    public static <T> T find(T[] array, FindIteratorCallback<T> callback) {
        @SuppressWarnings("unchecked")
        T lastObj = array[0];

        for (T e : array) {
            T element = callback.execute(lastObj, e);
            if(element != null)
                lastObj = element;
        }

        return lastObj;
    }

    public static <T> T find(List<T> list, FindIteratorCallback<T> callback){
        @SuppressWarnings("unchecked")
        T[] array = list.toArray((T[]) new Object[0]);

        return find(array, callback);
    }

    public static <T> T[] filter(T[] array, FilterIteratorCallback<T> callback){
        List<T> resultList = new ArrayList<>();

        for(T e : array){
            T element = callback.execute(e);
            if(element != null)
                resultList.add(element);
        }

        @SuppressWarnings("unchecked")
        T[] resultArray = resultList.toArray((T[])Array.newInstance(array[0].getClass(), resultList.size()));

        return resultArray;
    }

    public static <T> T[] filter(List<T> list, FilterIteratorCallback<T> callback){
        @SuppressWarnings("unchecked")
        T[] array = list.toArray((T[]) new Object[0]);

        return filter(array, callback);
    }
}
