package com.qwuiteam.project.fragment

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.qwuiteam.project.PageContainer
import com.qwuiteam.project.R
import kotlinx.android.synthetic.main.fragment_default3.*


/**
 * id
 */
class Default3Fragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_default3
    lateinit var adapter: BaseQuickAdapter<PageContainer, BaseViewHolder>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val recyclerView = rv
        recyclerView.layoutManager = LinearLayoutManager(context)
        val data = PageContainer.values().toMutableList()
        data.addAll(data)

        Log.d("liuyuzhe", "view create: " + data.size);
        adapter = object :
            BaseQuickAdapter<PageContainer, BaseViewHolder>(R.layout.item_main, data),
            LoadMoreModule {

            override fun getItemCount(): Int {
                val itemCount = super.getItemCount()
                Log.d("liuyuzhe", "getItemCount: $itemCount");
                return itemCount
            }

            override fun convert(holder: BaseViewHolder, item: PageContainer) {
                holder.setText(R.id.itemText, item.title)
            }
        }
        recyclerView.adapter = adapter
        adapter.loadMoreModule.setOnLoadMoreListener {
            loadMore()
        }
    }

    private fun loadMore() {
        requestData();
    }

    var page = 0

    private fun requestData() {
        page++
        Request(page, object : RequestCallBack {
            override fun success(data: List<PageContainer>) {
                adapter.addData(data)
                adapter.loadMoreModule.loadMoreComplete()
            }

            override fun fail(e: Exception) {
            }

        }).start()
    }


    /**
     * 模拟加载数据的类，不用特别关注
     */
    internal class Request(private val mPage: Int, private val mCallBack: RequestCallBack) :
        Thread() {

        private val mHandler: Handler = Handler(Looper.getMainLooper())

        override fun run() {
            try {
                sleep(800)
            } catch (e: InterruptedException) {
            }
//            if (mPage == 2 && mFirstError) {
//                mFirstError = false
//                mHandler.post(Runnable { mCallBack.fail(RuntimeException("fail")) })
//            } else {
//                var size = PAGE_SIZE
//                if (mPage == 1) {
//                    if (mFirstPageNoMore) {
//                        size = 1
//                    }
//                    mFirstPageNoMore = !mFirstPageNoMore
//                    if (!mFirstError) {
//                        mFirstError = true
//                    }
//                } else if (mPage == 4) {
//                    size = 1
//                }
//                val dataSize = size
//                mHandler.post(Runnable { mCallBack.success(DataServer.getSampleData(dataSize)) })
//            }
            mHandler.post {

                val values = PageContainer.values().apply { this.shuffle() }
                mCallBack.success(values.toMutableList())

            }
        }

        companion object {
            private var mFirstPageNoMore = false
            private var mFirstError = true
        }

    }

    internal interface RequestCallBack {

        fun success(data: List<PageContainer>)

        fun fail(e: Exception)
    }

}