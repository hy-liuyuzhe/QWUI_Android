package com.qwuiteam.project.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.Utils
import com.qwuiteam.project.R
import com.qwuiteam.project.utils.GiftTipAdapter
import com.qwuiteam.project.utils.MainAdapter
import com.qwuiteam.project.utils.HkSlideInLeftAnimator
import jp.wasabeef.example.recyclerview.SampleData
import jp.wasabeef.recyclerview.animators.*
import kotlinx.android.synthetic.main.fragment_sender.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * id
 */
class SenderViewFragment : BaseFragment() {

    internal enum class Type(val animator: BaseItemAnimator) {
        SlideInLeft(HkSlideInLeftAnimator()),
        FadeIn(FadeInAnimator()),
        FadeInDown(FadeInDownAnimator()),
        FadeInUp(FadeInUpAnimator()),
        FadeInLeft(FadeInLeftAnimator()),
        FadeInRight(FadeInRightAnimator()),
        Landing(LandingAnimator()),
        ScaleIn(ScaleInAnimator()),
        ScaleInTop(ScaleInTopAnimator()),
        ScaleInBottom(ScaleInBottomAnimator()),
        ScaleInLeft(ScaleInLeftAnimator()),
        ScaleInRight(ScaleInRightAnimator()),
        FlipInTopX(FlipInTopXAnimator()),
        FlipInBottomX(FlipInBottomXAnimator()),
        FlipInLeftY(FlipInLeftYAnimator()),
        FlipInRightY(FlipInRightYAnimator()),
        SlideInRight(SlideInRightAnimator()),
        SlideInDown(SlideInDownAnimator()),
        SlideInUp(SlideInUpAnimator()),
        OvershootInRight(OvershootInRightAnimator(1.0f)),
        OvershootInLeft(OvershootInLeftAnimator(1.0f))
    }


    override fun getLayoutId(): Int = R.layout.fragment_sender


    private val mainAdapter = GiftTipAdapter(SampleData.LIST.toMutableList())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val cache = LruCache<String,String>(2)
        //cache.putCount()
        //androidx.collection.LruCache
        //com.bumptech.glide.util.LruCache

        val recyclerView = list
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            itemAnimator = HkSlideInLeftAnimator()
//            itemAnimator?.addDuration = 500
            adapter = mainAdapter
        }
        val spinner = spinner
        val spinnerAdapter =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1)
        for (type in Type.values()) {
            spinnerAdapter.add(type.name)
        }
        spinner.adapter = spinnerAdapter
//        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>,
//                view: View,
//                position: Int,
//                id: Long,
//            ) {
//                recyclerView.itemAnimator = Type.values()[position].animator
//                recyclerView.itemAnimator?.addDuration = 500
//                recyclerView.itemAnimator?.removeDuration = 500
//                list.itemAnimator?.isRunning{
//                    Log.d("liuyuzhe", "isRunning finish: ");
//                }
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>) {
//                // no-op
//            }
//        }

        insert.setOnClickListener {
            if (mainAdapter.data.size == 0){
                mainAdapter.data.add("first item")
                mainAdapter.notifyItemInserted(mainAdapter.data.size + mainAdapter.headerLayoutCount)
                return@setOnClickListener
            }
            mainAdapter.addData("newly added item")
        }
        view.findViewById<View>(R.id.add).setOnClickListener { mainAdapter.addData(1,"newly added item 123") }
        view.findViewById<View>(R.id.del).setOnClickListener {
            if (mainAdapter.data.size == 1){
                val index = 0
                mainAdapter.data.removeAt(index)
                val internalPosition = index + mainAdapter.headerLayoutCount
                mainAdapter.notifyItemRemoved(internalPosition)
                mainAdapter.notifyItemRangeChanged(internalPosition, mainAdapter.data.size - internalPosition)
                return@setOnClickListener
            }
            mainAdapter.removeAt(0)
        }
    }
}