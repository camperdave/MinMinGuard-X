package com.gray101.xposed.minminguardx.adnetwork;

import com.gray101.xposed.minminguardx.Main;
import com.gray101.xposed.minminguardx.Util;

import android.view.View;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.XposedHelpers.ClassNotFoundError;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Vpon {
	public static boolean handleLoadPackage(final String packageName, LoadPackageParam lpparam, final boolean test) {
		try {
			
			Class<?> adView = XposedHelpers.findClass("com.vpon.ads.VponBanner", lpparam.classLoader);
			
			XposedBridge.hookAllMethods(adView, "loadAd" ,new XC_MethodHook() {
						@Override
						protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
							
							Util.log(packageName, "Detect VponBanner loadAd in " + packageName);
							
							if(!test) {
								param.setResult(new Object());
								Main.removeAdView((View) param.thisObject, packageName, true);
							}
						}
					});
			
			Class<?> InterAds = XposedHelpers.findClass("com.vpon.ads.VponInterstitialAd", lpparam.classLoader);
			XposedBridge.hookAllMethods(InterAds, "show" ,new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    
                    Util.log(packageName, "Detect VponInterstitialAd show in " + packageName);
                    
                    if(!test) {
                        param.setResult(new Object());
                    }
                }
            });
			
			Util.log(packageName, packageName + " uses Vpon");
		}
		catch(ClassNotFoundError e) {
			return false;
		}
		return true;
	}
}
