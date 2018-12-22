package com.reacttive.aadilmirrani.mlslibrary.helper

import android.content.Context
import android.util.TypedValue
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import com.reacttive.aadilmirrani.mlslibrary.model.TagHeader
import kotlin.math.roundToInt

internal fun LinearLayout.addHeaderTextView(@NonNull context: Context, @NonNull appData: AppData, @NonNull header: TagHeader, @NonNull txtColor: Int, @NonNull txtSize: Float, @NonNull headerBottomPadding: Float): TextView {
    val textView = TextView(context)

    textView.setTextColor(txtColor)
    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, txtSize)
    appData.headerTypeface?.let { textView.typeface = it }

    textView.text = header.title

    textView.setPadding(0, 0, 0, headerBottomPadding.roundToInt()/*TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, headerBottomPadding, resources.displayMetrics).roundToInt()*/)

    this.addView(textView)
    return textView
}