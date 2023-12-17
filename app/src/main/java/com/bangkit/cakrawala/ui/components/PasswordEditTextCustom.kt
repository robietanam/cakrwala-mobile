package com.bangkit.cakrawala.ui.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.bangkit.cakrawala.R

class PasswordEditTextCustom: AppCompatEditText, View.OnTouchListener {

    private lateinit var visibleButtonImage: Drawable
    private lateinit var invisibileButtonImage: Drawable
    private lateinit var iconButtonImage: Drawable

    private var isVisibilityButtonClicked = false

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

        visibleButtonImage = ContextCompat.getDrawable(context, R.drawable.visibility) as Drawable
        invisibileButtonImage = ContextCompat.getDrawable(context, R.drawable.visibility_off) as Drawable

        iconButtonImage = ContextCompat.getDrawable(context, R.drawable.lock) as Drawable

        transformationMethod = PasswordTransformationMethod.getInstance()

        val paddingInDp = 10

        val scale = resources.displayMetrics.density
        val paddingInPx = (paddingInDp * scale + 0.5f).toInt()

        compoundDrawablePadding = paddingInPx

        showTextButton()

        setOnTouchListener(this)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if(s.length < 8) {
                    error = "Password harus lebih dari 8 karakter"
                    Handler().postDelayed({ error = null
                    }, 1500)
                } else {
                    error = null
                }
            }
        })
    }

    private fun showTextButton() {

        if (isVisibilityButtonClicked){
            setButtonDrawables(visibleButtonImage)
        } else {
            setButtonDrawables(invisibileButtonImage)
        }

    }

    private fun setButtonDrawables(drawableButton: Drawable? = null){
        setCompoundDrawablesWithIntrinsicBounds(iconButtonImage, null, drawableButton, null)
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        if (compoundDrawables[2] != null) {

            var isButtonClicked = false

            val visibleButtonStart: Float = (width - paddingEnd - visibleButtonImage.intrinsicWidth).toFloat()

            when {
                event.x > visibleButtonStart -> isButtonClicked = true
            }

            when (event.action) {

                MotionEvent.ACTION_UP -> {
                    if (isButtonClicked){
                        if (isVisibilityButtonClicked) {

                            showTextButton()
                            transformationMethod = HideReturnsTransformationMethod.getInstance()
                        } else {
                            showTextButton()
                            transformationMethod = PasswordTransformationMethod.getInstance()
                        }

                        isVisibilityButtonClicked = !isVisibilityButtonClicked
                    } else return false

                }

            }

        }
        return false
    }

}