package com.example.myapplication1.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication1.databinding.FragmentElevenBinding
import com.example.myapplication1.model.MyData
import org.json.JSONArray
import org.json.JSONObject

class ElevenFragment : Fragment() {

    private var _binding: FragmentElevenBinding? = null
    private val binding get() = _binding!!
    private lateinit var jsonArray: JSONArray

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentElevenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = listOf(
            MyData(1, "John", 25),
            MyData(2, "Jane", 30),
            MyData(3, "Tom", 28),
            MyData(4, "Sri", 32)
        )

        jsonArray = JSONArray()
        val jsonArray = convertToJSON(data)
        binding.tvTitle.text = jsonArray
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun convertToJSON(data: List<MyData>): String {

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