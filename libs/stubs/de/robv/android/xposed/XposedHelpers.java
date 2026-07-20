package de.robv.android.xposed;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public final class XposedHelpers {
    public static Class<?> findClass(String className, ClassLoader classLoader) {
        try {
            return Class.forName(className, false, classLoader);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void setStaticObjectField(Class<?> clazz, String fieldName, Object value) {
        try {
            java.lang.reflect.Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(null, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static java.lang.reflect.Method getDeclaredMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        try {
            return clazz.getDeclaredMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static XC_MethodHook.Unhook findAndHookMethod(Class<?> clazz, String methodName, Object... parameterTypesAndCallback) {
        return new XC_MethodHook.Unhook(null);
    }
    
    public static XC_MethodHook.Unhook findAndHookMethod(String className, ClassLoader classLoader, String methodName, Object... parameterTypesAndCallback) {
        return new XC_MethodHook.Unhook(null);
    }
    
    public static XC_MethodHook.Unhook findAndHookConstructor(Class<?> clazz, Object... parameterTypesAndCallback) {
        return new XC_MethodHook.Unhook(null);
    }
    
    public static XC_MethodHook.Unhook findAndHookConstructor(String className, ClassLoader classLoader, Object... parameterTypesAndCallback) {
        return new XC_MethodHook.Unhook(null);
    }
}
