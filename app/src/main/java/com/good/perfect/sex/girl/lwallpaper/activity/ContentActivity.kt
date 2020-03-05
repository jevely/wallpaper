package com.good.perfect.sex.girl.lwallpaper.activity

import android.graphics.BitmapFactory
import android.os.*
import android.view.KeyEvent
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.good.perfect.sex.girl.lwallpaper.BaseActivity
import com.good.perfect.sex.girl.lwallpaper.R
import com.good.perfect.sex.girl.lwallpaper.util.*

class ContentActivity : BaseActivity(), View.OnClickListener {

    private lateinit var content_image: ImageView
    private lateinit var content_set: Button
    private val SET_REQUEST = 1
    private val SAVE_REQUEST = 2
    private var url: Int = -1
    private lateinit var imageName: String

    private lateinit var content_set_setback: Button
    private lateinit var content_set_setscreen: Button
    private lateinit var content_set_setboth: Button
    private lateinit var content_set_ll: LinearLayout

    private lateinit var content_progerss_re: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        url = intent.getIntExtra("url",-1)
        imageName = "wallpaper"

        content_image = findViewById(R.id.content_image)
        content_set = findViewById(R.id.content_set)
        content_set_setback = findViewById(R.id.content_set_setback)
        content_set_setscreen = findViewById(R.id.content_set_setscreen)
        content_set_setboth = findViewById(R.id.content_set_setboth)
        content_set_ll = findViewById(R.id.content_set_ll)
        content_progerss_re = findViewById(R.id.content_progerss_re)

        content_set.setOnClickListener(this)
        content_set_setback.setOnClickListener(this)
        content_set_setscreen.setOnClickListener(this)
        content_set_setboth.setOnClickListener(this)
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

    override fun onClick(v: View) {
        when (v.id) {
            R.id.content_set -> {
                content_set_ll.visibility = View.VISIBLE
            }
            R.id.content_set_setback -> {
                content_set_ll.visibility = View.GONE
//                content_progerss_re.visibility = View.VISIBLE
//                AgesTool.showPopup(false)
                setBackWallpaper(BitmapFactory.decodeResource(resources,url))
            }
            R.id.content_set_setscreen -> {
                content_set_ll.visibility = View.GONE
//                content_progerss_re.visibility = View.VISIBLE
//                AgesTool.showPopup(false)
                setLockWallpaper7(BitmapFactory.decodeResource(resources,url))
            }
            R.id.content_set_setboth -> {
                content_set_ll.visibility = View.GONE
//                content_progerss_re.visibility = View.VISIBLE
//                AgesTool.showPopup(false)
                setAllWallpaper(BitmapFactory.decodeResource(resources,url))
            }
            R.id.content_set_ll -> {
                content_set_ll.visibility = View.GONE
            }
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

}
