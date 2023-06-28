package com.example.myapplication1

import com.example.myapplication1.model.CompleteModel
import com.example.myapplication1.model.FavourModel
import com.example.myapplication1.model.TestModel
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface TestInterface {
    @FormUrlEncoded
    @POST("true_index.php")
    suspend fun showData(
        @Field("type") type: String,
        @Field("sub_type") subtypes: String,
        @Field("uid") uid: String,
        @Field("ln") ln: String,
        @Field("lt") lt: String,
        @Field("device_id") deviceId: String,
        @Field("cid") cid: String
    ): TestModel

    @FormUrlEncoded
    @POST("true_index.php")
    suspend fun data(
        @Field("type") type: String,
        @Field("sub_type") subtypes: String,
        @Field("uid") uid: String,
        @Field("ln") ln: String,
        @Field("lt") lt: String,
        @Field("device_id") deviceId: String,
        @Field("cid") cid: String,
        @Field("led_id") ledId: String
    ): List<FavourModel>

    @FormUrlEncoded
    @POST("true_index.php")
    suspend fun complete(
        @Field("type") type: String,
        @Field("sub_type") subtypes: String,
        @Field("cid") cid: String,
        @Field("ln") ln: String,
        @Field("lt") lt: String,
        @Field("uid") uid: String,
        @Field("device_id") deviceId: String,
    ): List<CompleteModel>
}