package com.good.perfect.free.hao.wallpaper.activity

import android.R.attr
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
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
    private lateinit var main_add: ImageView

    private val WRITE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE"
    private val READ_PERMISSION = "android.permission.READ_EXTERNAL_STORAGE"
    private val REQUEST_CODE = 0
    private val REQUEST_ALUBM = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DataTool.getInstance().initData(0, 15, true)
        init()
    }

    private fun init() {
        viewpager = findViewById(R.id.viewpager)
        main_add = findViewById(R.id.main_add)

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

        main_add.setOnClickListener {
            checkPermission()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        DataTool.destroy()
    }

    private fun checkPermission() {
        requestPermission(this, arrayOf(WRITE_PERMISSION, READ_PERMISSION), REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ALUBM) {
            val uri = data?.data

            val intent = Intent(this@MainActivity, ShowActivity::class.java)
            intent.putExtra("uri", uri.toString())
            startActivity(intent)
        }
    }

    override fun requestSuccess(requestCode: Int, permission: List<String>) {
        super.requestSuccess(requestCode, permission)
        if (permission.contains(WRITE_PERMISSION) && permission.contains(READ_PERMISSION)) {
            val intentToPickPic = Intent(Intent.ACTION_GET_CONTENT, null)
            intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            if (intentToPickPic.resolveActivity(packageManager) != null) {
                startActivityForResult(intentToPickPic, REQUEST_ALUBM)
            }
        }
    }

    override fun requestError(requestCode: Int, permission: List<String>) {
        super.requestError(requestCode, permission)

    }

}
