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
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_flow.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.util.concurrent.TimeUnit


/**
 * id
 */
class FlowFragment : BaseFragment() {

    lateinit var editViewModel: EditViewModel

    override fun getLayoutId(): Int = R.layout.fragment_flow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RxJavaPlugins.setErrorHandler {
            Log.d("liuyuzhe", "拦截错误: "+it);
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buffer.setOnClickListener { buffer() }
        process.setOnClickListener { process() }
        process2.setOnClickListener { process2() }
        product.setOnClickListener { product() }

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

    lateinit var publishSubject: PublishSubject<Int>

    private fun product(){
        GlobalScope.launch(Dispatchers.IO) {
            for (i in 0..1300) {
                Log.d(TAG, "process.上游给数据 : " + (i));
                publishSubject.onNext(i)
                delay(10)
            }
        }
    }

    private fun process2() {
        publishSubject = PublishSubject.create<Int>()
        publishSubject.toFlowable(BackpressureStrategy.BUFFER)
            .buffer(200, TimeUnit.MILLISECONDS, 20)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                GlobalScope.launch {
                    Log.e(TAG, "process.onNext : " + (it));
                    delay(100)
                }
//                Log.e(TAG, "外部 process.onNext : " + (it));
            }


    }

    private fun process() {
        publishSubject = PublishSubject.create<Int>()
        publishSubject.toFlowable(BackpressureStrategy.ERROR)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subscriber<Int> {
                override fun onSubscribe(s: Subscription) {
                    s.request(Long.MAX_VALUE)
                }

                override fun onNext(t: Int?) {
                    try {
                        Thread.sleep(1000);
                    } catch (e: InterruptedException) {
                        e.printStackTrace();
                    }
                    Log.e(TAG, "process.onNext : " + (t));
                }

                override fun onError(t: Throwable?) {
                    Log.e(TAG, "onError : " + (t));
                }

                override fun onComplete() {
                    Log.e(TAG, "onComplete");
                }
            })

        for (i in 0..130) {
            publishSubject.onNext(i)
            try {
                Thread.sleep(10);
            } catch (e: Exception) {
            }
            Log.e(TAG, "process.上游给数据 : " + (i));
        }

/*
//            .onNext()
        Flowable.create(FlowableOnSubscribe<Int> {
            for (i in 0..130) {
                it.onNext(i)
                try {
                    Thread.sleep(10);
                } catch (e: Exception) {
                }
                Log.e(TAG, "process.上游给数据 : " + (i));
            }
            it.onComplete()
        }, BackpressureStrategy.ERROR)
            .subscribe(object : Subscriber<Int> {
                override fun onSubscribe(s: Subscription) {
                    s.request(Long.MAX_VALUE)
                }

                override fun onNext(t: Int?) {
                    try {
                        Thread.sleep(1000);
                    } catch (e: InterruptedException) {
                        e.printStackTrace();
                    }
                    Log.e(TAG, "process.onNext : " + (t));
                }

                override fun onError(t: Throwable?) {
                    Log.e(TAG, "onError : " + (t));
                }

                override fun onComplete() {
                    Log.e(TAG, "onComplete");
                }

            })*/
    }

    private fun buffer() {
        Flowable.create(FlowableOnSubscribe<Int> {
            for (i in 0..130) {
                it.onNext(i)
                try {
                    Thread.sleep(10);
                } catch (e: Exception) {
                }
                Log.e(TAG, "上游给数据 : " + (i));
            }
            it.onComplete()
        }, BackpressureStrategy.ERROR)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subscriber<Int> {
                override fun onSubscribe(s: Subscription) {
                    s.request(Long.MAX_VALUE)
                }

                override fun onNext(t: Int?) {
                    try {
                        Thread.sleep(1000);
                    } catch (e: InterruptedException) {
                        e.printStackTrace();
                    }
                    Log.e(TAG, "onNext : " + (t));
                }

                override fun onError(t: Throwable?) {
                    Log.e(TAG, "onError : " + (t));
                }

                override fun onComplete() {
                    Log.e(TAG, "onComplete");
                }

            })
//        Flowable.create(new FlowableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
//                for(int j = 0;j<=150;j++){
//                e.onNext(j);
//                Log.i(TAG," 发送数据："+j);
//                try{
//                    Thread.sleep(50);
//                }catch (Exception ex){
//                }
//            }
//            }
//        },BackpressureStrategy.ERROR)
//            .subscribeOn(Schedulers.newThread())
//            .observeOn(Schedulers.newThread())
//            .subscribe(new Subscriber<Integer>() {
//                @Override
//                public void onSubscribe(Subscription s) {
//                    s.request(Long.MAX_VALUE); //观察者设置接收事件的数量,如果不设置接收不到事件
//                }
//                @Override
//                public void onNext(Integer integer) {
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    Log.e(TAG,"onNext : "+(integer));
//                }
//                @Override
//                public void onError(Throwable t) {
//                    Log.e(TAG,"onError : "+t.toString());
//                }
//                @Override
//                public void onComplete() {
//                    Log.e(TAG,"onComplete");
//                }
//            });


//        PublishProcessor.create<Int>()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe { integer -> Log.d("liuyuzhe", "integer: $integer"); }

//        val subscribe = Flowable.create<Int>({ emitter ->
//            for (i in 0..999) {
//                emitter.onNext(i)
//            }
//        }, BackpressureStrategy.BUFFER)
//            .buffer(2000,TimeUnit.MILLISECONDS,20)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe { integer -> Log.d("liuyuzhe", "integer: $integer"); }
    }

    fun searchFlow(key: String): Flow<String> {
        LogUtils.d("searchFlow: ")
        return flow { emit(search(key)) }
    }

    suspend fun search(key: String): String {
        LogUtils.d("search: " + key)
        delay(1000)
        return "search result: $key"
    }
}
