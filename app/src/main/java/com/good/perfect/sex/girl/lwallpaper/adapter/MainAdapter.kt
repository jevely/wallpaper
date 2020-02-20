package com.good.perfect.sex.girl.lwallpaper.adapter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.good.perfect.sex.girl.lwallpaper.R
import com.good.perfect.sex.girl.lwallpaper.activity.ContentActivity
import com.good.perfect.sex.girl.lwallpaper.util.DataTool
import com.good.perfect.sex.girl.lwallpaper.util.Logger
import com.good.perfect.sex.girl.lwallpaper.util.getScreen

class MainAdapter(val context: Context) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.main_recyclerview_layout, parent, false)
        return MainViewHolder(view)
    }

    override fun getItemCount(): Int {
        return DataTool.getInstance().getDataSize()
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.setData(DataTool.getInstance().getData(position))
    }

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val main_adapter_iv = itemView.findViewById<ImageView>(R.id.main_adapter_iv)

        fun setData(imageUrl: String) {

            main_adapter_iv.setOnClickListener {
                val intent = Intent(context, ContentActivity::class.java)
                intent.putExtra("url", imageUrl)
                context.startActivity(intent)
            }

            Glide
                .with(context)
                .load(imageUrl)
                .placeholder(R.mipmap.defult_img)
                .override((getScreen().x / 2.0F).toInt(), (getScreen().y / 2.0F).toInt())
                .centerCrop()
                .addListener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        //Logger.e("$imageUrl : ${e?.toString()!!}")
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                })
                .into(main_adapter_iv)

        }

    }

}