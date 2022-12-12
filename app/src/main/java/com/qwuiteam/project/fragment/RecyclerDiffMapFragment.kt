package com.qwuiteam.project.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qwuiteam.project.R
import com.qwuiteam.project.databinding.ItemDiffBinding
import jp.wasabeef.example.recyclerview.SampleData
import kotlinx.android.synthetic.main.fragment_rv_diff.*
import kotlin.random.Random

/**
 * id
 */
class RecyclerDiffMapFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_rv_diff

    val mapData = HashMap<Int, String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = SampleData.LIST1.toMutableList()
        for (i in -1..11) {
            Log.d("liuyuzhe", "i : " + i);
            mapData[i] = data[i + 1]
        }


        val diffAdapter = DiffAdapter(mapData)

        recyclerView.apply {
            itemAnimator = null
            layoutManager = GridLayoutManager(context,4)
            adapter = diffAdapter
        }

        refresh.setOnClickListener {
            val diffDataCallback = MultiAudioDiffDataCallback()
            diffDataCallback.old = diffAdapter.mapData as HashMap<Int, String>
            val mutableMap = mapData.toMutableMap()
            val n = Random.nextInt(10)
            Log.d("liuyuzhe", "随机数: $n");

            val tmp = mutableMap[n]
            mutableMap[n] = mutableMap[0]!!
            mutableMap[0] = tmp!!

            diffDataCallback.new = mutableMap as HashMap<Int, String>
            val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(diffDataCallback,false)
            diffResult.dispatchUpdatesTo(diffAdapter)
            diffAdapter.mapData = mutableMap
        }
    }


    inner class DiffAdapter(var mapData: HashMap<Int, String>) :
        RecyclerView.Adapter<DiffViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiffViewHolder {
            return DiffViewHolder(ItemDiffBinding.inflate(LayoutInflater.from(parent.context),
                parent, false))
        }

        override fun onBindViewHolder(holderDiff: DiffViewHolder, position: Int) {
            Log.d("liuyuzhe", "onBindViewHolder.position: $position");
            val data = mapData[position]
            holderDiff.binding.textTitle.text = data
            holderDiff.binding.textPosition.text = (position+1).toString()
        }

        override fun getItemCount(): Int = mapData.size - 1

    }


    inner class DiffViewHolder(val binding: ItemDiffBinding) : RecyclerView.ViewHolder(binding.root)


    inner class MultiAudioDiffDataCallback : DiffUtil.Callback() {

        var old = HashMap<Int, String>()
        var new = HashMap<Int, String>()

        override fun getOldListSize(): Int = old.size - 1

        override fun getNewListSize(): Int = new.size - 1

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            try {
                val (oldId, newId) = itemUserId(oldItemPosition, newItemPosition)
                Log.d("liuyuzhe",
                    "areItemsTheSame.newItemPosition: $newItemPosition , result: ${oldId == newId}");
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
                Log.d("liuyuzhe",
                    "areContentsTheSame.newItemPosition: $newItemPosition , result: ${oldId == newId}");
                return oldId == newId
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return true
        }

    }

}