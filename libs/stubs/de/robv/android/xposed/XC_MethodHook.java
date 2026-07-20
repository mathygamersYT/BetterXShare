package de.robv.android.xposed;

import java.lang.reflect.Member;

public class XC_MethodHook {
    public XC_MethodHook() {}
    public XC_MethodHook(int priority) {}
    
    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {}
    protected void afterHookedMethod(MethodHookParam param) throws Throwable {}
    
    public static final int PRIORITY_DEFAULT = 50;
    
    public static class MethodHookParam {
        public final Object thisObject;
        public final Object[] args;
        private Object result;
        
        public MethodHookParam() {
            this.thisObject = null;
            this.args = new Object[0];
        }
        
        public Object getResult() { return result; }
        public void setResult(Object result) { this.result = result; }
        public Throwable getThrowable() { return null; }
        public boolean hasThrowable() { return false; }
    }
    
    public static class Unhook {
        public Unhook(Member hookMethod) {}
        public void unhook() {}
    }
}
