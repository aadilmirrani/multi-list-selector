package com.reacttive.aadilmirrani.mlslibrary.adapter

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.reacttive.aadilmirrani.mlslibrary.R
import com.reacttive.aadilmirrani.mlslibrary.helper.RecyclerData
import com.reacttive.aadilmirrani.mlslibrary.model.MLSTagStyle
import com.reacttive.aadilmirrani.mlslibrary.model.Variant
import com.reacttive.aadilmirrani.mlslibrary.utils.setTagDrawable
import kotlin.math.roundToInt


class TagAdapter internal constructor(@NonNull private val variant: Variant, @NonNull private val mlsTagStyle: MLSTagStyle) : RecyclerView.Adapter<TagAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.tv_it_tag)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tag, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val title = variant.data[position].title
        RecyclerData.listNormal[variant.title.key]?.get(variant.data[position].key)?.let {
            holder.title.setTagDrawable(mlsTagStyle.tagNormalColor, mlsTagStyle.tagNormalCornerRadius, mlsTagStyle.normalStrokeWidth.roundToInt())
            holder.title.setTextColor(mlsTagStyle.tagNormalColor)
            holder.title.setTextSize(TypedValue.COMPLEX_UNIT_PX, mlsTagStyle.tagNormalTextSize)
        } ?: kotlin.run {
            if(RecyclerData.hideDisabled) {
                holder.itemView.visibility = View.GONE
            } else {
                holder.title.setTagDrawable(mlsTagStyle.tagDisabledColor, mlsTagStyle.tagNormalCornerRadius, mlsTagStyle.normalStrokeWidth.roundToInt())
                holder.title.setTextColor(mlsTagStyle.tagDisabledColor)
                holder.title.setTextSize(TypedValue.COMPLEX_UNIT_PX, mlsTagStyle.tagNormalTextSize)
            }
        }

        RecyclerData.listSelected[variant.title.key]?.let {
            if(it == variant.data[position].key) {
                holder.title.setTagDrawable(mlsTagStyle.tagSelectedColor, mlsTagStyle.tagSelectedCornerRadius, mlsTagStyle.selectedStrokeWidth.roundToInt())
                holder.title.setTextColor(mlsTagStyle.tagSelectedColor)
                holder.title.setTextSize(TypedValue.COMPLEX_UNIT_PX, mlsTagStyle.tagSelectedTextSize)
            }
        }
        RecyclerData.listIndependentSelected[variant.title.key]?.let {
            if(it == variant.data[position].key) {
                holder.title.setTagDrawable(mlsTagStyle.tagSelectedColor, mlsTagStyle.tagSelectedCornerRadius, mlsTagStyle.selectedStrokeWidth.roundToInt())
                holder.title.setTextColor(mlsTagStyle.tagSelectedColor)
                holder.title.setTextSize(TypedValue.COMPLEX_UNIT_PX, mlsTagStyle.tagSelectedTextSize)
            }
        }
        holder.title.text = title
    }

    override fun getItemCount(): Int {
        return variant.data.size
    }
}