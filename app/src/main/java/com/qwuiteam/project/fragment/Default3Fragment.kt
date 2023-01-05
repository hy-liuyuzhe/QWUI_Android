package com.qwuiteam.project.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.qwuiteam.project.PageContainer
import com.qwuiteam.project.R
import kotlinx.android.synthetic.main.fragment_default3.*

/**
 * id
 */
class Default3Fragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_default3

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val recyclerView = rv
        recyclerView.layoutManager = LinearLayoutManager(context)
        val data = PageContainer.values().toMutableList()
        data.addAll(data)
        data.addAll(data)
        data.addAll(data)

        Log.d("liuyuzhe", "view create: " + data.size);
        recyclerView.adapter = object :
            BaseQuickAdapter<PageContainer, BaseViewHolder>(R.layout.item_main, data) {

            override fun getItemCount(): Int {
                val itemCount = super.getItemCount()
                Log.d("liuyuzhe", "getItemCount: $itemCount");
                return itemCount
            }

            override fun convert(holder: BaseViewHolder, item: PageContainer) {
                holder.setText(R.id.itemText, item.title)
            }
        }
    }


}