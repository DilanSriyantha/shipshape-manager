package org.devdynamos.utils;

import java.lang.reflect.Constructor;

public class GenericFactory {
    public static <T> T createInstance(T _class, Object... args){
        try{
            for(Constructor<?> constructor : _class.getClass().getDeclaredConstructors()){
                if(matchConstructor(constructor, args)){
                    T instance = (T)constructor.newInstance(args);
                    return instance;
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    private static boolean matchConstructor(Constructor<?> constructor, Object... args){
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        if(parameterTypes.length != args.length)
            return false;

        for (int i = 0; i < parameterTypes.length; i++) {
            if(!parameterTypes[i].isInstance(args[i]))
                return false;
        }

        return true;
    }
}
