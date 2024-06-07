package org.devdynamos.utils;

public class ThreadTest {
    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                task1();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                task2();
            }
        });

        t1.start();
        t2.start();

        try{
            t1.join();
            t2.join();

            System.out.println("Both tasks are completed");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private static void task1() {
        int[] arr = new int[1000000000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i + 1;
        }

        System.out.println("task 1 completed");
    }

    private static void task2() {
        int[] arr = new int[100000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i + 1;
        }

        System.out.println("task 2 completed");
    }
}
