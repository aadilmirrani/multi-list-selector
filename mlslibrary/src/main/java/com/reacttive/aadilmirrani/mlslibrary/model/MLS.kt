package com.reacttive.aadilmirrani.mlslibrary.model

import android.os.Parcelable
import androidx.annotation.NonNull
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MLS(@NonNull private val group: Int, @NonNull private val position: Int) : Parcelable