package com.bangkit.cakrawala.ui.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.bangkit.cakrawala.R

class EmailEditTextCustom: AppCompatEditText{

    private lateinit var iconButtonImage: Drawable


    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun init() {
        // Menginisialisasi gambar clear button
        iconButtonImage = ContextCompat.getDrawable(context, R.drawable.mail) as Drawable

        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+.[A-Za-z0-9.-]\$"

        val paddingInDp = 10

        val scale = resources.displayMetrics.density
        val paddingInPx = (paddingInDp * scale + 0.5f).toInt()

        compoundDrawablePadding = paddingInPx

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if(!s.matches(emailRegex.toRegex())) {
                    error = "Format email salah"
                    Handler().postDelayed({ error = null
                    }, 2000)
                } else {
                    error = null
                }
            }
        })


        setCompoundDrawablesWithIntrinsicBounds(iconButtonImage, null, null, null)
    }


}