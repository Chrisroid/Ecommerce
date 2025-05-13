package com.chrisroid.ecommerce.features.auth

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authService: AuthService
) : ViewModel() {
    private val _authState = mutableStateOf<AuthState>(AuthState.Idle)
    val authState: State<AuthState> = _authState

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            when (val result = authService.signUp(email, password)) {
                is Resource.Success -> _authState.value = result.data?.let { AuthState.Success(it) }!!
                is Resource.Error -> _authState.value = AuthState.Error(result.message)
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            when (val result = authService.login(email, password)) {
                is Resource.Success -> _authState.value = result.data?.let { AuthState.Success(it) }!!
                is Resource.Error -> _authState.value = AuthState.Error(result.message)
            }
        }
    }

    fun logout() {
        authService.logout()
        _authState.value = AuthState.Idle
    }
}

