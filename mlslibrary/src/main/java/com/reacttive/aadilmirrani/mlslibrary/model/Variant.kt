package com.reacttive.aadilmirrani.mlslibrary.model

import android.os.Parcelable
import androidx.annotation.NonNull
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Variant(@NonNull val title: TagHeader, @NonNull val data: ArrayList<TagName>): Parcelable