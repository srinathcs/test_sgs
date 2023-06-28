package com.example.myapplication1.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication1.databinding.ActivityElevenBinding
import com.example.myapplication1.model.MyData
import com.facebook.internal.BundleJSONConverter.convertToJSON
import org.json.JSONArray
import org.json.JSONObject

class ElevenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityElevenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityElevenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = listOf(
            MyData(1, "John", 25),
            MyData(2, "Jane", 30),
            MyData(3, "Tom", 28),
            MyData(4, "Sri", 32)
        )
        val jsonData = convertToJSON(data)
        binding.tvTitle.text = jsonData
    }

    private fun convertToJSON(data: List<MyData>): String {
        val jsonArray = JSONArray()

        for (item in data) {
            val jsonObject = JSONObject()
            jsonObject.put("id", item.id)
            jsonObject.put("name", item.name)
            jsonObject.put("age", item.age)

            jsonArray.put(jsonObject)
        }
        return jsonArray.toString()
    }
}