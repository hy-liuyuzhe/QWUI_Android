package com.qwuiteam.project.fragment

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.core.content.FileProvider
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.UriUtils
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.config.PictureSelectionConfig
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnQueryDataResultListener
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.luck.picture.lib.model.LocalMediaPageLoader
import com.qwuiteam.project.R
import com.qwuiteam.project.utils.GlideEngine
import kotlinx.android.synthetic.main.fragment_choose_photo.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


/**
 * 动态设置layoutParams的类型要和add的父类一致，否则你设置的属性无效（宽高除外）
 */
class ChoosePhotoFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_choose_photo

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadAudio.setOnClickListener {
            loadAudioData()
        }

        choose.setOnClickListener {
            PictureSelector.create(this)
                .openGallery(PictureMimeType.ofAudio())
                .imageEngine(GlideEngine.createGlideEngine())
                .forResult(object : OnResultCallbackListener<LocalMedia?> {
                    override fun onResult(result: MutableList<LocalMedia?>?) {
                        Log.d("liuyuzhe", "post: "+result.toString());
                    }

                    override fun onCancel() {

                    }
                })
        }


        load.setOnClickListener {
            val f = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
                SimpleDateFormat("yyyyMMdd-HHmmss").format(Date()) + ".jpg")
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            uri = FileProvider.getUriForFile(requireContext(),
                requireActivity().packageName + ".fileprovider", f)
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            this.startActivityForResult(takePictureIntent, 111)
        }
    }

    private fun loadAudioData() {
        val instance = PictureSelectionConfig.getInstance()
        instance.chooseMode =  PictureMimeType.ofAudio()
        instance.pageSize = 60
        LocalMediaPageLoader.getInstance(context).loadPageMediaData(-1, 1,
            label@ OnQueryDataResultListener { data: List<LocalMedia?>, currentPage: Int, isHasMore: Boolean ->
                LogUtils.d("loadAudioData.size: "+data.size)
                LogUtils.d("loadAudioData: "+GsonUtils.toJson(data))
            })
    }

    lateinit var uri: Uri

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 111){

        }
        val file = UriUtils.uri2File(uri)
        Log.d("liuyuzhe", "requestCode: $requestCode");
        Log.d("liuyuzhe", "requestCode.path: ${uri.path}");
        Log.d("liuyuzhe", "requestCode.file: ${file}");
        Log.d("liuyuzhe", "requestCode.file.absolutePath: ${file.absolutePath}");
        Log.d("liuyuzhe", "requestCode.uri: $uri");
//        Log.d("liuyuzhe", "requestCode.uri.toFile().exists(): ${uri.toFile().exists()}");
//        image.setImageURI(uri)
        imagePath.setImageBitmap(BitmapFactory.decodeFile(file.absolutePath))

    }
}