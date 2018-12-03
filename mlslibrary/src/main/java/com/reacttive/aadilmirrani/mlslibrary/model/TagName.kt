package com.reacttive.aadilmirrani.mlslibrary.model

import android.os.Parcelable
import androidx.annotation.NonNull
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TagName(@NonNull internal val key: String, @NonNull internal val title: String, @NonNull internal val selected: Boolean = false): Parcelable