package com.reacttive.aadilmirrani.mlslibrary.helper

import android.graphics.Typeface
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.reacttive.aadilmirrani.mlslibrary.listener.OnTagSelectListener

internal class AppData {

    internal val tvList = hashMapOf<String, TextView>()
    internal val rvList = hashMapOf<String, RecyclerView>()

    internal var mOnTagSelectListener: OnTagSelectListener? = null

    internal var headerTypeface: Typeface? = null
    internal var tagTypeface: Typeface? = null

    fun notifyDataSetChanged() {
        rvList.forEach { it.value.adapter?.notifyDataSetChanged() }
    }
}