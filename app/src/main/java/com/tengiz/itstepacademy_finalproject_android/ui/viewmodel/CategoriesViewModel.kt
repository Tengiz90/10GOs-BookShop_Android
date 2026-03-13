package com.tengiz.itstepacademy_finalproject_android.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tengiz.itstepacademy_finalproject_android.domain.repository.BookShopRepository
import com.tengiz.itstepacademy_finalproject_android.model.Category
import com.tengiz.itstepacademy_finalproject_android.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val booksShopRepository: BookShopRepository
) : ViewModel() {

    private val _categoriesState =
        MutableStateFlow<ResponseState<List<Category>>>(ResponseState.Empty())
    val categoriesState: StateFlow<ResponseState<List<Category>>> = _categoriesState

    fun getCategories() {
        // Only fetch if empty
        if (_categoriesState.value is ResponseState.Empty) {
            viewModelScope.launch {
                _categoriesState.emit(ResponseState.Loading())
                val result = booksShopRepository.getCategories()
                result.collect { _categoriesState.emit(it) }
            }
        }
    }
}