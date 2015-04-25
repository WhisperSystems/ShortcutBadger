package me.leolin.shortcutbadger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.util.Log;

import me.leolin.shortcutbadger.impl.*;

/**
 * Created with IntelliJ IDEA.
 * User: leolin
 * Date: 2013/11/14
 * Time: 下午5:51
 * To change this template use File | Settings | File Templates.
 */
public abstract class ShortcutBadger {
    private static final String HOME_PACKAGE_ANDROID1 = "com.android.launcher";
    private static final String HOME_PACKAGE_ANDROID2 = "com.android.launcher2";
    private static final String HOME_PACKAGE_ANDROID3 = "com.google.android.googlequicksearchbox";
    private static final String HOME_PACKAGE_SONY    = "com.sonyericsson.home";
    private static final String HOME_PACKAGE_SAMSUNG1 = "com.sec.android.app.launcher";
    private static final String HOME_PACKAGE_SAMSUNG2 = "com.sec.android.app.twlauncher";
    private static final String HOME_PACKAGE_LG1      = "com.lge.launcher";
    private static final String HOME_PACKAGE_LG2      = "com.lge.launcher2";
    private static final String HOME_PACKAGE_HTC      = "com.htc.launcher";
    private static final String HOME_PACKAGE_APEX     = "com.anddoes.launcher";
    private static final String HOME_PACKAGE_ADW      = "org.adw.launcher";
    private static final String HOME_PACKAGE_ADW_EX   = "org.adwfreak.launcher";
    private static final String HOME_PACKAGE_NOVA     = "com.teslacoilsw.launcher";
    private static final String HOME_PACKAGE_SOLID    = "com.majeur.launcher";

//    private static final String MESSAGE_NOT_SUPPORT_BADGE_COUNT = "ShortBadger is currently not support the badgeCount \"%d\"";
//    private static final String MESSAGE_NOT_SUPPORT_THIS_HOME = "ShortcutBadger is currently not support the home launcher package \"%s\"";

    private static final int MIN_BADGE_COUNT = 0;
    private static final int MAX_BADGE_COUNT = 99;

    private static ShortcutBadger mShortcutBadger;

//    private ShortcutBadger() {
//    }

    protected Context mContext;

    protected ShortcutBadger(Context context) {
        this.mContext = context;
    }

    protected abstract void executeBadge(int badgeCount) throws ShortcutBadgeException;

    public static void setBadge(Context context, int badgeCount) {
        badgeCount = Math.min(Math.max(MIN_BADGE_COUNT, badgeCount), MAX_BADGE_COUNT);

        //find the home launcher Package
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        ResolveInfo resolveInfo = context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        String currentHomePackage = resolveInfo.activityInfo.packageName;

        try {
            //different home launcher packages use different way adding badges
            ShortcutBadger shortcutBadger = getShortcutBadger(currentHomePackage, context);

            //not support this home launcher package
            if (shortcutBadger == null) {
                Log.w("ShortcutBadger", "No support for: " + currentHomePackage);
            } else {
                shortcutBadger.executeBadge(badgeCount);
            }
        } catch (Throwable e) {
            Log.w("ShortcutBadger", e);
        }

    }

    private static ShortcutBadger getShortcutBadger(String currentHomePackage, Context context){
        if(mShortcutBadger != null) {
            return mShortcutBadger;
        }

        // Workaround for Meizu:
        // Meizu declare 'com.android.launcher', but hold something else
        // Icons get duplicated on restart after badge change
        if(Build.MANUFACTURER.toLowerCase().contains("meizu")) {
            return null;
        }

        if (Build.MANUFACTURER.equalsIgnoreCase("Xiaomi")) {
            //"com.miui.miuilite", "com.miui.miuihome", "com.miui.miuihome2"
            mShortcutBadger = new XiaomiHomeBadger(context);
            return mShortcutBadger;
        }

        switch (currentHomePackage){
            case HOME_PACKAGE_ANDROID1:
            case HOME_PACKAGE_ANDROID2:
            case HOME_PACKAGE_ANDROID3:
                mShortcutBadger = null;
                break;
            case HOME_PACKAGE_SONY:
                mShortcutBadger = new SonyHomeBadger(context);
                break;
            case HOME_PACKAGE_SAMSUNG1:
            case HOME_PACKAGE_SAMSUNG2:
                mShortcutBadger = new SamsungHomeBadger(context);
                break;
            case HOME_PACKAGE_LG1:
            case HOME_PACKAGE_LG2:
                mShortcutBadger = new LGHomeBadger(context);
                break;
            case HOME_PACKAGE_HTC:
                mShortcutBadger = new NewHtcHomeBadger(context);
                break;
            case HOME_PACKAGE_APEX:
                mShortcutBadger = new ApexHomeBadger(context);
                break;
            case HOME_PACKAGE_ADW:
            case HOME_PACKAGE_ADW_EX:
                mShortcutBadger = new AdwHomeBadger(context);
                break;
            case HOME_PACKAGE_NOVA:
                mShortcutBadger = new NovaHomeBadger(context);
                break;
            case HOME_PACKAGE_SOLID:
                mShortcutBadger = new SolidHomeBadger(context);
                break;
        }
        return mShortcutBadger;
    }

    protected String getEntryActivityName() {
        ComponentName componentName = mContext.getPackageManager().getLaunchIntentForPackage(mContext.getPackageName()).getComponent();
        return componentName.getClassName();
    }

    protected String getContextPackageName() {
        return mContext.getPackageName();
    }
}
