package com.qwuiteam.project.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import com.blankj.utilcode.util.*
import com.qwuiteam.project.R
import kotlinx.android.synthetic.main.fragment_i18n.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*


/**
 * id
 */
class I18nFragment : BaseFragment() {
    companion object {
        val TAG = I18nFragment::class.java.simpleName
    }

    val fileName = "super-gift-lottery.json"

    val air = "ui"

    override fun getLayoutId(): Int = R.layout.fragment_i18n

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        xls.setOnClickListener {
//            Log.i(TAG, "onViewCreated: $path")
        }

        more_to_one_json.setOnClickListener {
            toOneJson()
//            toNewJson()
        }

        count.setOnClickListener {
//            countJson()
            countJson2()
        }
    }

    private fun countJson2() {
        val list = resources.assets.list("count")
        list!!.map { readJson2(it) }
        Log.e(TAG, "countJson.total: $total")
    }

    private fun readJson2(path: String) {
        val input = resources.assets.open("count/${path}")
        val size = input.available()
        val buffer = ByteArray(size)
        input.read(buffer)
        input.close()
        val s = String(buffer, Charsets.UTF_8)
        val jsonArray = JSONArray(s) //response 为str
        for (i in 0 until jsonArray.length()) {
            val jsonObject: JSONObject = jsonArray.getJSONObject(i)
            countJsonObject(jsonObject, path)
        }

        Log.e(TAG, "当前json: $path , 中文数量: $total")
    }

    private fun countJsonObject(jsonObject: JSONObject, path: String) {
        val keys: Iterator<String> = jsonObject.keys()
        val list = ArrayList<JsonBean>()
        while (keys.hasNext()) {
            val key = keys.next()
            //            Log.e(TAG, "onResponseteams: $key")
            val value = jsonObject.optString(key)
            value.forEach {
                val b = isChinese(it)
                if (b) {
                    total++
                }
                //Log.e(TAG, "isChinese.value: $value , result: $b")
            }
            val bean = JsonBean(path.substring(0, path.indexOf(".")), key, value)
            list.add(bean)
        }
    }


    private fun i18nFileToBean2(dir: String, path: String): ArrayList<JsonBean> {
        if (!FileUtils.getFileExtension(path).equals("json")) {
            return ArrayList<JsonBean>()
        }
        val input = resources.assets.open("$dir/${path}")
        val size = input.available()
        val buffer = ByteArray(size)
        input.read(buffer)
        input.close()
        val s = String(buffer, Charsets.UTF_8)
        val jsonObject = JSONObject(s) //response 为str

        val keys: Iterator<String> = jsonObject.keys()
        val list = ArrayList<JsonBean>()
        while (keys.hasNext()) {
            val key = keys.next()
//            Log.e(TAG, "onResponseteams: $key")
            val value = jsonObject.optString(key)
            val bean = JsonBean(path.substring(0, path.indexOf(".")), key, value)
            list.add(bean)
        }
        //val jsonBean = GsonUtils.fromJson(InputStreamReader(input), JsonBean::class.java)
//        Log.i(TAG, "i18nFileToBean: $jsonBean")
        return list
    }


    private fun toNewJson() {
        val list = resources.assets.list("translatedir")!!
        list.forEachIndexed { index, bean ->
            val dir = "translatedir/$bean/locale"
            val innerList = resources.assets.list(dir)
            val list = innerList?.map { i18nFileToBean2(dir, it) }?.filter { it.isNotEmpty() }?.toMutableList()
            Log.i(TAG, "toNewJson: $bean")
            toJson(bean, list)
        }
    }

    fun toJson(dirName: String, jsonList: MutableList<ArrayList<JsonBean>>?) {
        if (jsonList.isNullOrEmpty()) return
        val dataList = findMaxSizeList(jsonList)

        val data = LinkedList<OneJsonBean>()
        dataList.forEachIndexed { index, jsonBean ->
            data.add(
                OneJsonBean(
                    jsonBean.key,
                    findNextColumnValue(jsonList, "zh", jsonBean.key),
                    findNextColumnValue(jsonList, "ar", jsonBean.key),
                    findNextColumnValue(jsonList, "en", jsonBean.key),
                    findNextColumnValue(jsonList, "hi", jsonBean.key),
                    findNextColumnValue(jsonList, "tr", jsonBean.key),
                )
            )
        }
        ///storage/emulated/0/Android/data/com.qwuiteam.project/cache/world-cup.xls
        Log.i(TAG, "toOneJson: $data")
        val json = GsonUtils.toJson(data)
        val path = PathUtils.getExternalAppCachePath() + "/$dirName" + fileName
        val r = FileIOUtils.writeFileFromString(path, json)
        Log.i(TAG, "path:$path , 结果: $r")
    }

    fun isChinese(c: Char): Boolean {
        val sc = Character.UnicodeScript.of(c.code)
        return sc == Character.UnicodeScript.HAN
    }

    var curtJson = ""
    var total = 0


    private fun countJson() {
        val list = resources.assets.list("count")
        list!!.map { readJson(it) }
        Log.e(TAG, "countJson.total: $total")
    }

    private fun readJson(path: String) {
        val input = resources.assets.open("count/${path}")
        val size = input.available()
        val buffer = ByteArray(size)
        input.read(buffer)
        input.close()
        val s = String(buffer, Charsets.UTF_8)
        val jsonObject = JSONObject(s) //response 为str

        val keys: Iterator<String> = jsonObject.keys()
        val list = ArrayList<JsonBean>()
        while (keys.hasNext()) {
            val key = keys.next()
//            Log.e(TAG, "onResponseteams: $key")
            val value = jsonObject.optString(key)
            value.forEach {
                val b = isChinese(it)
                if (b) {
                    total++
                }
                //Log.e(TAG, "isChinese.value: $value , result: $b")
            }
            val bean = JsonBean(path.substring(0, path.indexOf(".")), key, value)
            list.add(bean)
        }
        Log.e(TAG, "当前json: $path , 中文数量: $total")
    }

    private fun toOneJson() {
        val list = resources.assets.list(air)
        val jsonList = list!!.map { path -> i18nFileToBean(path) }.toMutableList()
        val dataList = findMaxSizeList(jsonList)

        val data = LinkedList<OneJsonBean>()
        dataList.forEachIndexed { index, jsonBean ->
            data.add(
                OneJsonBean(
                    jsonBean.key,
                    findNextColumnValue(jsonList, "zh", jsonBean.key),
                    findNextColumnValue(jsonList, "ar", jsonBean.key),
                    findNextColumnValue(jsonList, "en", jsonBean.key),
                    findNextColumnValue(jsonList, "hi", jsonBean.key),
                    findNextColumnValue(jsonList, "tr", jsonBean.key),
                )
            )
        }
        ///storage/emulated/0/Android/data/com.qwuiteam.project/cache/world-cup.xls
        Log.i(TAG, "toOneJson: $data")
        val json = GsonUtils.toJson(data)
        val path = PathUtils.getExternalAppCachePath() + "/" + fileName
        val r = FileIOUtils.writeFileFromString(path, json)
        Log.i(TAG, "path:$path , 结果: $r")
    }

    private fun findNextColumnValue(jsonList: MutableList<java.util.ArrayList<JsonBean>>, letter: String, key: String): String {
        jsonList.forEach {
            it.forEach { bean ->
                if (bean.column == letter && bean.key == key) {
                    return bean.value
                }
            }
        }
        Log.i(TAG, "findNextColumnValue.letter: $letter , key: $key")
        return ""
    }

    private fun findMaxSizeList(jsonList: MutableList<ArrayList<JsonBean>>): ArrayList<JsonBean> {
        var max = 0
        var i = 0
        jsonList.forEachIndexed { index, arrayList ->
            if (arrayList.size > max) {
                i = index
                max = arrayList.size
            }
        }
        return jsonList[i]
    }

    private fun i18nFileToBean(path: String): ArrayList<JsonBean> {
        if (!FileUtils.getFileExtension(path).equals("json")) {
            return ArrayList<JsonBean>()
        }
        val input = resources.assets.open("$air/${path}")
        val size = input.available()
        val buffer = ByteArray(size)
        input.read(buffer)
        input.close()
        val s = String(buffer, Charsets.UTF_8)
        val jsonObject = JSONObject(s) //response 为str

        val keys: Iterator<String> = jsonObject.keys()
        val list = ArrayList<JsonBean>()
        while (keys.hasNext()) {
            val key = keys.next()
//            Log.e(TAG, "onResponseteams: $key")
            val value = jsonObject.optString(key)
            val bean = JsonBean(path.substring(0, path.indexOf(".")), key, value)
            list.add(bean)
        }
        //val jsonBean = GsonUtils.fromJson(InputStreamReader(input), JsonBean::class.java)
//        Log.i(TAG, "i18nFileToBean: $jsonBean")
        return list
    }
}

data class JsonBean(
    val column: String,
    val key: String,
    val value: String,
)

data class OneJsonBean(
    val name: String,
    val zh: String,
    val ar: String,
    val en: String,
    val hi: String,
    val tr: String,
)