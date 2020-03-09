package com.good.perfect.free.hao.wallpaper.adapter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.alite.qeuaed.manager.NliManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.facebook.ads.AdOptionsView
import com.facebook.ads.MediaView
import com.facebook.ads.NativeAdLayout
import com.good.perfect.free.hao.wallpaper.R
import com.good.perfect.free.hao.wallpaper.WallpaperApplication
import com.good.perfect.free.hao.wallpaper.activity.ShowActivity
import com.good.perfect.free.hao.wallpaper.content.WallPaperContent
import com.good.perfect.free.hao.wallpaper.util.DataTool
import com.good.perfect.free.hao.wallpaper.util.DeviceUtils
import com.good.perfect.free.hao.wallpaper.util.Logger
import com.good.perfect.free.hao.wallpaper.util.getScreen

class MainAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var requestTime = 0L

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == 1) {
            val view =
                LayoutInflater.from(context)
                    .inflate(R.layout.main_recyclerview_layout, parent, false)
            return MainViewHolder(view)
        } else {
            val view = LayoutInflater.from(context)
                .inflate(R.layout.facebook_native_layout, parent, false)
            return AdViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position % 5 == 0) {
            val content = DataTool.getInstance().getData(position)
            if (content.fbNativeAd == null) {
                if (System.currentTimeMillis() - requestTime > 10000) {
                    requestTime = System.currentTimeMillis()
                    val newFbNativeAd = NliManager.getFbAppAd()
                    if (newFbNativeAd != null) {
                        content.fbNativeAd = newFbNativeAd
                        return 2
                    }
                }
            } else {
                return 2
            }
        }
        return 1
    }

    override fun getItemCount(): Int {
        return DataTool.getInstance().getDataSize()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MainViewHolder) {
            holder.setData(DataTool.getInstance().getData(position).imageId)
        } else if (holder is AdViewHolder) {
            Logger.d("我是列表中的广告 onBindViewHolder")
            holder.initAd(DataTool.getInstance().getData(position))
        }

    }

    inner class AdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun initAd(content: WallPaperContent) {
            try {
                val nativeAd = content.fbNativeAd ?: return

                nativeAd.unregisterView()

                val parentView = itemView

                val adView =
                    parentView.findViewById<NativeAdLayout>(R.id.iuw_moni_native_ad_container)

                // Add the AdOptionsView
                val adChoicesContainer =
                    adView.findViewById<LinearLayout>(R.id.iuw_moni_ad_choices_container)

                val adOptionsView =
                    AdOptionsView(WallpaperApplication.getWallpaperContext(), nativeAd, adView)
                adChoicesContainer.removeAllViews()
                adChoicesContainer.addView(adOptionsView, 0)

                // Create native UI using the ad metadata.
                val nativeAdIcon = adView.findViewById<MediaView>(R.id.iuw_moni_native_ad_icon)
                val nativeAdTitle = adView.findViewById<TextView>(R.id.iuw_moni_native_ad_title)
                val nativeAdMedia = adView.findViewById<MediaView>(R.id.iuw_moni_native_ad_media)
                val nativeAdCallToAction =
                    adView.findViewById<Button>(R.id.iuw_moni_native_ad_call_to_action)
                val adsocial = adView.findViewById<TextView>(R.id.iuw_moni_adsocial)
                val nl_fb_content1 = adView.findViewById<TextView>(R.id.iuw_moni_nl_fb_content1)
                val nl_fb_content2 = adView.findViewById<TextView>(R.id.iuw_moni_nl_fb_content2)
                val nl_bf_parent = adView.findViewById<RelativeLayout>(R.id.iuw_moni_nl_bf_parent)
                val nl_fb_translation =
                    adView.findViewById<TextView>(R.id.iuw_moni_native_ad_translation)

                val title = nativeAd.advertiserName
                if (!TextUtils.isEmpty(title))
                    nativeAdTitle.text = title

                val translation = nativeAd.sponsoredTranslation
                if (!TextUtils.isEmpty(translation)) {
                    nl_fb_translation.text = translation
                }

                val social = nativeAd.adSocialContext
                if (!TextUtils.isEmpty(social)) {
                    adsocial.text = social
                }

                val head = nativeAd.adHeadline
                if (head != null && !TextUtils.isEmpty(head) && head.length > 20) {
                    nl_fb_content1.text = head
                } else {
                    nl_fb_content1.visibility = View.GONE

                    val layoutparams: RelativeLayout.LayoutParams =
                        nl_fb_content2.layoutParams as RelativeLayout.LayoutParams
                    layoutparams.topMargin =
                        DeviceUtils.dp2px(WallpaperApplication.getWallpaperContext(), 16f)
                    nl_fb_content2.layoutParams = layoutparams
                }

                val body = nativeAd.adBodyText
                if (!TextUtils.isEmpty(body)) {
                    nl_fb_content2.text = body
                }

                if (nativeAd.hasCallToAction()) {
                    nativeAdCallToAction.visibility = View.VISIBLE
                    val action = nativeAd.adCallToAction
                    if (!TextUtils.isEmpty(action))
                        nativeAdCallToAction.text = action
                } else {
                    nativeAdCallToAction.visibility = View.INVISIBLE
                }

                // Create a list of clickable views
                val clickableViews = mutableListOf<View>()

                clickableViews.add(nativeAdIcon)

                clickableViews.add(nativeAdTitle)

                clickableViews.add(nl_fb_content1)
                clickableViews.add(nl_fb_content2)

                clickableViews.add(nl_bf_parent)


                clickableViews.add(nativeAdCallToAction)

                // Register the Title and CTA button to listen for clicks.
                //必须在主线程调用
                nativeAd.registerViewForInteraction(
                    adView,
                    nativeAdMedia,
                    nativeAdIcon,
                    clickableViews
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val main_adapter_iv = itemView.findViewById<ImageView>(R.id.main_adapter_iv)

        fun setData(imageUrl: Int) {

            main_adapter_iv.setOnClickListener {
                val intent = Intent(context, ShowActivity::class.java)
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