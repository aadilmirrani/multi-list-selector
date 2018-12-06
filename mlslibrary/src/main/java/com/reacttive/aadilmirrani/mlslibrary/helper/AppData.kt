package com.reacttive.aadilmirrani.mlslibrary.helper

import android.graphics.Typeface
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.reacttive.aadilmirrani.mlslibrary.listener.OnTagSelectListener

object AppData {

    internal val tvList = arrayListOf<TextView>()
    internal val rvList = arrayListOf<RecyclerView>()

    internal var mOnTagSelectListener: OnTagSelectListener? = null

    internal var headerTypeface: Typeface? = null
    internal var tagTypeface: Typeface? = null

    fun notifyDataSetChanged() {
        rvList.forEach { it.adapter?.notifyDataSetChanged() }
    }
}