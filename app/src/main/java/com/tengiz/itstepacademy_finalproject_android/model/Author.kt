package com.tengiz.itstepacademy_finalproject_android.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Author(
    val id: Int,
    val name: String
) : Parcelable