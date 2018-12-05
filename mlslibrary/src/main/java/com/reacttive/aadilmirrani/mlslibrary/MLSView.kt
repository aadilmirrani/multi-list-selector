package com.reacttive.aadilmirrani.mlslibrary

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.reacttive.aadilmirrani.mlslibrary.helper.AppData
import com.reacttive.aadilmirrani.mlslibrary.helper.RecyclerData
import com.reacttive.aadilmirrani.mlslibrary.helper.addHeaderTextView
import com.reacttive.aadilmirrani.mlslibrary.helper.addRecyclerView
import com.reacttive.aadilmirrani.mlslibrary.listener.OnTagSelectListener
import com.reacttive.aadilmirrani.mlslibrary.model.MLSTagStyle
import com.reacttive.aadilmirrani.mlslibrary.model.Variant
import java.util.*


class MLSView : LinearLayout {

    private val defaultHeaderTextColor = ContextCompat.getColor(context, R.color.header_text_color)
    private val defaultHeaderTextSize = resources.getDimension(R.dimen.header_text_size)
    private val defaultHeaderTypeface = ResourcesCompat.getFont(context, R.font.roboto_bold)

    private val defaultTagNormalColor = ContextCompat.getColor(context, R.color.tag_normal_color)
    private val defaultTagSelectedColor = ContextCompat.getColor(context, R.color.tag_selected_color)
    private val defaultTagDisabledColor = ContextCompat.getColor(context, R.color.tag_disabled_color)

    private val defaultTagNormalTextSize = resources.getDimension(R.dimen.tag_normal_text_size)
    private val defaultTagSelectedTextSize = resources.getDimension(R.dimen.tag_selected_text_size)

    private val defaultTagTypeface = ResourcesCompat.getFont(context, R.font.roboto_regular)

    private val defaultNormalStrokeWidth = resources.getDimension(R.dimen.tag_normal_stroke_width)
    private val defaultSelectedStrokeWidth = resources.getDimension(R.dimen.tag_selected_stroke_width)

    private val defaultTagNormalCornerRadius = resources.getDimension(R.dimen.tag_normal_corner_radius)
    private val defaultTagSelectedCornerRadius = resources.getDimension(R.dimen.tag_selected_corner_radius)

    private var headerTextColor: Int? = null
    private var headerTextSize: Float? = null
    private var headerTypeface: Typeface? = null

    private var tagNormalColor: Int? = null
    private var tagSelectedColor: Int? = null
    private var tagDisabledColor: Int? = null

    private var tagNormalTextSize: Float? = null
    private var tagSelectedTextSize: Float? = null

    private var tagTypeface: Typeface? = null

    private var normalStrokeWidth: Float? = null
    private var selectedStrokeWidth: Float? = null

    private var tagNormalCornerRadius: Float? = null
    private var tagSelectedCornerRadius: Float? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.attr.mlsGroupStyle)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        this.orientation = LinearLayout.VERTICAL

        val a = context.obtainStyledAttributes(attrs, R.styleable.MLSView, defStyle, R.style.MLSView)
        try {
            headerTextColor = a.getColor(R.styleable.MLSView_headerTextColor, defaultHeaderTextColor)
            headerTextSize = a.getDimension(R.styleable.MLSView_headerTextSize, defaultHeaderTextSize)
            headerTypeface = defaultHeaderTypeface

            tagNormalColor = a.getColor(R.styleable.MLSView_tagNormalTextColor, defaultTagNormalColor)
            tagSelectedColor = a.getColor(R.styleable.MLSView_tagSelectedTextColor, defaultTagSelectedColor)
            tagDisabledColor = a.getColor(R.styleable.MLSView_tagDisabledTextColor, defaultTagDisabledColor)

            tagNormalTextSize = a.getDimension(R.styleable.MLSView_tagNormalTextSize, defaultTagNormalTextSize)
            tagSelectedTextSize = a.getDimension(R.styleable.MLSView_tagSelectedTextSize, defaultTagSelectedTextSize)

            tagTypeface = defaultTagTypeface

            normalStrokeWidth = a.getDimension(R.styleable.MLSView_tagNormalStrokeWidth, defaultNormalStrokeWidth)
            selectedStrokeWidth = a.getDimension(R.styleable.MLSView_tagSelectedStrokeWidth, defaultSelectedStrokeWidth)

            tagNormalCornerRadius = a.getDimension(R.styleable.MLSView_tagNormalCornerRadius, defaultTagNormalCornerRadius)
            tagSelectedCornerRadius = a.getDimension(R.styleable.MLSView_tagSelectedCornerRadius, defaultTagSelectedCornerRadius)

        } finally {
            a.recycle()
        }
    }

    override fun onViewAdded(child: View?) {
        super.onViewAdded(child)
/*
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(
            ObjectAnimator.ofFloat(this, "translationY", 100f,0f),
            ObjectAnimator.ofFloat(this, "alpha", 0f, 1f)
        )
        animatorSet.interpolator = DecelerateInterpolator(3.0f)
        animatorSet.duration = 300
*/
    }

    fun clearData(){
        if(this.childCount > 0) {
            AppData.tvList.clear()
            AppData.rvList.clear()
            RecyclerData.delimiter = ""
            RecyclerData.listVariant.clear()
            RecyclerData.listValue.clear()
            this.removeAllViews()
        }
    }

    fun setData(@NonNull context: Context, @NonNull listVariant: ArrayList<Variant>, @NonNull listValue: HashMap<String, Int>, @NonNull delimiter: String) {
        val mlsTagStyle = MLSTagStyle(
            tagNormalColor?: defaultTagNormalColor,
            tagSelectedColor?: defaultTagSelectedColor,
            tagDisabledColor?: defaultTagDisabledColor,
            tagNormalTextSize?: defaultTagNormalTextSize,
            tagSelectedTextSize?: defaultTagSelectedTextSize,
            normalStrokeWidth?: defaultNormalStrokeWidth,
            selectedStrokeWidth?: defaultSelectedStrokeWidth,
            tagNormalCornerRadius?: defaultTagNormalCornerRadius,
            tagSelectedCornerRadius?: defaultTagSelectedCornerRadius)

        clearData()
        RecyclerData.delimiter = delimiter
        RecyclerData.listVariant = listVariant
        RecyclerData.listValue = listValue
        RecyclerData.generateNormal()

        for(variant in listVariant) {
            AppData.tvList.add(this.addHeaderTextView(context, variant.title, headerTextColor?: defaultHeaderTextColor, headerTextSize ?: defaultHeaderTextSize, headerTypeface))
            AppData.rvList.add(this.addRecyclerView(context, mlsTagStyle, AppData.mOnTagSelectListener, variant, tagTypeface ?: defaultTagTypeface))
        }
        this.invalidate()
    }

    fun setOnTagSelectListener(l: OnTagSelectListener) {
        AppData.mOnTagSelectListener = l
    }

    fun setTypeface(@NonNull typeface: Typeface) {
        tagTypeface = typeface
    }

    fun enableAll(@NonNull b: Boolean) {
        RecyclerData.enableAll = b
    }

    fun enablePreSelect(@NonNull b: Boolean) {
        RecyclerData.preSelected = b
    }

    @Deprecated(
        message = "Crashed with index out of bound",
        level = DeprecationLevel.WARNING,
        replaceWith = ReplaceWith(
            "",
            ""
//            "RecyclerData.hideDisabled = b",
//            "com.reacttive.aadilmirrani.mlslibrary.helper.RecyclerData"
        )
    )
    fun hideDisabledTag(@NonNull b: Boolean) {
        RecyclerData.hideDisabled = b
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