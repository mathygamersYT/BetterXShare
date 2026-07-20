package de.robv.android.xposed.callbacks;

import android.content.Context;

public final class XC_LoadPackage {
    public static final class LoadPackageParam {
        public final String packageName;
        public final ClassLoader classLoader;
        public final Context appContext;
        
        public LoadPackageParam(String packageName, ClassLoader classLoader, Context appContext) {
            this.packageName = packageName;
            this.classLoader = classLoader;
            this.appContext = appContext;
        }
    }
}
