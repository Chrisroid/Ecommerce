package com.chrisroid.ecommerce.features.auth

import com.google.firebase.auth.FirebaseUser

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    class Success(val user: FirebaseUser) : AuthState()
    class Error(val message: String?) : AuthState()
}