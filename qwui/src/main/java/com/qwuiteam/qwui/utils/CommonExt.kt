package com.qwuiteam.qwui.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File


fun String.openFile(context: Context) {
    val filePath = this
    val file = File(filePath)
    val uri: Uri = FileProvider.getUriForFile(
        context, "${context.packageName}.fileprovider", file
    )
    val mime = context.contentResolver.getType(uri)
    val intent = Intent()
    intent.action = Intent.ACTION_VIEW
    intent.setDataAndType(uri, mime)
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    context.startActivity(intent)
}