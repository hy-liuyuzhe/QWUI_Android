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
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.opensource.svgaplayer.SVGADrawable
import com.opensource.svgaplayer.SVGADynamicEntity
import com.opensource.svgaplayer.SVGAParser
import com.opensource.svgaplayer.SVGAVideoEntity
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

/**
 * videoItem.videoSize / 2 等于 Android dp值
 */
class SvgaViewFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_svga


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutParams = svgaView.layoutParams
        layoutParams.width = ScreenUtils.getAppScreenWidth()
        layoutParams.height = SizeUtils.dp2px(200f)
        svgaView.layoutParams = layoutParams

        SVGAParser(context).decodeFromAssets("room_gift_first_banner.svga", object : SVGAParser.ParseCompletion {

            override fun onComplete(videoItem: SVGAVideoEntity) {
                Log.d("liuyuzhe", "height= ${videoItem.videoSize.height}");
                Log.d("liuyuzhe", "width= ${videoItem.videoSize.width}");
                svgaView.visibility = View.VISIBLE
                svgaView.setImageDrawable(SVGADrawable(videoItem, SVGADynamicEntity().apply {}))
                svgaView.startAnimation()
            }

            override fun onError() {

            }
        })


        val url = "http://img.hakiapp.com/res/default_head_nan.png"
        Log.d("liuyuzhe", "start : ");
        val requestManager = Glide.with(this)
        bitmapPoolSize.setOnClickListener {
            val bitmapPool = Glide.get(requireContext()).bitmapPool;
            val pool = bitmapPool as LruBitmapPool
            Log.d("liuyuzhe", "current size: " + pool.currentSize);
            Log.d("liuyuzhe", "current maxSize: " + pool.maxSize);
        }
        getHeight.setOnClickListener {
//                requestManager.asBitmap()
//                .load(url).transform(BlurTransformation(25, 3)).into(avatarView)

            Log.d("liuyuzhe", "svgaView.height: "+svgaView.height);

        }
        imageBitmapCache.setOnClickListener {
            val drawable = avatarCacheView.drawable as? BitmapDrawable
            Log.d("liuyuzhe", "imageBitmapCache: ");
            Glide.with(requireActivity()).load(url)
                .dontAnimate()
                .placeholder(drawable)
                .error(drawable)
                .into(avatarCacheView)
        }
        clear.setOnClickListener {
            GlideCacheUtil.getInstance().clearImageAllCache(requireContext())
        }

        avatarCacheView.setOnClickListener {
            SimpleActivity.start(requireContext(), PageContainer.bitmap)
        }
    }

}