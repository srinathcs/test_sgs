package com.example.myapplication1.holder

import android.content.Context
import com.example.myapplication1.widgets.customEdit.CustomEditDataModel

class CustomEditTextDataHolder(private val context: Context) {

    fun getInputText(): CustomEditDataModel {
        return CustomEditDataModel("name", "please enter the name")
    }

    fun getInputEmail(): CustomEditDataModel {
        return CustomEditDataModel("email", "please enter the email")
    }
}