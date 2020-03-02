package com.good.perfect.sex.girl.lwallpaper.activity

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.good.perfect.sex.girl.lwallpaper.BaseActivity
import com.good.perfect.sex.girl.lwallpaper.R
import com.good.perfect.sex.girl.lwallpaper.adapter.MainAdapter
import com.good.perfect.sex.girl.lwallpaper.util.DataTool
import com.good.perfect.sex.girl.lwallpaper.util.Logger
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    private lateinit var main_recyclerview: RecyclerView
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DataTool.getInstance().initData(0, 13, true)
        init()
    }

    private fun init() {
        main_recyclerview = findViewById(R.id.main_recyclerview)

        main_recyclerview.layoutManager = GridLayoutManager(this, 2)

        adapter = MainAdapter(this)
        main_recyclerview.adapter = adapter

    }

    override fun onDestroy() {
        super.onDestroy()
        DataTool.destroy()
    }

}
