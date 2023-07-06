package com.example.myapplication1.widgets.customToast

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication1.R

class CustomToast(private val context: Context) {

    fun showToast(message: String, type: ToastType) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val mView = inflater.inflate(R.layout.custom_toast, null)
        val tvMessage = mView.findViewById<TextView>(R.id.tvToast)
        val ivICon = mView.findViewById<ImageView>(R.id.ivIcon)

        when (type) {
            ToastType.SUCCESS -> {
                ivICon.setImageResource(R.drawable.ic_tick_toast)
            }

            ToastType.ERROR -> {
                ivICon.setImageResource(R.drawable.ic_close_toast)
            }
        }

        tvMessage.text = message
        val toast = Toast(context)
        toast.duration = Toast.LENGTH_SHORT
        toast.setGravity(Gravity.BOTTOM, 0, 350)
        toast.view = mView
        toast.show()
    }
}