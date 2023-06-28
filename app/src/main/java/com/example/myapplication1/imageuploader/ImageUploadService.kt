package com.example.myapplication1.imageuploader

import com.example.myapplication1.model.MultipartModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImageUploadService {
    @Multipart
    @POST("addpro.php")
    fun uploadImage(
        @Part("type") type: RequestBody,
        @Part("cid") cid: RequestBody,
        @Part image: MultipartBody.Part
    ): Call<MultipartModel>
}