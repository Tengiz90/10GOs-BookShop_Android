package com.tengiz.itstepacademy_finalproject_android.data.common

data class ApiResponse<T>(
    val wasSuccessful: Boolean,
    val message: String,
    val data: T?
)