package com.qwuiteam.project

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.blankj.utilcode.util.ActivityUtils
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
                        if (holder.layoutPosition == 2){
                            val intent = Intent(context, SimpleActivity::class.java)
                                intent.putExtra("page", item)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            context.startActivity(intent,ActivityOptionsCompat.makeCustomAnimation(context, 0, 0).toBundle())
//                            ActivityUtils.startActivity(this@MainActivity, intent,0,0)
                            return@setOnClickListener
                        }
                        SimpleActivity.start(this@MainActivity, item)
                    }
                }
            }

        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission_group.STORAGE,
                Manifest.permission_group.MICROPHONE,
                Manifest.permission_group.CAMERA,
            ),
            124
        )
    }

}