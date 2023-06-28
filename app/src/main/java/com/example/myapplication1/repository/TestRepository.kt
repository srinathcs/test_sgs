package com.example.myapplication1.repository

import com.example.myapplication1.retrofit.Retrofit

class TestRepository {
    suspend fun showData(
        type: String,
        sub_type: String,
        uid: String,
        ln: String,
        lt: String,
        device_id: String,
        cid: String,
    ) = Retrofit.api.showData(type, sub_type, uid, ln, lt, device_id, cid)

    suspend fun data(
        type: String,
        sub_type: String,
        uid: String,
        ln: String,
        lt: String,
        device_id: String,
        cid: String,
        led_id: String
    ) = Retrofit.api.data(type, sub_type, uid, ln, lt, device_id, cid, led_id)

    suspend fun complete(
        type: String,
        sub_type: String,
        cid: String,
        ln: String,
        lt: String,
        device_id: String,
        uid: String,
    ) = Retrofit.api.complete(type, sub_type, cid, ln, lt, uid,device_id)

}