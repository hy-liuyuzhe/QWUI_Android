package com.qwuiteam.project.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.qwuiteam.project.R
import com.qwuiteam.project.utils.ScrollLinearLayoutManager
import com.qwuiteam.project.view.CropVideoCursorLayout
import kotlinx.android.synthetic.main.fragment_pk.*

/**
 * id
 */
class PkFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_pk

    private var canScroll: Boolean = false
    private var cropVideoCursorLayout: CropVideoCursorLayout? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    progressPk.update(10f, progress.toFloat())
                    layoutProgress.update(10f,progress.toFloat())
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
        load.setOnClickListener {
            layoutProgress.update(1111110f*1000, 11111f)
            progressPk.progressBitmapWidth = SizeUtils.dp2px(15f)
            progressPk.playAnimation = true
            progressPk.update(1111110f, 1111110f*1000)
        }

        start.setOnClickListener {
            progressPk.progressBitmapWidth = SizeUtils.dp2px(15f)
            progressPk.playAnimation = true
            progressPk.update(0f, 0f)
            layoutProgress.update(0f, 0f)
        }
//        val params = layoutCrop.layoutParams as FrameLayout.LayoutParams
//        params.width = SizeUtils.dp2px(1000f)
//        layoutCrop.layoutParams = params
        layoutCrop.setCropVideoListener(null)


//        val p = recyclerView.layoutParams as FrameLayout.LayoutParams
//        p.width = SizeUtils.dp2px(500f)
//        recyclerView.layoutParams = p
//        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        val linearLayoutManager = ScrollLinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.d("liuyuzhe", "onScrolled: " + dx);
                if (canScroll){
                    cropVideoCursorLayout?.justLeft(dx)
                }
            }
        })
        recyclerView.adapter = object : BaseQuickAdapter<String, BaseViewHolder>(
            R.layout.item_crop_pk,
            arrayListOf("1")
        ) {
            override fun convert(holder: BaseViewHolder, item: String) {
                val layout = holder.getView<CropVideoCursorLayout>(R.id.layoutCrop)
                cropVideoCursorLayout = layout
                val layoutParams = layout.layoutParams
                layoutParams.width = SizeUtils.dp2px(2000f)
                layout.layoutParams = layoutParams
                layout.setCropVideoListener(object : CropVideoCursorLayout.CropVideoListener {
                    override fun onUp(dragDirection: Int) {
                        Log.d("liuyuzhe", "松手了啦啦啦啦啦啦啦啦啦啦: ");
                        recyclerView.stopScroll()

                    }

                    override fun onMove(dragDirection: Int, moveValue: Int) {

                    }

                    override fun onScrollScreenRightEdge() {

                    }

                    override fun onScrollScreenLeftEdge(canScroll: Boolean) {
                        this@PkFragment.canScroll = canScroll
                        if (canScroll) {
                            recyclerView.smoothScrollToPosition(itemCount)
                        } else {
                            recyclerView.stopScroll()
                        }
                    }
                })
            }
        }
    }
}