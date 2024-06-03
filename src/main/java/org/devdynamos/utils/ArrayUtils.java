package org.devdynamos.utils;

import org.devdynamos.interfaces.FindIteratorCallback;
import org.devdynamos.interfaces.MapIteratorCallback;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
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

        for (int i = 0; i < array.length; i++) {
            T element = callback.execute(lastObj, array[i]);
            if(element != null){
                lastObj = element;
            }
        }

        return lastObj;
    }

    public static <T> T find(List<T> list, FindIteratorCallback<T> callback){
        @SuppressWarnings("unchecked")
        T[] array = list.toArray((T[]) new Object[0]);

        return find(array, callback);
    }
}
