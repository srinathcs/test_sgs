package com.example.myapplication1.imageuploader

import android.content.Context
import android.net.Uri
import com.example.myapplication1.model.MultipartModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ImageUploader(private val context: Context) {
    private val baseUrl = "https://truebroker.in/web/api/mobile/"

    private val service: ImageUploadService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(ImageUploadService::class.java)
    }

    fun uploadImage(imageUri: Uri, type: String, cid: String, callback: Callback<MultipartModel>) {
        val imageFile = context.contentResolver.openInputStream(imageUri)?.use { inputStream ->
            val requestBody = inputStream.readBytes().toRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("photo", "image", requestBody)
        }

        val typePart = type.toRequestBody("text/plain".toMediaTypeOrNull())
        val cidPart = cid.toRequestBody("text/plain".toMediaTypeOrNull())

        service.uploadImage(typePart, cidPart, imageFile!!)
            .enqueue(callback)
    }
}