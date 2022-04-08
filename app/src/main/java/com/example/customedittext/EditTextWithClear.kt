package com.example.customedittext

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
import android.text.Editable

import android.text.TextWatcher

import android.R

import android.view.MotionEvent
import android.view.View.OnTouchListener


class EditTextWithClear: AppCompatEditText {

    lateinit var mClearButtonImage: Drawable


    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context:Context, attrs: AttributeSet): super(context , attrs){
        init()
    }

    constructor(context:Context, attrs:AttributeSet, defStyleAttr:Int):
            super(context , attrs , defStyleAttr){
        init()
            }

    private fun init(){

        // Initialize Drawable member variable.
        mClearButtonImage = ResourcesCompat.getDrawable(resources , R.drawable.ic_notification_clear_all,null)!!

        // TODO: If the clear (X) button is tapped, clear the text.

        setOnTouchListener(OnTouchListener { v, event -> // Use the getCompoundDrawables()[2] expression to check
            // if the drawable is on the "end" of text [2].
            if (compoundDrawablesRelative[2] != null) {
                val clearButtonStart: Float // Used for LTR languages
                val clearButtonEnd: Float // Used for RTL languages
                var isClearButtonClicked = false
                // Detect the touch in RTL or LTR layout direction.
                if (layoutDirection == LAYOUT_DIRECTION_RTL) {
                    // If RTL, get the end of the button on the left side.
                    clearButtonEnd = (mClearButtonImage
                        .intrinsicWidth + paddingStart).toFloat()
                    // If the touch occurred before the end of the button,
                    // set isClearButtonClicked to true.
                    if (event.x < clearButtonEnd) {
                        isClearButtonClicked = true
                    }
                } else {
                    // Layout is LTR.
                    // Get the start of the button on the right side.
                    clearButtonStart = (width - paddingEnd
                            - mClearButtonImage.intrinsicWidth).toFloat()
                    // If the touch occurred after the start of the button,
                    // set isClearButtonClicked to true.
                    if (event.x > clearButtonStart) {
                        isClearButtonClicked = true
                    }
                }
                // Check for actions if the button is tapped.
                if (isClearButtonClicked) {
                    // Check for ACTION_DOWN (always occurs before ACTION_UP).
                    if (event.action == MotionEvent.ACTION_DOWN) {
                        // Switch to the black version of clear button.
                        mClearButtonImage = ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.ic_notification_clear_all, null
                        )!!
                        showClearButton()
                    }
                    // Check for ACTION_UP.
                    if (event.action == MotionEvent.ACTION_UP) {
                        // Switch to the opaque version of clear button.
                        mClearButtonImage = ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.ic_notification_clear_all, null
                        )!!
                        // Clear the text and hide the clear button.
                        text!!.clear()
                        hideClearButton()
                        return@OnTouchListener true
                    }
                } else {
                    return@OnTouchListener false
                }
            }
            false
        })

        // If the text changes, show or hide the X (clear) button.

        // If the text changes, show or hide the X (clear) button.
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int, count: Int, after: Int
            ) {
                // Do nothing.
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int, before: Int, count: Int
            ) {
                showClearButton()
            }

            override fun afterTextChanged(s: Editable) {
                // Do nothing.
            }
        })


            // TODO: If the text changes, show or hide the clear (X) button.
    }

    /**
     * Shows the clear (X) button.
     */
    private fun showClearButton(){
        setCompoundDrawablesRelativeWithIntrinsicBounds(
            null  // Start of text.
            ,null // Above text.
            , mClearButtonImage // End of text.
            , null // Below text.
        )
    }

    /**
     * Hides the clear button.
     */
    private fun hideClearButton(){

        setCompoundDrawablesRelativeWithIntrinsicBounds(
            null  // Start of text.
            ,null // Above text.
            , null // End of text.
            , null // Below text.
        )
    }
}