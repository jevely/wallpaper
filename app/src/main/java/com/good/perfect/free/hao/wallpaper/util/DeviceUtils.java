package com.good.perfect.free.hao.wallpaper.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.good.perfect.free.hao.wallpaper.WallpaperApplication;


/**
 * 工具类
 */
public class DeviceUtils {

    //获取navigationbar高度
    private static int getNavigationBarHeight() {
        Resources resources = WallpaperApplication.getWallpaperContext().getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        Log.v("dbw", "Navi height:" + height);
        return height;
    }

    public static int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
