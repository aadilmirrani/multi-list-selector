package com.reacttive.aadilmirrani.mlslibrary.utils

import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.annotation.NonNull

fun View.setTagDrawable(@NonNull color: Int, @NonNull radius: Float, @NonNull strokeWidth: Int) {

    val gradientDrawable = GradientDrawable()
    gradientDrawable.cornerRadius = radius
    gradientDrawable.setStroke(strokeWidth, color)
    this.background = gradientDrawable
}