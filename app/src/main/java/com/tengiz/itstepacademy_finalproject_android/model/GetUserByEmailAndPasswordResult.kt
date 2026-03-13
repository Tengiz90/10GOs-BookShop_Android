package com.tengiz.itstepacademy_finalproject_android.model

import com.tengiz.itstepacademy_finalproject_android.extensions.toRoleString

data class GetUserByEmailAndPasswordResult(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val jwtToken: String,
    val role: Int,
   val dateOfBirth: String, // or LocalDate if I parse
    val orders: List<GetUserOrder>?,
) {
    val fullName: String
        get() = "$firstName $lastName"

    val roleString: String
        get() = role.toRoleString() // maps role int -> string
}