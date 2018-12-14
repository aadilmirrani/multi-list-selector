package com.reacttive.aadilmirrani.mlslibrary.model

import android.os.Parcelable
import androidx.annotation.NonNull
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TagName(@NonNull val key: String, @NonNull val title: String, @NonNull val selected: Boolean = false): Parcelable