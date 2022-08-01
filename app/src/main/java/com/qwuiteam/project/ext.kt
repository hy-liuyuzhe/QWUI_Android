package com.qwuiteam.project

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.blankj.utilcode.util.LogUtils
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


fun View.visible() {
    this.visibility = View.VISIBLE;
}

fun View.gone() {
    this.visibility = View.GONE;
}

//callback 和 launchIn的生命周期同步，当viewmodel销毁时，它也会执行close
fun EditText.textChangeFlow(): Flow<CharSequence> = callbackFlow {

    val watcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            LogUtils.d("onTextChanged: $s");
            this@callbackFlow.trySend(s).isSuccess
            LogUtils.d("trySend: $s");
        }

        override fun afterTextChanged(s: Editable?) {
        }

    }
    addTextChangedListener(watcher)
    awaitClose {
        LogUtils.d("awaitClose: ");
        removeTextChangedListener(watcher)
    }
}