package com.reacttive.aadilmirrani.mlslibrary.model

import android.os.Parcelable
import androidx.annotation.NonNull
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class TagHeader(@NonNull internal val key: String, @NonNull internal val title: String, @NonNull internal val independent: Boolean = false): Parcelable