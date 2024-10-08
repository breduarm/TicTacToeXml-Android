package com.beam.tictactoe.ui.board

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.beam.tictactoe.R

class CellView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        foreground = ContextCompat.getDrawable(context, R.drawable.cell_border)
        setTextAppearance(com.google.android.material.R.style.TextAppearance_MaterialComponents_Headline4)

        val outValue = TypedValue()
        context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
        setBackgroundResource(outValue.resourceId)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var width = measuredWidth
        var height = measuredHeight

        if (width == 0 && height == 0) return

        if (width > 0) {
            height = width
        } else {
            width = height
        }

        setMeasuredDimension(width, height)
    }

    fun update(value: String, enabled: Boolean) {
        text = value
        isClickable = text.isNullOrBlank() && enabled
    }
}