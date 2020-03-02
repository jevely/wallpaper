package com.good.perfect.sex.girl.lwallpaper.util

import com.good.perfect.sex.girl.lwallpaper.R
import com.good.perfect.sex.girl.lwallpaper.content.WallPaperContent
import kotlin.random.Random

class DataTool {

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
        val content1 = WallPaperContent(R.mipmap.tmage_1)
        allList.add(content1)
        val content2 = WallPaperContent(R.mipmap.tmage_2)
        allList.add(content2)
        val content3 = WallPaperContent(R.mipmap.tmage_3)
        allList.add(content3)
        val content4 = WallPaperContent(R.mipmap.tmage_4)
        allList.add(content4)
        val content5 = WallPaperContent(R.mipmap.tmage_5)
        allList.add(content5)
        val content6 = WallPaperContent(R.mipmap.tmage_6)
        allList.add(content6)
        val content7 = WallPaperContent(R.mipmap.tmage_7)
        allList.add(content7)
        val content8 = WallPaperContent(R.mipmap.tmage_8)
        allList.add(content8)
        val content9 = WallPaperContent(R.mipmap.tmage_9)
        allList.add(content9)
        val content10 = WallPaperContent(R.mipmap.tmage_10)
        allList.add(content10)
        val content11 = WallPaperContent(R.mipmap.tmage_11)
        allList.add(content11)
        val content12 = WallPaperContent(R.mipmap.tmage_12)
        allList.add(content12)
        val content13 = WallPaperContent(R.mipmap.tmage_13)
        allList.add(content13)
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
        }
    }

    fun getDataSize(): Int {
        return imageList.size
    }

    fun getData(position: Int): WallPaperContent {
        return imageList[position]
    }

}