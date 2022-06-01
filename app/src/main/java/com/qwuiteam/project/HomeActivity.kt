package com.qwuiteam.project

import android.animation.ObjectAnimator
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.CleanUtils
import com.blankj.utilcode.util.Utils
import kotlinx.android.synthetic.main.activity_home.*
import java.util.regex.Pattern

class HomeActivity : AppCompatActivity() {

    var bitmap = BitmapFactory.decodeResource(Utils.getApp().resources, R.mipmap.ic_launcher)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val scheme = "odaily://open/web?url=http://test01.odaily.com/activity/397"

        val SPECIAL_URL_RULE = ("(http|https)://(test01.odaily.com|odaily.com)/"
                + "(goods|reference|report|topics|newsflashes|votes|themes|activity|videos|audios)/(\\d+)(?:\\"
                + ".html|)")

        imageBitmapSchemeMatch.setOnClickListener {
            val p = Pattern.compile(SPECIAL_URL_RULE)
            val m = p.matcher(scheme)
            if (m.find()) {
                val id = m.group(4)
                val group3 = m.group(3)
                Log.d("liuyuzhe", "id: "+id);
                Log.d("liuyuzhe", "group3: "+group3);
            }

            val uri = Uri.parse(scheme)
            Log.d("liuyuzhe", "host: "+uri.host);
//            Log.d("liuyuzhe", "scheme: "+uri.scheme);
//            Log.d("liuyuzhe", "path: "+uri.path);
//            Log.d("liuyuzhe", "url: "+uri.getQueryParameter("url"));
        }

        imageBitmap.setOnClickListener {
            if (bitmap.isRecycled){
                Log.d("liuyuzhe", "recycler: ");
                bitmap = bitmap.copy(Bitmap.Config.ARGB_8888,true)
            }
            progress_circular.visibility = View.VISIBLE
            imageBitmap.setImageBitmap(bitmap)
        }
        imageBitmapR.setOnClickListener {
            bitmap.recycle()
        }
    }
}