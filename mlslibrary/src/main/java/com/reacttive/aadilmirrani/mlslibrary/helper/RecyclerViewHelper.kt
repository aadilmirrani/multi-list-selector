package com.reacttive.aadilmirrani.mlslibrary.helper

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.reacttive.aadilmirrani.mlslibrary.adapter.TagAdapter
import com.reacttive.aadilmirrani.mlslibrary.listener.OnTagSelectListener
import com.reacttive.aadilmirrani.mlslibrary.listener.RecyclerTouchListener
import com.reacttive.aadilmirrani.mlslibrary.model.MLSTagStyle
import com.reacttive.aadilmirrani.mlslibrary.model.Variant

internal fun LinearLayout.addRecyclerView(@NonNull context: Context, @NonNull mlsTagStyle: MLSTagStyle, clickListener: OnTagSelectListener?, @NonNull variant: Variant): RecyclerView {

    val flexboxLayoutManager = FlexboxLayoutManager(context)
    flexboxLayoutManager.flexWrap = FlexWrap.WRAP
    flexboxLayoutManager.flexDirection = FlexDirection.ROW
    flexboxLayoutManager.alignItems = AlignItems.STRETCH

    val recyclerView = RecyclerView(context)

    val adapter = TagAdapter(variant, mlsTagStyle)

    recyclerView.layoutManager = flexboxLayoutManager
    recyclerView.setHasFixedSize(true)
    recyclerView.adapter = adapter

    recyclerView.addOnItemTouchListener( RecyclerTouchListener(context, recyclerView,
        object: RecyclerTouchListener.ClickListener {
            override fun onClick(view: View, position: Int) {
                RecyclerData.listNormal[variant.title.key]?.get(variant.data[position].key)?.let {
//                    RecyclerData.updateSelectedData(variant.title.key, variant.data[position].key)
//                    if(!RecyclerData.enableAll) RecyclerData.updateNormalList(variant.title.key)
                    RecyclerData.updateData(variant, variant.data[position].key)
                    AppData.notifyDataSetChanged()
                    clickListener?.onTagSelect(position)
                }
            }
            override fun onLongClick(view: View?, position: Int) {}
        }
    ))

    adapter.notifyDataSetChanged()

    this.addView(recyclerView)
    return recyclerView
}