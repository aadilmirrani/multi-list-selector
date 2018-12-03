package com.reacttive.aadilmirrani.mlslibrary.model

import android.os.Parcelable
import androidx.annotation.NonNull
import kotlinx.android.parcel.Parcelize

@Parcelize
internal data class MLSTagStyle(
    @NonNull internal val tagNormalColor: Int,
    @NonNull internal val tagSelectedColor: Int,
    @NonNull internal val tagDisabledColor: Int,
    @NonNull internal val tagNormalTextSize: Float,
    @NonNull internal val tagSelectedTextSize: Float,
    @NonNull internal val normalStrokeWidth: Float,
    @NonNull internal val selectedStrokeWidth: Float,
    @NonNull internal val tagNormalCornerRadius: Float,
    @NonNull internal val tagSelectedCornerRadius: Float): Parcelable