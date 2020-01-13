package com.good.perfect.sex.girl.lwallpaper.activity

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.good.perfect.sex.girl.lwallpaper.BaseActivity
import com.good.perfect.sex.girl.lwallpaper.R
import com.good.perfect.sex.girl.lwallpaper.adapter.MainAdapter
import com.good.perfect.sex.girl.lwallpaper.util.DataTool
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    private lateinit var main_recyclerview: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var adapter: MainAdapter

//    private lateinit var bannerview: NewBanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DataTool.getInstance().initData(0, 50, true)
        init()

//        AgesTool.showStartAd()
//        AgesTool.createShortcut(this, MainActivity::class.java)
    }

    private fun init() {
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        main_recyclerview = findViewById(R.id.main_recyclerview)
//        bannerview = findViewById(R.id.bannerview)
//
//        bannerview.comeBanner()

        main_recyclerview.layoutManager = GridLayoutManager(this, 2)

        adapter = MainAdapter(this)
        main_recyclerview.adapter = adapter

        refreshInit()
    }

    private fun refreshInit() {
        swipeRefreshLayout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            DataTool.getInstance().initData(DataTool.getInstance().getDataSize(), 14, false)
            adapter.notifyDataSetChanged()
            swipeRefreshLayout.isRefreshing = false
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        DataTool.destroy()
//        bannerview.closeBanner()
    }

}
