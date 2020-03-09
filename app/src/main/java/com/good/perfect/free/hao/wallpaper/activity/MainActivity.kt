package com.good.perfect.free.hao.wallpaper.activity

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.good.perfect.free.hao.wallpaper.BaseActivity
import com.good.perfect.free.hao.wallpaper.R
import com.good.perfect.free.hao.wallpaper.adapter.MainAdapter
import com.good.perfect.free.hao.wallpaper.other.ScaleInTransformer
import com.good.perfect.free.hao.wallpaper.util.DataTool


class MainActivity : BaseActivity() {

    private lateinit var adapter: MainAdapter
    private lateinit var viewpager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DataTool.getInstance().initData(0, 15, true)
        init()
    }

    private fun init() {
        viewpager = findViewById(R.id.viewpager)

        adapter = MainAdapter(this)
        viewpager.adapter = adapter

        viewpager.apply {
            offscreenPageLimit = 1
            val recyclerView = getChildAt(0) as RecyclerView
            recyclerView.apply {
                val padding = resources.getDimensionPixelOffset(R.dimen.dp_10) +
                        resources.getDimensionPixelOffset(R.dimen.dp_10)
                // setting padding on inner RecyclerView puts overscroll effect in the right place
                setPadding(padding, 0, padding, 0)
                clipToPadding = false
            }
        }
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(ScaleInTransformer())
        compositePageTransformer.addTransformer(MarginPageTransformer(resources.getDimension(R.dimen.dp_10).toInt()))
        viewpager.setPageTransformer(compositePageTransformer)

    }

    override fun onDestroy() {
        super.onDestroy()
        DataTool.destroy()
    }

}
