package com.tengiz.itstepacademy_finalproject_android.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tengiz.itstepacademy_finalproject_android.data.common.ApiResponse
import com.tengiz.itstepacademy_finalproject_android.data.remote.BookShopApi
import com.tengiz.itstepacademy_finalproject_android.data.repository.paging.BookPagingSource
import com.tengiz.itstepacademy_finalproject_android.domain.repository.BookShopRepository
import com.tengiz.itstepacademy_finalproject_android.model.Book
import com.tengiz.itstepacademy_finalproject_android.model.Category
import com.tengiz.itstepacademy_finalproject_android.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class BookShopRepositoryImpl @Inject constructor(private val api: BookShopApi) :
    BookShopRepository {
        // Safe API call for ApiResponse<T>
        private suspend fun <T> safeApiCall(
            apiCall: suspend () -> ApiResponse<T>
        ): ResponseState<T> {
            return try {
                val response = apiCall()
                if (response.wasSuccessful) {
                    ResponseState.Success(response.data ?: throw Exception("No data"))
                } else {
                    ResponseState.Error(response.message)
                }
            } catch (e: Exception) {
                ResponseState.Error(e.message ?: "Unknown error")
            }
        }

    override suspend fun getCategories(): Flow<ResponseState<List<Category>>> = flow {
        emit(ResponseState.Loading())
        val result = safeApiCall { api.getCategories() }
        emit(result)
    }
    override fun getBooksPage(title: String?): Flow<PagingData<Book>> {

        val pageSize = 10

        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                initialLoadSize = pageSize,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { BookPagingSource(api, title, pageSize) }
        ).flow
    }


}