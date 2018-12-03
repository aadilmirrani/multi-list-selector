package com.reacttive.aadilmirrani.mlslibrary.model

import android.os.Parcelable
import androidx.annotation.NonNull
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Variant(@NonNull internal val title: TagHeader, @NonNull internal val data: ArrayList<TagName>): Parcelable