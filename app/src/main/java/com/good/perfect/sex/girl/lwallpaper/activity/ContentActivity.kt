package com.good.perfect.sex.girl.lwallpaper.activity

import android.os.*
import android.view.KeyEvent
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.good.perfect.sex.girl.lwallpaper.BaseActivity
import com.good.perfect.sex.girl.lwallpaper.DownloadCallBack
import com.good.perfect.sex.girl.lwallpaper.R
import com.good.perfect.sex.girl.lwallpaper.WallpaperApplication
import com.good.perfect.sex.girl.lwallpaper.util.*
import java.io.File

class ContentActivity : BaseActivity(), View.OnClickListener {

    private lateinit var content_image: ImageView
    private lateinit var content_set: Button
    private lateinit var content_save: Button
    private val SET_REQUEST = 1
    private val SAVE_REQUEST = 2
    private lateinit var url: String
    private lateinit var imageName: String

    private lateinit var content_set_setback: Button
    private lateinit var content_set_setscreen: Button
    private lateinit var content_set_setboth: Button
    private lateinit var content_set_ll: LinearLayout

    private lateinit var content_progerss_re: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        url = intent.getStringExtra("url")
        imageName = url.substring(url.lastIndexOf("/"))
        //Logger.e("show $url")

        content_image = findViewById(R.id.content_image)
        content_set = findViewById(R.id.content_set)
        content_save = findViewById(R.id.content_save)
        content_set_setback = findViewById(R.id.content_set_setback)
        content_set_setscreen = findViewById(R.id.content_set_setscreen)
        content_set_setboth = findViewById(R.id.content_set_setboth)
        content_set_ll = findViewById(R.id.content_set_ll)
        content_progerss_re = findViewById(R.id.content_progerss_re)

        content_set.setOnClickListener(this)
        content_set_setback.setOnClickListener(this)
        content_set_setscreen.setOnClickListener(this)
        content_set_setboth.setOnClickListener(this)
        content_save.setOnClickListener(this)
        content_set_ll.setOnClickListener(this)

        Glide
            .with(this)
            .load(url)
            .centerCrop()
            .into(content_image)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            content_set_setscreen.visibility = View.GONE
            content_set_setboth.visibility = View.GONE
        }
    }

    private inner class DownloadThread(val action: Int) : Runnable {

        override fun run() {
            NetTool.downloadWallPaper(url, object : DownloadCallBack {
                override fun downloadSuccess(path: String) {
                    when (action) {
                        1 -> {
                            setBackWallpaper(getWallpaperFromLocal(path))
                            setWallpaperFinish(path)
                        }
                        2 -> {
                            setLockWallpaper7(getWallpaperFromLocal(path))
                            setWallpaperFinish(path)
                        }
                        3 -> {
                            setAllWallpaper(getWallpaperFromLocal(path))
                            setWallpaperFinish(path)
                        }
                        else -> {
                            handler.sendEmptyMessage(0)
                            Looper.prepare()
                            Toast.makeText(
                                WallpaperApplication.getWallpaperContext(),
                                "save finish",
                                Toast.LENGTH_SHORT
                            ).show()
                            Looper.loop()
                        }
                    }
                }

                override fun downloadError() {
                    handler.sendEmptyMessage(0)
                    Looper.prepare()
                    Toast.makeText(
                        WallpaperApplication.getWallpaperContext(),
                        "net error",
                        Toast.LENGTH_SHORT
                    ).show()
                    Looper.loop()
                }
            })
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.content_set -> {
                requestPermission(
                    this@ContentActivity,
                    arrayOf(Permission.WRITE, Permission.READ, Permission.OTHER),
                    SET_REQUEST
                )
            }
            R.id.content_save -> {
                content_progerss_re.visibility = View.VISIBLE
                requestPermission(
                    this@ContentActivity,
                    arrayOf(Permission.WRITE, Permission.READ, Permission.OTHER),
                    SAVE_REQUEST
                )
            }
            R.id.content_set_setback -> {
                content_set_ll.visibility = View.GONE
                content_progerss_re.visibility = View.VISIBLE
//                AgesTool.showPopup(false)
                Thread(DownloadThread(1)).start()
            }
            R.id.content_set_setscreen -> {
                content_set_ll.visibility = View.GONE
                content_progerss_re.visibility = View.VISIBLE
//                AgesTool.showPopup(false)
                Thread(DownloadThread(2)).start()
            }
            R.id.content_set_setboth -> {
                content_set_ll.visibility = View.GONE
                content_progerss_re.visibility = View.VISIBLE
//                AgesTool.showPopup(false)
                Thread(DownloadThread(3)).start()
            }
            R.id.content_set_ll -> {
                content_set_ll.visibility = View.GONE
            }
        }
    }

    override fun requestSuccess(requestCode: Int, permission: List<String>) {
        super.requestSuccess(requestCode, permission)
        if (permission.contains(Permission.WRITE)
            && permission.contains(Permission.READ)
        ) {
            if (requestCode == SET_REQUEST) {
                content_set_ll.visibility = View.VISIBLE
            } else if (requestCode == SAVE_REQUEST) {
//                AgesTool.showPopup(false)
                Thread(DownloadThread(4)).start()
            }
        }
    }

    override fun requestError(requestCode: Int, permission: List<String>) {
        super.requestError(requestCode, permission)
        if (permission.contains(Permission.WRITE) || permission.contains(Permission.READ)) {
            Toast.makeText(
                WallpaperApplication.getWallpaperContext(),
                "please allow the permission",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (content_set_ll.visibility == View.VISIBLE) {
                content_set_ll.visibility = View.GONE
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    private val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            //Logger.d("in handler")
            when (msg.what) {
                0 -> {
                    //Logger.d("in handler 0")
                    content_progerss_re.visibility = View.GONE
                }
            }
        }
    }

    private fun setWallpaperFinish(path: String) {
        deleteFile(File(path))
        try {
            Thread.sleep(5000)
            handler.sendEmptyMessage(0)
            Looper.prepare()
            Toast.makeText(
                WallpaperApplication.getWallpaperContext(),
                "Finished",
                Toast.LENGTH_SHORT
            ).show()
            Looper.loop()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(null)
    }
}
