package com.tengiz.itstepacademy_finalproject_android.data.remote

import com.tengiz.itstepacademy_finalproject_android.data.common.ApiResponse
import com.tengiz.itstepacademy_finalproject_android.model.Book
import com.tengiz.itstepacademy_finalproject_android.model.Category
import com.tengiz.itstepacademy_finalproject_android.model.GetUserByEmailAndPasswordResult
import com.tengiz.itstepacademy_finalproject_android.model.SignInUser
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface BookShopApi {
    @GET("categories/get-all")
    suspend fun getCategories(): ApiResponse<List<Category>>

    @GET("books/page")
    suspend fun getBooksPage(
        @Query("title") title: String?,
        @Query("pageNumber") pageNumber: Int,
        @Query("pageSize") pageSize: Int
    ): ApiResponse<List<Book>>

    @POST("users/sign-in")
    suspend fun signIn(
        @Body signInUser: SignInUser
    ): ApiResponse<GetUserByEmailAndPasswordResult>

}