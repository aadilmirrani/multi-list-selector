package com.reacttive.aadilmirrani.mlslibrary.helper

import android.content.Context
import android.util.TypedValue
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import com.reacttive.aadilmirrani.mlslibrary.model.TagHeader

internal fun LinearLayout.addHeaderTextView(@NonNull context: Context, @NonNull header: TagHeader, @NonNull txtColor: Int, @NonNull txtSize: Float): TextView {
    val textView = TextView(context)

    textView.setTextColor(txtColor)
    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, txtSize)
    AppData.headerTypeface?.let { textView.typeface = it }

    textView.text = header.title

    this.addView(textView)
    return textView
}