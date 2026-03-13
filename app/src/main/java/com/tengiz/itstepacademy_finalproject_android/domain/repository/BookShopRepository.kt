package com.tengiz.itstepacademy_finalproject_android.domain.repository

import androidx.paging.PagingData
import com.tengiz.itstepacademy_finalproject_android.model.Book
import com.tengiz.itstepacademy_finalproject_android.model.Category
import com.tengiz.itstepacademy_finalproject_android.utils.ResponseState
import kotlinx.coroutines.flow.Flow

interface BookShopRepository {
    suspend fun getCategories(): Flow<ResponseState<List<Category>>>

    fun getBooksPage(title: String?): Flow<PagingData<Book>>


}