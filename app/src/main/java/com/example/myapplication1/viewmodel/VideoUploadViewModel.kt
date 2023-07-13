package com.example.myapplication1.viewmodel

import android.content.Context
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import androidx.lifecycle.ViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class VideoUploadViewModel : ViewModel() {

    fun uploadVideoToGallery(context: Context, videoUri: Uri) {
        val targetFilename = "my_video.mp4"
        val targetDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
        val targetFile = File(targetDir, targetFilename)

        try {
            context.contentResolver.openInputStream(videoUri)?.use { inputStream ->
                FileOutputStream(targetFile).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }

            MediaScannerConnection.scanFile(
                context,
                arrayOf(targetFile.toString()),
                arrayOf("video/*"),
                null
            )

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}