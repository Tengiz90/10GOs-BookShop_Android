package com.tengiz.itstepacademy_finalproject_android.model

data class Book(
    val id: Int,
    val title: String,
    val authors: List<Author>,
    val language: Int,
    val quantity: Int,
    val imageURL: String
)
