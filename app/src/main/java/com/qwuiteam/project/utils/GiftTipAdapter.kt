package com.qwuiteam.project.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.qwuiteam.project.R

/**
 * Created by Daichi Furiya / Wasabeef on 2020/08/26.
 */
class GiftTipAdapter(private val dataSet: MutableList<String>) :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.layout_list_item, dataSet) {
    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.text, item)
    }
}
