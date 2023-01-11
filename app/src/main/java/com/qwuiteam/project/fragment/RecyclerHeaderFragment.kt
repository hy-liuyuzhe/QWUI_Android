package com.qwuiteam.project.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ThreadUtils
import com.qwuiteam.project.PageContainer
import com.qwuiteam.project.R
import com.qwuiteam.project.bean.AbsUser
import com.qwuiteam.project.bean.User
import com.qwuiteam.project.gone
import kotlinx.android.synthetic.main.fragment_default3.rv
import kotlinx.android.synthetic.main.fragment_rv_header.*
import kotlin.random.Random


class RecyclerHeaderFragment : BaseFragment() {

    private val data = ArrayList<AbsUser>()
    private val adapter by lazy { HeaderAdapter() }
    lateinit var headerView: View
    
    override fun getLayoutId(): Int = R.layout.fragment_rv_header


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        data.addAll(PageContainer.values().toMutableList().map {
            User().apply { title = it.title }
        }.take(1).toList())
        btnAddList.setOnClickListener {
            if (this::headerView.isInitialized && headerView.isInvisible){
                Log.d("liuyuzhe", "gone header: ");
                headerView.gone()
            }
            adapter.addData(0, arrayListOf(User("new Data: ${Random.nextInt(100)}")))
            scrollBotttom()
        }
        btnAddHeader.setOnClickListener {
            addHeaderView("")
            scrollBotttom()
            ThreadUtils.runOnUiThreadDelayed({
                Log.d("liuyuzhe", "5秒钟后 隐藏header: ");
                headerView.visibility = View.INVISIBLE               
            },5000)
        }

        val recyclerView = rv
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, true)

//        data.addAll(data)

        Log.d("liuyuzhe", "view create: " + data.size);
//        adapter = object :
//            BaseQuickAdapter<PageContainer, BaseViewHolder>(R.layout.item_main, data),
//            LoadMoreModule {
//
//            override fun getItemCount(): Int {
//                val itemCount = super.getItemCount()
//                Log.d("liuyuzhe", "getItemCount: $itemCount");
//                return itemCount
//            }
//
//            override fun convert(holder: BaseViewHolder, item: PageContainer) {
//                holder.setText(R.id.itemText, item.title)
//            }
//        }
        recyclerView.adapter = adapter
    }

    private fun scrollBotttom() {
        (rv.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(0, 0)
    }

    private fun addHeaderView(data: String) {
        headerView = LayoutInflater.from(context)
            .inflate(R.layout.item_main, null, false)
            .also {
                it.rootView.findViewById<TextView>(R.id.itemText).text = "header: $data"
            }
        adapter.addHeaderView(headerView)
    }


    inner class HeaderAdapter : RecyclerView.Adapter<HeaderViewHolder>() {
        private val headerType = 2;

        private lateinit var mHeaderLayout: LinearLayout

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
            if (viewType == headerType) {
                val headerLayoutVp = mHeaderLayout.parent
                if (headerLayoutVp is ViewGroup) {
                    headerLayoutVp.removeView(mHeaderLayout)
                }
                Log.d("liuyuzhe", "onCreateViewHolder.headerView: ");
                return HeaderViewHolder(mHeaderLayout)
            }

            return HeaderViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_main, parent, false))
        }

        override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
            if (holder.itemViewType == headerType) {
                Log.d("liuyuzhe", "bind.position: " + position);
                return
            }
            holder.rootView.findViewById<TextView>(R.id.itemText)
                .text = data[position - getHeaderCount()].title
        }

        override fun getItemCount(): Int = data.size + getHeaderCount()

        private fun getHeaderCount(): Int {
            if (!this::mHeaderLayout.isInitialized || mHeaderLayout.childCount == 0) {
                return 0
            } else {
                return 1
            }
        }

        override fun getItemViewType(position: Int): Int {
            if (position == 0 && getHeaderCount()>0) {
                return headerType
            }
            return super.getItemViewType(position)
        }

        fun addHeaderView(header: View) {
            addHeaderView(header, -1, LinearLayout.VERTICAL)
        }

        fun addHeaderView(header: View, index: Int, orientation: Int): Int {
            if (!this::mHeaderLayout.isInitialized) {
                mHeaderLayout = LinearLayout(header.context)
                if (orientation == LinearLayout.VERTICAL) {
                    mHeaderLayout.setOrientation(LinearLayout.VERTICAL)
                    mHeaderLayout.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT))
                } else {
                    mHeaderLayout.setOrientation(LinearLayout.HORIZONTAL)
                    mHeaderLayout.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.MATCH_PARENT))
                }
            }
            val childCount: Int = mHeaderLayout.getChildCount()
            var mIndex = index
            if (index < 0 || index > childCount) {
                mIndex = childCount
            }
            mHeaderLayout.addView(header, mIndex)
            if (mHeaderLayout.getChildCount() == 1) {
                notifyItemInserted(0)
            }
            return mIndex
        }

        fun addData(position: Int, newData: List<AbsUser>) {
            data.addAll(position, newData)
//            notifyItemRangeInserted(data.size - newData.size + getHeaderCount(),
//                newData.size)
            notifyItemRangeInserted(position+getHeaderCount(), newData.size)
        }
        fun addData(newData: List<AbsUser>) {
            data.addAll(newData)
            notifyItemRangeInserted(data.size - newData.size + getHeaderCount(),
                newData.size)
        }
    }


    inner class HeaderViewHolder(val rootView: View) : RecyclerView.ViewHolder(rootView) {

    }

}