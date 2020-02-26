package com.good.perfect.sex.girl.lwallpaper.util

import com.good.perfect.sex.girl.lwallpaper.content.WallPaperContent
import kotlin.random.Random

class DataTool {

    private val name = "tmage_"
    private val imageList = mutableListOf<WallPaperContent>()
    val allList = mutableListOf<WallPaperContent>()

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

    fun init() {
        for (i in 1..499) {
            val url = "https://s3.amazonaws.com/download.filterisq.com/jar/wp/tmage_$i.jpg"
            val content = WallPaperContent(url)
            allList.add(content)
        }
    }

    fun initData(startCount: Int, addCount: Int, clean: Boolean) {

        if (allList.isEmpty()) {
            return
        }

        if (clean) {
            imageList.clear()
        }

        while (imageList.size < startCount + addCount) {
            val position = Random.nextInt(allList.size)
            val content = allList[position]
            imageList.add(content)
            allList.remove(content)
//            if (position == 0)
//                continue
//            val url = "https://s3.amazonaws.com/download.filterisq.com/jar/wp/tmage_$position.jpg"
//            val content = WallPaperContent(url)
//            if (imageList.contains(url)) {
//                continue
//            }
//            imageList.add(content)
        }
    }

    fun getDataSize(): Int {
        return imageList.size
    }

    fun getData(position: Int): WallPaperContent {
        return imageList[position]
    }

}