package com.reacttive.aadilmirrani.mlslibrary.helper

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.reacttive.aadilmirrani.mlslibrary.listener.OnTagSelectListener

object AppData {

    internal val tvList = arrayListOf<TextView>()
    internal val rvList = arrayListOf<RecyclerView>()

    internal var mOnTagSelectListener: OnTagSelectListener? = null

    fun notifyDataSetChanged() {
        rvList.forEach { it.adapter?.notifyDataSetChanged() }
    }
}