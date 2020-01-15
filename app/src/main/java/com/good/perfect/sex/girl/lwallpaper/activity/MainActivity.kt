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
        DataTool.getInstance().initData(0, 50, true)
        init()
    }

    private fun init() {
        main_recyclerview = findViewById(R.id.main_recyclerview)

        main_recyclerview.layoutManager = GridLayoutManager(this, 2)

        adapter = MainAdapter(this)
        main_recyclerview.adapter = adapter
        moreProducts()
    }

    var isSlidingToLast = false
    private fun moreProducts() {

        main_recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                isSlidingToLast = dy > 0
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val manager = recyclerView.layoutManager as LinearLayoutManager
                // 当不滚动时
                if (newState === RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition
                    val lastVisibleItem = manager.findLastCompletelyVisibleItemPosition()
                    val totalItemCount = manager.itemCount

                    // 判断是否滚动到底部，并且是向右滚动
                    if (lastVisibleItem == totalItemCount - 1 && isSlidingToLast) {
                        //加载更多功能的代码
                        Logger.d("滑到最下面，增加壁纸")
                        DataTool.getInstance()
                            .initData(DataTool.getInstance().getDataSize(), 50, false)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        DataTool.destroy()
    }

}
