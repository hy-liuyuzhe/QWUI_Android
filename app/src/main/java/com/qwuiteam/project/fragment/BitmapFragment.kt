package com.qwuiteam.project.fragment

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.qwuiteam.project.PageContainer
import com.qwuiteam.project.R
import com.qwuiteam.project.SimpleActivity
import com.qwuiteam.project.utils.GlideCacheUtil
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.fragment_bitmap.*

class BitmapFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_bitmap


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = "http://img.hakiapp.com/res/default_head_nan.png"
        Log.d("liuyuzhe", "start : ");
        val requestManager = Glide.with(this)
        bitmapPoolSize.setOnClickListener {
            val bitmapPool = Glide.get(requireContext()).bitmapPool;
            val pool = bitmapPool as LruBitmapPool
            Log.d("liuyuzhe", "current size: " + pool.currentSize);
            Log.d("liuyuzhe", "current maxSize: " + pool.maxSize);
        }
        load.setOnClickListener {
                requestManager.asBitmap()
                .load(url).transform(BlurTransformation(25, 3)).into(avatarView)
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