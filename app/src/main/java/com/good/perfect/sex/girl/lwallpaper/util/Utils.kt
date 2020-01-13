package com.good.perfect.sex.girl.lwallpaper.util

import android.annotation.TargetApi
import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Environment
import com.good.perfect.sex.girl.lwallpaper.R
import com.good.perfect.sex.girl.lwallpaper.WallpaperApplication
import java.io.File
import java.io.FileOutputStream

fun getScreen(): Point {
    val dm = WallpaperApplication.getWallpaperContext().resources.displayMetrics
    val point = Point(dm.widthPixels, dm.heightPixels)
    return point
}

//val saveFloadAddress =
//    Environment.getExternalStorageDirectory().absolutePath + File.separator + WallpaperApplication.getWallpaperContext().resources.getString(
//        R.string.app_name
//    ) + File.separator

val saveFloadAddress =
    Environment.getExternalStorageDirectory().absolutePath + File.separator+"Uwallpaper"+File.separator

//本地读取图片
fun getWallpaperFromLocal(path: String): Bitmap {
    val bitmap = BitmapFactory.decodeFile(path)
    return bitmap
}

/**
 * 删除文件
 */
fun deleteFile(file: File): Boolean {
    if (file.isFile) {
        file.delete()
    } else if (file.isDirectory) {
        val files = file.listFiles()
        for (file1 in files) {
            deleteFile(file1)
        }
    }
    return file.exists()
}

//获取原有壁纸
fun getWallpaper(): Bitmap {
    val wallpaperManager = WallpaperManager.getInstance(WallpaperApplication.getWallpaperContext())
    val wallpaperDrawable = wallpaperManager.drawable
    // 将Drawable,转成Bitmap
    val bm = (wallpaperDrawable as BitmapDrawable).bitmap
    return bm
}

//设置桌面壁纸
fun setBackWallpaper(bitmap: Bitmap) {
    try {
        val wallpaperManager =
            WallpaperManager.getInstance(WallpaperApplication.getWallpaperContext())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            val baos = ByteArrayOutputStream()
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
//            val inputStream = ByteArrayInputStream(baos.toByteArray())

            wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM)
        } else {
            wallpaperManager.setBitmap(bitmap)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

//设置锁屏壁纸
@TargetApi(Build.VERSION_CODES.N)
fun setLockWallpaper7(bitmap: Bitmap) {
    try {
//        val baos = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
//        val inputStream = ByteArrayInputStream(baos.toByteArray())

        val wallpaperManager =
            WallpaperManager.getInstance(WallpaperApplication.getWallpaperContext())

        wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

/**
 * 同时设置桌面，锁屏壁纸
 */
@TargetApi(Build.VERSION_CODES.N)
fun setAllWallpaper(bitmap: Bitmap) {
    try {
        val wallpaperManager =
            WallpaperManager.getInstance(WallpaperApplication.getWallpaperContext())

        wallpaperManager.setBitmap(
            bitmap,
            null,
            true,
            WallpaperManager.FLAG_LOCK or WallpaperManager.FLAG_SYSTEM
        )
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
