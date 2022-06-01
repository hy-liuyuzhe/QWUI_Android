package com.qwuiteam.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val pages = PageContainer.values()
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.adapter =
            object :
                BaseQuickAdapter<PageContainer, BaseViewHolder>(
                    R.layout.item_main,
                    pages.toMutableList()
                ) {
                override fun convert(holder: BaseViewHolder, item: PageContainer) {
                    holder.setText(R.id.itemText, item.title)
                    holder.getView<View>(R.id.layoutItemText).setOnClickListener {
                        SimpleActivity.start(this@MainActivity, item)
                    }
                }
            }
    }

}