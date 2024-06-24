package org.devdynamos.utils;

public class Console {
    public static <T> void log(T content){
        if(content instanceof String){
            System.out.println(content);
            return;
        }
        System.out.println(String.valueOf(content));
    }
}
