package com.good.perfect.sex.girl.lwallpaper.activity

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.good.perfect.sex.girl.lwallpaper.BaseActivity
import com.good.perfect.sex.girl.lwallpaper.R
import com.good.perfect.sex.girl.lwallpaper.adapter.MainAdapter
import com.good.perfect.sex.girl.lwallpaper.other.ScaleInTransformer
import com.good.perfect.sex.girl.lwallpaper.util.DataTool
import com.good.perfect.sex.girl.lwallpaper.util.Logger
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    private lateinit var adapter: MainAdapter
    private lateinit var viewpager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DataTool.getInstance().initData(0, 13, true)
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
