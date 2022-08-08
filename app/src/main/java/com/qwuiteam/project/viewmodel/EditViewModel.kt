package com.qwuiteam.project.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class EditViewModel : ViewModel() {

    val subtitlesLiveData = MutableLiveData<String>()

    private val stateFlowV1 = MutableStateFlow(0)
    val resultFlow = stateFlowV1.map { netWork() }

    suspend fun netWork(): Flow<String> {
        return flow<String> {
            delay(1000)
            emit("123")
        }
    }


    var number = 0
    val simpleFlow = flow {
        number++
        emit("send value: $number")
    }.flowOn(Dispatchers.IO)

    val flowConvertSharedFlow by lazy {
        flow {
            emit("1、flow")
            emit("2、convert")
            emit("3、SharedFlow")
        }.shareIn(
            viewModelScope, //协程作用域范围
            SharingStarted.Eagerly, //立即开始
            replay = 3 //重播给新订阅者的数量
        ).onStart { LogUtils.d("onStart") }
    }


    val m = MutableSharedFlow<Int>()
    fun update() {
        val simpleFlow = flow {
            number++
            emit("send value: $number")
        }.catch {

        }.flowOn(Dispatchers.IO)
            .onCompletion {

            }

    }
}