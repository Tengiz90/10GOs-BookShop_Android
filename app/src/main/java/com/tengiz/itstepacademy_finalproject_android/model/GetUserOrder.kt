package com.tengiz.itstepacademy_finalproject_android.model

import java.util.Date

data class GetUserOrder(
    val id: Int,
    val orderDate: String,
    val orderItems: List<GetOrderItem> = emptyList()
)