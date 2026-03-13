package com.tengiz.itstepacademy_finalproject_android.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tengiz.itstepacademy_finalproject_android.domain.repository.BookShopRepository
import com.tengiz.itstepacademy_finalproject_android.model.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class HomeBooksViewModel @Inject constructor(
    private val repository: BookShopRepository
) : ViewModel() {

    private val queryFlow = MutableStateFlow<String?>(null)

    // Update search query
    fun searchBooks(query: String?) {
        queryFlow.value = query
    }

    // Books Flow from repository
    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val booksFlow: Flow<PagingData<Book>> = queryFlow
        .debounce(300) // optional: small delay for search typing
        .distinctUntilChanged()
        .flatMapLatest { query ->
            repository.getBooksPage(query)
        }
        .cachedIn(viewModelScope)
}