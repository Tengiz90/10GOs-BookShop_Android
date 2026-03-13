package com.tengiz.itstepacademy_finalproject_android.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tengiz.itstepacademy_finalproject_android.data.remote.BookShopApi
import com.tengiz.itstepacademy_finalproject_android.model.Book

class BookPagingSource(
    private val api: BookShopApi,
    private val title: String?,
    private val pageSize: Int
) : PagingSource<Int, Book>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        return try {

            val pageNumber = params.key ?: 1

            val response = api.getBooksPage(
                title = title,
                pageNumber = pageNumber,
                pageSize = pageSize
            )

            if (response.wasSuccessful && response.data != null) {

                val books = response.data

                LoadResult.Page(
                    data = books,
                    prevKey = if (pageNumber == 1) null else pageNumber - 1,
                    nextKey = if (books.isEmpty()) null else pageNumber + 1
                )

            } else {
                LoadResult.Error(Exception(response.message))
            }

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }
}