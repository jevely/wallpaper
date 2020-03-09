package com.good.perfect.free.hao.wallpaper.util

import android.util.Log

object Logger {

    private const val TAG = "UUE"
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