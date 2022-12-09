package com.qwuiteam.project.fragment

import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qwuiteam.project.R
import com.qwuiteam.project.databinding.ItemDiffBinding
import jp.wasabeef.example.recyclerview.SampleData
import kotlinx.android.synthetic.main.fragment_rv_diff.*
import kotlin.random.Random

/**
 * id
 */
class RecyclerDiffFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_rv_diff

    val data = SampleData.LIST.toMutableList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val diffAdapter = DiffAdapter(data)

        recyclerView.apply {
            itemAnimator = null
            layoutManager = LinearLayoutManager(context)
            adapter = diffAdapter
        }

        refresh.setOnClickListener {
            val diffDataCallback = MultiAudioDiffDataCallback()
            diffDataCallback.old = diffAdapter.dataList as ArrayList<String>
            val toMutableList = data.toMutableList()
            val n = Random.nextInt(10)
            Log.d("liuyuzhe", "随机数: $n");

            toMutableList[n] = "随机 $n"
            diffDataCallback.new = toMutableList as ArrayList<String>
            val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(diffDataCallback)
            diffResult.dispatchUpdatesTo(diffAdapter)
            diffAdapter.dataList = toMutableList
        }
    }


    inner class DiffAdapter(var dataList: MutableList<String>) :
        RecyclerView.Adapter<DiffViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiffViewHolder {
            return DiffViewHolder(ItemDiffBinding.inflate(LayoutInflater.from(parent.context),
                parent, false))
        }

        override fun onBindViewHolder(holderDiff: DiffViewHolder, position: Int) {
            Log.d("liuyuzhe", "onBindViewHolder.position: $position");
            val data = dataList[position]
            holderDiff.binding.textTitle.text = data
            holderDiff.binding.textPosition.text = position.toString()
        }

        override fun getItemCount(): Int = dataList.size

    }


    inner class DiffViewHolder(val binding: ItemDiffBinding) : RecyclerView.ViewHolder(binding.root)


   inner class MultiAudioDiffDataCallback : DiffUtil.Callback() {

        var old = ArrayList<String>()
        var new = ArrayList<String>()

        override fun getOldListSize(): Int = old.size

        override fun getNewListSize(): Int = new.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            try {
                val (oldId, newId) = itemUserId(oldItemPosition, newItemPosition)
                return oldId == newId
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return true
        }

        private fun itemUserId(
            oldItemPosition: Int,
            newItemPosition: Int,
        ): Pair<String?, String?> {
            val oldId = old.get(oldItemPosition)
            val newId = new.get(newItemPosition)
            return Pair(oldId, newId)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            try {
                val (oldId, newId) = itemUserId(oldItemPosition, newItemPosition)
                return oldId == newId
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return true
        }

    }

}