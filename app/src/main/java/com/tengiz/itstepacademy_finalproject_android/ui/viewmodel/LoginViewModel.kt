package com.tengiz.itstepacademy_finalproject_android.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tengiz.itstepacademy_finalproject_android.domain.repository.BookShopRepository
import com.tengiz.itstepacademy_finalproject_android.model.GetUserByEmailAndPasswordResult
import com.tengiz.itstepacademy_finalproject_android.model.SignInUser
import com.tengiz.itstepacademy_finalproject_android.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: BookShopRepository
) : ViewModel() {

    private val _loginState =
        MutableStateFlow<ResponseState<GetUserByEmailAndPasswordResult>>(ResponseState.Empty())
    val loginState: StateFlow<ResponseState<GetUserByEmailAndPasswordResult>> get() = _loginState

    fun signIn(email: String, password: String, client: Int) {
        viewModelScope.launch {
            val signInUser = SignInUser(email, password, client)
            repository.signIn(signInUser).collect { result ->
                _loginState.value = result
            }
        }
    }

    fun resetState() {
        _loginState.value = ResponseState.Empty()
    }
}