package com.good.perfect.free.hao.wallpaper.activity

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.*
import android.view.KeyEvent
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.good.perfect.free.hao.wallpaper.BaseActivity
import com.good.perfect.free.hao.wallpaper.R
import com.good.perfect.free.hao.wallpaper.util.*

class ShowActivity : BaseActivity(), View.OnClickListener {

    private lateinit var content_image: ImageView
    private lateinit var content_set: Button
    private val SET_REQUEST = 1
    private val SAVE_REQUEST = 2
    private var url: Int = -1
    private var imageUri: Uri? = null
    private lateinit var imageName: String

    private lateinit var content_set_setback: Button
    private lateinit var content_set_setscreen: Button
    private lateinit var content_set_setboth: Button
    private lateinit var content_set_ll: LinearLayout

    private lateinit var content_progerss_re: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        url = intent.getIntExtra("url", -1)
        imageUri = Uri.parse(intent.getStringExtra("uri"))

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

        if (url != -1) {
            Glide
                .with(this)
                .load(url)
                .centerCrop()
                .into(content_image)
        } else {
            Glide
                .with(this)
                .load(imageUri)
                .centerCrop()
                .into(content_image)
        }


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
                if(url != -1) {
                    setBackWallpaper(BitmapFactory.decodeResource(resources, url))
                }else{
                    setBackWallpaper(BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri!!)))
                }
            }
            R.id.content_set_setscreen -> {
                content_set_ll.visibility = View.GONE
//                content_progerss_re.visibility = View.VISIBLE
//                AgesTool.showPopup(false)
                if(url != -1) {
                    setLockWallpaper7(BitmapFactory.decodeResource(resources, url))
                }else{
                    setLockWallpaper7(BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri!!)))
                }
            }
            R.id.content_set_setboth -> {
                content_set_ll.visibility = View.GONE
//                content_progerss_re.visibility = View.VISIBLE
//                AgesTool.showPopup(false)
                if(url != -1) {
                    setAllWallpaper(BitmapFactory.decodeResource(resources, url))
                }else{
                    setAllWallpaper(BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri!!)))
                }
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
