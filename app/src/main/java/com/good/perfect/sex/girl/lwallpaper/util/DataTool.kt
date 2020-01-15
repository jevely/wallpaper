package com.good.perfect.sex.girl.lwallpaper.util

import kotlin.random.Random

class DataTool {

    private val name = "tmage_"
    private val imageList = mutableListOf<String>()

    companion object {

        private var dataTool: DataTool? = null

        fun getInstance(): DataTool {
            if (dataTool == null) {
                @Synchronized
                if (dataTool == null) {
                    dataTool =
                        DataTool()
                }

            }
            return dataTool as DataTool
        }

        fun destroy() {
            dataTool = null
        }
    }

    fun initData(startCount: Int, addCount: Int, clean: Boolean) {
        if (clean) {
            imageList.clear()
        }
        while (imageList.size < startCount + addCount) {
            val position = Random.nextInt(499)
            if (position == 0)
                continue
            val url = "https://s3.amazonaws.com/download.filterisq.com/jar/wp/tmage_$position.jpg"
            if (imageList.contains(url)) {
                continue
            }
            imageList.add(url)
        }
    }

    fun getDataSize(): Int {
        return imageList.size
    }

    fun getData(position: Int): String {
        return imageList.get(position)
    }

}