package ru.javawebinar.util;

public class LazySingleton {
    //private static final LazySingleton INSTANCE = new LazySingleton();
    int i;
    volatile private static LazySingleton INSTANCE;

    double sin = Math.sin(13.);

    private LazySingleton() {

    }

    private static class LazySingletonHolder {
        private static final LazySingleton INSTANCE = new LazySingleton();

    }

    public static LazySingleton getInstance() {
        return LazySingletonHolder.INSTANCE;
//        if(INSTANCE == null) {
//            synchronized (LazySingleton.class) {
//                if(INSTANCE == null) {
//                    int i = 13;
//                    INSTANCE = new LazySingleton();
//                }
//            }
//        }
//        return INSTANCE;
    }
}
