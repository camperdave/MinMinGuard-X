package com.gray101.xposed.minminguardx.adnetwork;

import com.gray101.xposed.minminguardx.Main;
import com.gray101.xposed.minminguardx.Util;

import android.view.View;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.XposedHelpers.ClassNotFoundError;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Adfurikun {
    public static boolean handleLoadPackage(final String packageName, LoadPackageParam lpparam, final boolean test) {
        try {
            Class<?> adView = XposedHelpers.findClass("jp.tjkapp.adfurikunsdk.AdfurikunBase", lpparam.classLoader);
            XposedBridge.hookAllMethods(adView, "a", new XC_MethodHook() {
                
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    Util.log(packageName, "Detect AdfurikunAdView update in " + packageName);
                    
                    if(!test) {
                        param.setResult(new Object());
                        Main.removeAdView((View) param.thisObject, packageName, true);
                    }
                }
                
            });
            Util.log(packageName, packageName + " uses Adfurikun");
        }
        catch(ClassNotFoundError e) {
            return false;
        }
        return true;
    }
}
