package com.reacttive.aadilmirrani.mlslibrary.listener

import com.reacttive.aadilmirrani.mlslibrary.model.TagHeader
import com.reacttive.aadilmirrani.mlslibrary.model.TagName

interface OnTagSelectListener {
    fun onTagSelect(index: Int, key: String, selected: Boolean, group: TagHeader, tag: TagName, mapIndependent: LinkedHashMap<String, String>)
}