package com.reacttive.aadilmirrani.mlslibrary

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import com.reacttive.aadilmirrani.mlslibrary.helper.AppData
import com.reacttive.aadilmirrani.mlslibrary.helper.RecyclerData
import com.reacttive.aadilmirrani.mlslibrary.helper.addHeaderTextView
import com.reacttive.aadilmirrani.mlslibrary.helper.addRecyclerView
import com.reacttive.aadilmirrani.mlslibrary.listener.OnTagSelectListener
import com.reacttive.aadilmirrani.mlslibrary.model.MLSTagStyle
import com.reacttive.aadilmirrani.mlslibrary.model.Variant

import java.util.*


class MLSView : LinearLayout {
    
    private val appData = AppData()
    private val recyclerData = RecyclerData(appData)

    private val defaultGroupBottomPadding = resources.getDimension(R.dimen.groupBottomPadding)

    private val defaultHeaderTextColor = ContextCompat.getColor(context, R.color.header_text_color)
    private val defaultHeaderTextSize = resources.getDimension(R.dimen.header_text_size)
    private val defaultHeaderBottomPadding = resources.getDimension(R.dimen.header_bottom_padding)

    private val defaultTagNormalColor = ContextCompat.getColor(context, R.color.tag_normal_color)
    private val defaultTagSelectedColor = ContextCompat.getColor(context, R.color.tag_selected_color)
    private val defaultTagDisabledColor = ContextCompat.getColor(context, R.color.tag_disabled_color)

    private val defaultTagNormalTextSize = resources.getDimension(R.dimen.tag_normal_text_size)
    private val defaultTagSelectedTextSize = resources.getDimension(R.dimen.tag_selected_text_size)

    private val defaultNormalStrokeWidth = resources.getDimension(R.dimen.tag_normal_stroke_width)
    private val defaultSelectedStrokeWidth = resources.getDimension(R.dimen.tag_selected_stroke_width)

    private val defaultTagNormalCornerRadius = resources.getDimension(R.dimen.tag_normal_corner_radius)
    private val defaultTagSelectedCornerRadius = resources.getDimension(R.dimen.tag_selected_corner_radius)

    private var groupBottomPadding: Float? = null

    private var headerTextColor: Int? = null
    private var headerTextSize: Float? = null
    private var headerBottomPadding: Float? = null

    private var tagNormalColor: Int? = null
    private var tagSelectedColor: Int? = null
    private var tagDisabledColor: Int? = null

    private var tagNormalTextSize: Float? = null
    private var tagSelectedTextSize: Float? = null

    private var normalStrokeWidth: Float? = null
    private var selectedStrokeWidth: Float? = null

    private var tagNormalCornerRadius: Float? = null
    private var tagSelectedCornerRadius: Float? = null

    private var mlsTagStyle: MLSTagStyle? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.attr.mlsGroupStyle)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        this.orientation = LinearLayout.VERTICAL

        val a = context.obtainStyledAttributes(attrs, R.styleable.MLSView, defStyle, R.style.MLSView)
        try {
            groupBottomPadding = a.getDimension(R.styleable.MLSView_groupBottomPadding, defaultGroupBottomPadding)

            headerTextColor = a.getColor(R.styleable.MLSView_headerTextColor, defaultHeaderTextColor)
            headerTextSize = a.getDimension(R.styleable.MLSView_headerTextSize, defaultHeaderTextSize)
            headerBottomPadding = a.getDimension(R.styleable.MLSView_headerBottomPadding, defaultHeaderBottomPadding)

            tagNormalColor = a.getColor(R.styleable.MLSView_tagNormalTextColor, defaultTagNormalColor)
            tagSelectedColor = a.getColor(R.styleable.MLSView_tagSelectedTextColor, defaultTagSelectedColor)
            tagDisabledColor = a.getColor(R.styleable.MLSView_tagDisabledTextColor, defaultTagDisabledColor)

            tagNormalTextSize = a.getDimension(R.styleable.MLSView_tagNormalTextSize, defaultTagNormalTextSize)
            tagSelectedTextSize = a.getDimension(R.styleable.MLSView_tagSelectedTextSize, defaultTagSelectedTextSize)

            normalStrokeWidth = a.getDimension(R.styleable.MLSView_tagNormalStrokeWidth, defaultNormalStrokeWidth)
            selectedStrokeWidth = a.getDimension(R.styleable.MLSView_tagSelectedStrokeWidth, defaultSelectedStrokeWidth)

            tagNormalCornerRadius = a.getDimension(R.styleable.MLSView_tagNormalCornerRadius, defaultTagNormalCornerRadius)
            tagSelectedCornerRadius = a.getDimension(R.styleable.MLSView_tagSelectedCornerRadius, defaultTagSelectedCornerRadius)

            mlsTagStyle = MLSTagStyle(
                tagNormalColor?: defaultTagNormalColor,
                tagSelectedColor?: defaultTagSelectedColor,
                tagDisabledColor?: defaultTagDisabledColor,
                tagNormalTextSize?: defaultTagNormalTextSize,
                tagSelectedTextSize?: defaultTagSelectedTextSize,
                normalStrokeWidth?: defaultNormalStrokeWidth,
                selectedStrokeWidth?: defaultSelectedStrokeWidth,
                tagNormalCornerRadius?: defaultTagNormalCornerRadius,
                tagSelectedCornerRadius?: defaultTagSelectedCornerRadius)

        } finally {
            a.recycle()
        }
    }

    private fun clearAll() {
        appData.tvList.clear()
        appData.rvList.clear()
        recyclerData.delimiter = '+'
        recyclerData.listVariant.clear()
        recyclerData.listValue.clear()
        recyclerData.listNormal.clear()
        recyclerData.listSelected.clear()
        recyclerData.listIndependentSelected.clear()
        recyclerData.enableAll = false
        recyclerData.preSelected = false
        this.removeAllViews()
        this.invalidate()
    }

    private fun clearValues() {
        recyclerData.listValue.clear()
        recyclerData.listNormal.clear()
//        recyclerData.listSelected.clear()
//        recyclerData.listIndependentSelected.clear()
    }

    fun setData(@NonNull context: Context, @NonNull listVariant: ArrayList<Variant>, @NonNull listValue: HashMap<String, Int>, @NonNull delimiter: Char) {

        clearAll()
        recyclerData.delimiter = delimiter
        recyclerData.listVariant = listVariant.associateByTo(recyclerData.listVariant, {it.title.key}, {it} )
        recyclerData.listValue = listValue
        recyclerData.generateNormal()

        for(variant in listVariant) {
            appData.tvList[variant.title.key] = this.addHeaderTextView(context, appData, variant.title, headerTextColor?: defaultHeaderTextColor, headerTextSize ?: defaultHeaderTextSize, headerBottomPadding ?: defaultHeaderBottomPadding)
            appData.rvList[variant.title.key] = this.addRecyclerView(context, appData, recyclerData, mlsTagStyle, variant, groupBottomPadding ?: defaultGroupBottomPadding)
        }
        this.invalidate()
    }

    fun addIndependentGroup(variant: Variant) {
        if(variant.title.independent) {
            if(recyclerData.listVariant.containsKey(variant.title.key)) {
                this.removeView(appData.tvList[variant.title.key])
                this.removeView(appData.rvList[variant.title.key])

                appData.tvList.remove(variant.title.key)
                appData.tvList.remove(variant.title.key)
            }

            recyclerData.listVariant[variant.title.key] = variant
            appData.tvList[variant.title.key] = this.addHeaderTextView(context, appData, variant.title, headerTextColor?: defaultHeaderTextColor, headerTextSize ?: defaultHeaderTextSize, headerBottomPadding ?: defaultHeaderBottomPadding)
            appData.rvList[variant.title.key] = this.addRecyclerView(context, appData, recyclerData, mlsTagStyle, variant, groupBottomPadding ?: defaultGroupBottomPadding)
            recyclerData.addIndependentGroup(variant)
            appData.notifyDataSetChanged()
        }
    }

    fun removeIndependentGroup(key: String) {
        if(recyclerData.listVariant.containsKey(key)) {
            if(recyclerData.listVariant[key]?.title?.independent == true) {
                this.removeView(appData.tvList[key])
                this.removeView(appData.rvList[key])

                appData.tvList.remove(key)
                appData.tvList.remove(key)

                appData.notifyDataSetChanged()
            }
        }
    }

    fun updateValues(@NonNull listValue: HashMap<String, Int>) : Boolean {
        if(recyclerData.enableAll) {
            appData.notifyDataSetChanged()
            return true
        }
        return if(listValue.size > 0) {
            clearValues()
            recyclerData.listValue = listValue
            recyclerData.generateUpdatedValues()
            appData.notifyDataSetChanged()
            recyclerData.listNormal.size > 0
        } else {
            false
        }
    }

    fun setOnTagSelectListener(l: OnTagSelectListener) {
        appData.mOnTagSelectListener = l
    }

    fun setHeaderTypeface(typeface: Typeface?) {
        typeface?.let { tf ->
            appData.headerTypeface = tf
            appData.tvList.forEach { it.value.typeface = tf }
        }
    }

    fun setTagTypeface(typeface: Typeface?) {
        typeface?.let { tf ->
            appData.tagTypeface = tf
            appData.notifyDataSetChanged()
        }
    }

    fun enableAll(@NonNull b: Boolean) {
        recyclerData.enableAll = b
    }

    fun enablePreSelect(@NonNull b: Boolean) {
        recyclerData.preSelected = b
    }

    @Deprecated(
        message = "Crashed with index out of bound",
        level = DeprecationLevel.WARNING,
        replaceWith = ReplaceWith(
            "",
            ""
//            "recyclerData.hideDisabled = b",
//            "com.reacttive.aadilmirrani.mlslibrary.helper.recyclerData"
        )
    )
    fun hideDisabledTag(@NonNull b: Boolean) {
//        recyclerData.hideDisabled = b
    }
/*
    private fun Float.dp2px(): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, resources.displayMetrics)
    }

    private fun Float.sp2px(): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this, resources.displayMetrics)
    }
*/
}