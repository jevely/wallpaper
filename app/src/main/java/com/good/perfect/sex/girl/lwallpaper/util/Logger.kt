package com.good.perfect.sex.girl.lwallpaper.util

import android.util.Log

object Logger {

    private const val TAG = "LJW"
    private const val DEBUG = true

    fun d(content : String){
        if(DEBUG)
            Log.d(TAG,content)
    }

    fun e(content : String){
        if(DEBUG)
            Log.e(TAG,content)
    }

}