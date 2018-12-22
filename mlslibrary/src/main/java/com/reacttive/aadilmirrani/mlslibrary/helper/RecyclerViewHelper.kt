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

internal fun LinearLayout.addRecyclerView(@NonNull context: Context, @NonNull appData: AppData, @NonNull recyclerData: RecyclerData, mlsTagStyle: MLSTagStyle?, @NonNull variant: Variant, @NonNull groupBottomPadding: Float): RecyclerView {

    val paddingPx =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, groupBottomPadding, context.resources.displayMetrics)
            .toInt()

    val flexboxLayoutManager = FlexboxLayoutManager(context)
    flexboxLayoutManager.flexWrap = FlexWrap.WRAP
    flexboxLayoutManager.flexDirection = FlexDirection.ROW
    flexboxLayoutManager.alignItems = AlignItems.STRETCH

    val recyclerView = RecyclerView(context)

    val adapter = TagAdapter(appData, recyclerData, variant, mlsTagStyle)

    recyclerView.layoutManager = flexboxLayoutManager
    recyclerView.setHasFixedSize(true)
    recyclerView.setPadding(0, 0, 0, paddingPx)
    recyclerView.adapter = adapter

    recyclerView.addOnItemTouchListener(RecyclerTouchListener(context, recyclerView,
        object : RecyclerTouchListener.ClickListener {
            override fun onClick(view: View, position: Int) {
                recyclerData.listNormal[variant.title.key]?.get(variant.data[position].key)?.let {
                    //                    recyclerData.updateSelectedData(variant.title.key, variant.data[position].key)
//                    if(!recyclerData.enableAll) recyclerData.updateNormalList(variant.title.key)
                    if (recyclerData.listSelected[variant.title.key]?.equals(variant.data[position].key) == true ||
                        recyclerData.listIndependentSelected[variant.title.key]?.equals(variant.data[position].key) == true
                    ) {
                        appData.mOnTagSelectListener?.onTagSelect(
                            position,
                            recyclerData.getPQKey(),
                            recyclerData.isSelected(),
                            variant.title,
                            variant.data[position],
                            recyclerData.listIndependentSelected
                        )
                    } else {
                        recyclerData.updateData(variant, variant.data[position].key)
                        appData.notifyDataSetChanged()
                        appData.mOnTagSelectListener?.onTagSelect(
                            position,
                            recyclerData.getPQKey(),
                            recyclerData.isSelected(),
                            variant.title,
                            variant.data[position],
                            recyclerData.listIndependentSelected
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