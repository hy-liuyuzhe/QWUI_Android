package com.qwuiteam.project.fragment

import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.databinding.adapters.ImageViewBindingAdapter.setImageDrawable
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.opensource.svgaplayer.*
import com.qwuiteam.project.PageContainer
import com.qwuiteam.project.R
import com.qwuiteam.project.SimpleActivity
import com.qwuiteam.project.utils.GlideCacheUtil
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.fragment_bitmap.*
import kotlinx.android.synthetic.main.fragment_bitmap.avatarCacheView
import kotlinx.android.synthetic.main.fragment_bitmap.avatarView
import kotlinx.android.synthetic.main.fragment_bitmap.bitmapPoolSize
import kotlinx.android.synthetic.main.fragment_bitmap.clear
import kotlinx.android.synthetic.main.fragment_bitmap.imageBitmapCache
import kotlinx.android.synthetic.main.fragment_bitmap.load
import kotlinx.android.synthetic.main.fragment_svga.*
import java.net.URL

/**
 * videoItem.videoSize / 2 等于 Android dp值
 */
class SvgaViewFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_svga

    lateinit var svgaDrawable1: SVGADrawable
    lateinit var svgaDrawable2: SVGADrawable

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        load1.setOnClickListener {
            SVGAParser(context).decodeFromAssets(
                "room_gift_first_banner.svga",
                object : SVGAParser.ParseCompletion {

                    override fun onComplete(videoItem: SVGAVideoEntity) {
                        svgaDrawable1 = SVGADrawable(videoItem, SVGADynamicEntity())
                        svgaView.setImageDrawable(svgaDrawable1)
                        svgaView.startAnimation()
                    }

                    override fun onError() {

                    }
                })
        }
        load2.setOnClickListener {
            SVGAParser(context).decodeFromAssets(
                "room_gift_full_banner.svga",
                object : SVGAParser.ParseCompletion {

                    override fun onComplete(videoItem: SVGAVideoEntity) {
                        svgaDrawable2 = SVGADrawable(
                            videoItem, SVGADynamicEntity().apply {})
                        svgaView.setImageDrawable(
                            svgaDrawable2
                        )
                        svgaView.startAnimation()
                    }

                    override fun onError() {

                    }
                })
        }
        loadFull.setOnClickListener {
            svgaViewFull.callback = object : SVGACallback{
                override fun onFinished() {
                    Log.e(TAG,"onFinished: ");
                }

                override fun onPause() {
                    Log.e(TAG,"onPause: ");
                }

                override fun onRepeat() {
                    Log.e(TAG,"onRepeat: ");
                }

                override fun onStep(frame: Int, percentage: Double) {
                    Log.e(TAG,"onStep: "+frame);
                    Log.e(TAG,"percentage: "+percentage);
                }

            }
//            val url = "https://img.hakiapp.com/FnYguyw0fvvXfWNoBD1QxtoYamhY?imageslim"
//                URL(url)
            SVGAParser(context).decodeFromAssets("long.svga",
                object : SVGAParser.ParseCompletion {
                    override fun onComplete(videoItem: SVGAVideoEntity) {
                        val svgaDrawable2 = SVGADrawable(
                            videoItem, SVGADynamicEntity().apply {})
                        svgaViewFull.setImageDrawable(svgaDrawable2)
                        svgaViewFull.startAnimation()
                    }

                    override fun onError() {

                    }
                })
        }

        cache1.setOnClickListener {
            svgaView.setImageDrawable(svgaDrawable1)
            svgaView.startAnimation()
        }


        cache2.setOnClickListener {
            svgaView.setImageDrawable(svgaDrawable2)
            svgaView.startAnimation()
        }
    }

}