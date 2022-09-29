package com.qwuiteam.project.fragment

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import com.blankj.utilcode.constant.MemoryConstants
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SizeUtils
import com.opensource.svgaplayer.*
import com.qwuiteam.project.R
import kotlinx.android.synthetic.main.fragment_svga.*
import java.lang.reflect.Field
import java.net.URL
import kotlin.math.roundToLong

/**
 * videoItem.videoSize / 2 等于 Android dp值
 */
class SvgaViewFragment : BaseFragment() {

    companion object {
        const val TAG = "SvgaViewFragment"
    }

    override fun getLayoutId(): Int = R.layout.fragment_svga

    lateinit var svgaDrawable1: SVGADrawable
    lateinit var svgaDrawable2: SVGADrawable

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val imagesFiled: Field =
//            videoEntity.javaClass.getDeclaredField("imageMap")
//        imagesFiled.isAccessible = true
//        val images: HashMap<String, Bitmap> =
//            imagesFiled.get(videoEntity) as HashMap<String, Bitmap>
//        var total = 0f for (entry in images) {
//            total += entry.value.height * entry.value.width * 4
//        }

        load1.setOnClickListener {
            SVGAParser(context).decodeFromAssets(
                "room_gift_first_banner.svga",
                object : SVGAParser.ParseCompletion {
                    override fun onComplete(videoEntity: SVGAVideoEntity) {
//                        calculateMemorySize(videoItem)
                        calculateSvgaMemory(videoEntity)

//                        LogUtils.d("memory size : ${
//                            ConvertUtils.memorySize2Byte(
//                                total2., MemoryConstants.MB
//                            )
//                        }")
                        svgaDrawable1 = SVGADrawable(videoEntity, SVGADynamicEntity())
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
            svgaViewFull.callback = object : SVGACallback {
                override fun onFinished() {
                    Log.e(TAG, "onFinished: ");
                }

                override fun onPause() {
                    Log.e(TAG, "onPause: ");
                }

                override fun onRepeat() {
                    Log.e(TAG, "onRepeat: ");
                }

                override fun onStep(frame: Int, percentage: Double) {
                    Log.i(TAG, "onStep: " + frame);
                    Log.e(TAG, "percentage: " + percentage);
                }

            }
//            val url = "https://img.hakiapp.com/FnYguyw0fvvXfWNoBD1QxtoYamhY?imageslim"
//                URL(url)
//            SVGAParser(context).decodeFromAssets("longV2.svga",
            SVGAParser(context).decodeFromURL(URL("https://img.hakiapp.com/FpNSQCjsZRZ6Efb0mgX5mhK6tbMR?imageslim"),
                object : SVGAParser.ParseCompletion {
                    override fun onComplete(videoItem: SVGAVideoEntity) {
                        val svgaDrawable2 = SVGADrawable(
                            videoItem, SVGADynamicEntity().apply {})
                        svgaViewFull.setImageDrawable(svgaDrawable2)
                        svgaViewFull.startAnimation()
                    }

                    override fun onError() {
                        Log.e(TAG, "onError: ");
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

    private fun calculateSvgaMemory(videoEntity: SVGAVideoEntity) {
        val imagesFiled: Field = videoEntity.javaClass.getDeclaredField("imageMap")
        imagesFiled.isAccessible = true
        val images = imagesFiled.get(videoEntity) as HashMap<String, Bitmap>
        var total = 0f
        for (entry in images) {
            total += entry.value.height * entry.value.width * 4
        }
        LogUtils.d("memory size : ${total / 1024 / 1024}");
    }

}