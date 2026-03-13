package com.tengiz.itstepacademy_finalproject_android.model

import com.tengiz.itstepacademy_finalproject_android.extensions.toLanguageString
import org.intellij.lang.annotations.Language

data class GetOrderItem(
    val bookId: Int,
    val title: String,
    val language: Int,
    val quantity: Int,
    val imageURL: String
){
    val languageString: String
        get() = language.toLanguageString()
}
