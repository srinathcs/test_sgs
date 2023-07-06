package com.example.myapplication1.widgets.customEdit

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import com.example.myapplication1.databinding.CustomEdittextBinding


class CustomEditText : LinearLayout {

    private lateinit var binding: CustomEdittextBinding
    private var parentClickListener: (() -> Unit)? = null

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context, attrs, defStyle
    ) {
        initView()
    }


    fun setParentListener(listener: () -> Unit) {
        parentClickListener = listener
    }

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                parentClickListener?.invoke()
            }
        }
        return super.onInterceptTouchEvent(event)
    }

    private fun initView() {
        val inflater = LayoutInflater.from(context)
        binding = CustomEdittextBinding.inflate(inflater, this, true)
        binding.frame.setOnClickListener {
            binding.etText.setText("")
        }
        binding.etText.addTextChangedListener(textWatcher)

    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            val text = s?.toString()?.trim()
            if (text.isNullOrEmpty()) {
                binding.tvError.visibility = View.VISIBLE
            } else {
                binding.tvError.visibility = View.GONE
            }
        }
    }

    fun setSelect(isClickable: Boolean) {
        binding.etText.apply {
            inputType = if (isClickable) InputType.TYPE_CLASS_TEXT else InputType.TYPE_NULL
            isFocusable = isClickable
            isFocusableInTouchMode = isClickable
            val isClickableTemp = isClickable
            isEnabled = isClickableTemp
        }
    }

    fun setClick(click: Boolean) {
        binding.etText.apply {
            isClickable = click
            isFocusable = click
            isFocusableInTouchMode = click
            isCursorVisible = click
        }
    }

    fun getText(): String {
        return binding.etText.text.toString()
    }

    fun setupDataModel(model: CustomEditDataModel) {
        binding.label.text = model.title
        binding.tvError.text = model.error
    }

    fun setDefaultText(defaultText: String) {
        binding.etText.setText(defaultText)
    }

    fun setEndIconVisibility(visible: Boolean) {
        binding.frame.visibility = if (visible) View.VISIBLE else View.GONE
    }

    fun setText(text: String) {
        binding.etText.setText(text)
    }

    fun setError(visible: Boolean) {
        binding.tvError.visibility = if (visible) View.VISIBLE else View.GONE
    }

    fun isTextEmpty(): Boolean {
        return binding.etText.text.isNullOrEmpty()
    }

}