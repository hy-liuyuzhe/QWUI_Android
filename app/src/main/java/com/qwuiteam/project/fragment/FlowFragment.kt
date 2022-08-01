package com.qwuiteam.project.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.*
import com.qwuiteam.project.R
import com.qwuiteam.project.textChangeFlow
import com.qwuiteam.project.viewmodel.EditViewModel
import kotlinx.android.synthetic.main.fragment_flow.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * id
 */
class FlowFragment : BaseFragment() {

    lateinit var editViewModel: EditViewModel

    override fun getLayoutId(): Int = R.layout.fragment_flow


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editViewModel = ViewModelProvider(this).get("123", EditViewModel::class.java)
        send1.setOnClickListener {
            GlobalScope.launch {
                editViewModel.flowConvertSharedFlow.collect {
                    Log.d("liuyuzhe", "111flowConvertSharedFlow: " + it);
                }
            }
            GlobalScope.launch {
                editViewModel.simpleFlow.collect {
                    send1.text = it
                }
            }
        }
        send2.setOnClickListener {
            GlobalScope.launch {
                editViewModel.flowConvertSharedFlow.collect {
                    Log.d("liuyuzhe", "222flowConvertSharedFlow: " + it);
                }
            }

            GlobalScope.launch {
                editViewModel.simpleFlow.collect {
                    send2.text = it
                }
            }
        }
        flush.setOnClickListener {
            GlobalScope.launch {
                editViewModel.resultFlow.collect {
                    LogUtils.d("flow: " + it);
                }
            }
        }

        create.setOnClickListener {
//            (1..5).asFlow()
//                .map {
//                    LogUtils.d("it: $it");
//                    it * it
//                }
//                .collect()
            GlobalScope.launch {
                val reduce = (1..5).asFlow()
                    .reduce { a, b -> a + b }
                LogUtils.d("reduce r: $reduce");
                //.collect { LogUtils.d("take: $it"); }
            }
        }

        editContent.textChangeFlow()
            .filter { it.isNotEmpty() }
            .debounce(300)
            .flatMapLatest { searchFlow(it.toString()) }
            .flowOn(Dispatchers.IO)
            .onEach {
                LogUtils.d("result: $it");
            }.launchIn(editViewModel.viewModelScope)
    }

    fun searchFlow(key: String): Flow<String> {
        LogUtils.d("searchFlow: ")
        return flow { emit(search(key)) }
    }

    suspend fun search(key: String): String {
        LogUtils.d("search: "+key)
        delay(1000)
        return "search result: $key"
    }
}
