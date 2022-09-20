package com.qwuiteam.project.fragment

//import com.theartofdev.edmodo.cropper.CropImage
import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import com.blankj.utilcode.util.*
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.config.PictureSelectionConfig
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnQueryDataResultListener
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.luck.picture.lib.model.LocalMediaPageLoader
import com.luck.picture.lib.widget.longimage.ImageSource
import com.luck.picture.lib.widget.longimage.ImageViewState
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView
import com.qwuiteam.project.R
import com.qwuiteam.project.utils.GlideEngine
import com.yalantis.ucrop.UCrop
import com.yalantis.ucrop.view.OverlayView
import kotlinx.android.synthetic.main.fragment_choose_photo.*
import java.util.*


/**
 * 动态设置layoutParams的类型要和add的父类一致，否则你设置的属性无效（宽高除外）
 */
class ChoosePhotoFragment : BaseFragment() {

    private lateinit var sourceBitmap: Bitmap

    override fun getLayoutId(): Int = R.layout.fragment_choose_photo

//    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
//        if (result.isSuccessful) {
//            // use the returned uri
//            val uriContent = result.uriContent
//            val uriFilePath = result.getUriFilePath(context) // optional usage
//        } else {
//            // an error occurred
//            val exception = result.error
//        }
//    }

//    private fun startCrop() {
//        // start picker to get image for cropping and then use the image in cropping activity
//        cropImage.launch(
//            options {
//                setGuidelines(Guidelines.ON)
//            }
//        )
//
//        //start picker to get image for cropping from only gallery and then use the image in
//        //cropping activity
//        cropImage.launch(
//            options {
//                setImagePickerContractOptions(
//                    PickImageContractOptions(includeGallery = true, includeCamera = false)
//                )
//            }
//        )
//
//        // start cropping activity for pre-acquired image saved on the device and customize settings
//        cropImage.launch(
//            options(uri = imageUri) {
//                setGuidelines(Guidelines.ON)
//                setOutputCompressFormat(CompressFormat.PNG)
//            }
//        )
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        save.setOnClickListener {
//            CropImage.activity()
//                .setGuidelines(CropImageView.Guidelines.ON)
//                .start(this);
            //BitmapUtils.rotateImage()

            val drawRect = Rect()
//            layoutOverLay.getDrawingRect(drawRect)
            layoutOverLay.getGlobalVisibleRect(drawRect)

            val b = Bitmap.createBitmap(sourceBitmap,
                drawRect.left,
                drawRect.top,
                drawRect.width(), drawRect.height(), image.matrix, false)
            image2.setImageBitmap(b)
            LogUtils.d("createBitmap.x: " + layoutOverLay.x.toInt() + ",  " +
                    "createBitmap.y: " + layoutOverLay.y.toInt())
        }
        camera.setOnClickListener {
            PictureSelector.create(this)
                .openCamera(PictureMimeType.ofImage())
                .forResult(object : OnResultCallbackListener<LocalMedia> {
                    override fun onResult(result: MutableList<LocalMedia>) {
                        val media = result.first()
                        Log.d("liuyuzhe", "openCamera : " + GsonUtils.toJson(media));
//                        Log.d("liuyuzhe", "size 128: " + SizeUtils.dp2px(128f));
//                        Log.d("liuyuzhe", "r.cut: " + media.realPath);
//                        Log.d("liuyuzhe", "r.w: " + media.width);
//                        Log.d("liuyuzhe", "r.h: " + media.height);
//                        sourceBitmap = BitmapFactory.decodeFile(media.realPath)
//                        image.setImageBitmap(sourceBitmap)

                        //test(media)
                    }

                    override fun onCancel() {

                    }
                })
        }
        loadAudio.setOnClickListener {
            loadAudioData()
        }

        choose.setOnClickListener {
//            val p =
//                "/storage/emulated/0/DCIM/Screenshots/Screenshot_2022-08-18-16-08-35-364_com.haki.mobile.png"
//            sourceBitmap = BitmapFactory.decodeFile(p)
//            image.setImageBitmap(sourceBitmap)

            PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .imageEngine(GlideEngine.createGlideEngine())
                .isCamera(false)
//                .isEnableCrop(true)
//                .freeStyleCropEnabled(true)
//                .cropImageWideHigh(ScreenUtils.getScreenWidth(), SizeUtils.dp2px(128f))
                .forResult(object : OnResultCallbackListener<LocalMedia> {
                    override fun onResult(result: MutableList<LocalMedia>) {
                        val media = result.first()
                        Log.d("liuyuzhe", "size screen: " + GsonUtils.toJson(media));
//                        Log.d("liuyuzhe", "size 128: " + SizeUtils.dp2px(128f));
//                        Log.d("liuyuzhe", "r.cut: " + media.realPath);
//                        Log.d("liuyuzhe", "r.w: " + media.width);
//                        Log.d("liuyuzhe", "r.h: " + media.height);
//                        sourceBitmap = BitmapFactory.decodeFile(media.realPath)
//                        image.setImageBitmap(sourceBitmap)

                        //test(media)
                    }

                    override fun onCancel() {

                    }
                })
//            CropImage.activity().start(requireContext(), this@ChoosePhotoFragment);

        }


        load.setOnClickListener {
//            view_overlay.setTargetAspectRatio(1.77f)

//            val f = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
//                SimpleDateFormat("yyyyMMdd-HHmmss").format(Date()) + ".jpg")
//            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            uri = FileProvider.getUriForFile(requireContext(),
//                requireActivity().packageName + ".fileprovider", f)
//            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
//            this.startActivityForResult(takePictureIntent, 111)
        }
    }

    private fun test(media: LocalMedia) {
        //                        ucropView.cropImageView.setImageBitmap(BitmapFactory.decodeFile(media.realPath))
        view_overlay.isDragFrame = true
        view_overlay.setShowCropGrid(true)
        view_overlay.setTargetAspectRatio(1.77f)

        val intent = requireActivity().intent
        // Overlay view options
        view_overlay.setFreestyleCropEnabled(intent.getBooleanExtra(UCrop.Options.EXTRA_FREE_STYLE_CROP,
            OverlayView.DEFAULT_FREESTYLE_CROP_MODE != OverlayView.FREESTYLE_CROP_MODE_DISABLE))
        view_overlay.setDragFrame(true)
        view_overlay.setDimmedColor(intent.getIntExtra(UCrop.Options.EXTRA_DIMMED_LAYER_COLOR,
            Color.parseColor("#8c000000")))
        view_overlay.setCircleDimmedLayer(intent.getBooleanExtra(UCrop.Options.EXTRA_CIRCLE_DIMMED_LAYER,
            OverlayView.DEFAULT_CIRCLE_DIMMED_LAYER))

        view_overlay.setShowCropFrame(intent.getBooleanExtra(UCrop.Options.EXTRA_SHOW_CROP_FRAME,
            OverlayView.DEFAULT_SHOW_CROP_FRAME))
        view_overlay.setCropFrameColor(intent.getIntExtra(UCrop.Options.EXTRA_CROP_FRAME_COLOR,
            Color.parseColor("#ffffff")))
        view_overlay.setCropFrameStrokeWidth(intent.getIntExtra(UCrop.Options.EXTRA_CROP_FRAME_STROKE_WIDTH,
            SizeUtils.dp2px(1f)))

        view_overlay.setShowCropGrid(intent.getBooleanExtra(UCrop.Options.EXTRA_SHOW_CROP_GRID,
            OverlayView.DEFAULT_SHOW_CROP_GRID))
        view_overlay.setCropGridRowCount(intent.getIntExtra(UCrop.Options.EXTRA_CROP_GRID_ROW_COUNT,
            OverlayView.DEFAULT_CROP_GRID_ROW_COUNT))
        view_overlay.setCropGridColumnCount(intent.getIntExtra(UCrop.Options.EXTRA_CROP_GRID_COLUMN_COUNT,
            OverlayView.DEFAULT_CROP_GRID_COLUMN_COUNT))
        view_overlay.setCropGridColor(intent.getIntExtra(UCrop.Options.EXTRA_CROP_GRID_COLOR,
            resources.getColor(R.color.purple)))
        view_overlay.setCropGridStrokeWidth(intent.getIntExtra(UCrop.Options.EXTRA_CROP_GRID_STROKE_WIDTH,
            SizeUtils.dp2px(2f)))


        image.setImageBitmap(BitmapFactory.decodeFile(media.realPath))
        //                        displayLongPic(Uri.parse(media.path), image)
    }

    private fun loadAudioData() {
        val instance = PictureSelectionConfig.getInstance()
        instance.chooseMode = PictureMimeType.ofAudio()
        instance.pageSize = 60
        LocalMediaPageLoader.getInstance(context).loadPageMediaData(-1, 1,
            label@ OnQueryDataResultListener { data: List<LocalMedia?>, currentPage: Int, isHasMore: Boolean ->
                LogUtils.d("loadAudioData.size: " + data.size)
                LogUtils.d("loadAudioData: " + GsonUtils.toJson(data))
            })
    }

    lateinit var uri: Uri

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 111) {
//
//        }
//        val file = UriUtils.uri2File(uri)
//        Log.d("liuyuzhe", "requestCode: $requestCode");
//        Log.d("liuyuzhe", "requestCode.path: ${uri.path}");
//        Log.d("liuyuzhe", "requestCode.file: ${file}");
//        Log.d("liuyuzhe", "requestCode.file.absolutePath: ${file.absolutePath}");
//        Log.d("liuyuzhe", "requestCode.uri: $uri");
////        Log.d("liuyuzhe", "requestCode.uri.toFile().exists(): ${uri.toFile().exists()}");
////        image.setImageURI(uri)
//        imagePath.setImageBitmap(BitmapFactory.decodeFile(file.absolutePath))

//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            val result = CropImage.getActivityResult(data)
//            if (resultCode == RESULT_OK) {
//                val resultUri = result.uri
//                image.setImageURI(resultUri)
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                val error = result.error
//            }
//            Log.d("liuyuzhe", "result: "+result);
//        }
    }

    private fun displayLongPic(uri: Uri, longImg: SubsamplingScaleImageView) {
        longImg.isQuickScaleEnabled = true
        longImg.isZoomEnabled = true
        longImg.setDoubleTapZoomDuration(100)
        longImg.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP)
        longImg.setDoubleTapZoomDpi(SubsamplingScaleImageView.ZOOM_FOCUS_CENTER)
        val i = 0f
        longImg.setImage(ImageSource.uri(uri), ImageViewState(i, PointF(i, i), 0))
    }
}