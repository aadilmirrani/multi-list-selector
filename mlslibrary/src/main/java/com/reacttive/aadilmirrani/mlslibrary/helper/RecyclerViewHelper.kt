package com.reacttive.aadilmirrani.mlslibrary.helper

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.reacttive.aadilmirrani.mlslibrary.adapter.TagAdapter
import com.reacttive.aadilmirrani.mlslibrary.listener.RecyclerTouchListener
import com.reacttive.aadilmirrani.mlslibrary.model.MLSTagStyle
import com.reacttive.aadilmirrani.mlslibrary.model.Variant


internal fun LinearLayout.addRecyclerView(@NonNull context: Context, mlsTagStyle: MLSTagStyle?, @NonNull variant: Variant, @NonNull groupBottomPadding: Float): RecyclerView {

    val paddingPx =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, groupBottomPadding, context.resources.displayMetrics)
            .toInt()

    val flexboxLayoutManager = FlexboxLayoutManager(context)
    flexboxLayoutManager.flexWrap = FlexWrap.WRAP
    flexboxLayoutManager.flexDirection = FlexDirection.ROW
    flexboxLayoutManager.alignItems = AlignItems.STRETCH

    val recyclerView = RecyclerView(context)

    val adapter = TagAdapter(variant, mlsTagStyle)

    recyclerView.layoutManager = flexboxLayoutManager
    recyclerView.setHasFixedSize(true)
    recyclerView.setPadding(0, 0, 0, paddingPx)
    recyclerView.adapter = adapter

    recyclerView.addOnItemTouchListener(RecyclerTouchListener(context, recyclerView,
        object : RecyclerTouchListener.ClickListener {
            override fun onClick(view: View, position: Int) {
                RecyclerData.listNormal[variant.title.key]?.get(variant.data[position].key)?.let {
                    //                    RecyclerData.updateSelectedData(variant.title.key, variant.data[position].key)
//                    if(!RecyclerData.enableAll) RecyclerData.updateNormalList(variant.title.key)
                    if (RecyclerData.listSelected[variant.title.key]?.equals(variant.data[position].key) == true ||
                        RecyclerData.listIndependentSelected[variant.title.key]?.equals(variant.data[position].key) == true
                    ) {
                        AppData.mOnTagSelectListener?.onTagSelect(
                            position,
                            RecyclerData.getPQKey(),
                            RecyclerData.isSelected(),
                            variant.title,
                            variant.data[position],
                            RecyclerData.listIndependentSelected
                        )
                    } else {
                        RecyclerData.updateData(variant, variant.data[position].key)
                        AppData.notifyDataSetChanged()
                        AppData.mOnTagSelectListener?.onTagSelect(
                            position,
                            RecyclerData.getPQKey(),
                            RecyclerData.isSelected(),
                            variant.title,
                            variant.data[position],
                            RecyclerData.listIndependentSelected
                        )
                    }
                }
            }

            override fun onLongClick(view: View?, position: Int) {}
        }
    ))

    adapter.notifyDataSetChanged()

    this.addView(recyclerView)
    return recyclerView
}