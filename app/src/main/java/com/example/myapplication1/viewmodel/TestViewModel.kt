package com.example.myapplication1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication1.Resources
import com.example.myapplication1.model.CompleteModel
import com.example.myapplication1.repository.TestRepository
import com.example.myapplication1.model.FavourModel
import com.example.myapplication1.model.TestModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TestViewModel(private val testRepository: TestRepository) : ViewModel() {


    private val _testFlow = MutableStateFlow<Resources<TestModel>>(Resources.Loading())
    val testFlow2: StateFlow<Resources<TestModel>>
        get() = _testFlow

    private val dataFlow = MutableStateFlow<Resources<List<FavourModel>>>(Resources.Loading())
    val dataFlow2: StateFlow<Resources<List<FavourModel>>>
        get() = dataFlow

    private val completeFlow = MutableStateFlow<Resources<List<CompleteModel>>>(Resources.Loading())
    val completeFlow2: StateFlow<Resources<List<CompleteModel>>>
        get() = completeFlow

    fun data(
        type: String,
        subType: String,
        uid: String,
        ln: String,
        lt: String,
        deviceId: String,
        cid: String,

        ) = viewModelScope.launch {
        try {
            val response = testRepository.showData(type, subType, uid, ln, lt, deviceId, cid)
            _testFlow.value = Resources.Success(response)
        } catch (exception: Exception) {
            _testFlow.value = Resources.Error(exception.message.toString())
        }
    }

    fun dataFlow(
        type: String,
        subType: String,
        cid: String,
        ln: String,
        lt: String,
        deviceId: String,
        ledId: String,
        uId: String
    ) = viewModelScope.launch {
        try {
            val response = testRepository.data(type, subType, cid, ln, lt, deviceId, ledId, uId)
            dataFlow.value = Resources.Success(response)
        } catch (exception: Exception) {
            dataFlow.value = Resources.Error(exception.message.toString())
        }
    }

    fun complete(
        type: String,
        subType: String,
        cid: String,
        ln: String,
        lt: String,
        deviceId: String,
        uid: String,
    ) = viewModelScope.launch {
        try {
            val response = testRepository.complete(type, subType, cid, ln, lt, deviceId,uid)
            completeFlow.value = Resources.Success(response)
        } catch (exception: Exception) {
            completeFlow.value = Resources.Error(exception.message.toString())
        }
    }

}