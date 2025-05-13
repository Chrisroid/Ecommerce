package com.chrisroid.ecommerce.auth

import com.chrisroid.ecommerce.features.auth.AuthService
import com.chrisroid.ecommerce.features.auth.AuthState
import com.chrisroid.ecommerce.features.auth.AuthViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

//@HiltAndroidTest
@ExperimentalCoroutinesApi
class AuthViewModelTest {
    @get:Rule
//    var hiltRule = HiltAndroidRule(this)

//    @get:Rule
//    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var authService: AuthService

    private lateinit var viewModel: AuthViewModel

    @Before
    fun setup() {
//        hiltRule.inject()
        viewModel = AuthViewModel(authService)
    }

    @Test
    fun `login should update state correctly`() = runTest {
        val email = "test@example.com"
        val password = "password"

        viewModel.login(email, password)

        assertEquals(AuthState.Loading, viewModel.authState.value)
        advanceUntilIdle()

        assertTrue(viewModel.authState.value is AuthState.Error ||
                viewModel.authState.value is AuthState.Success)
    }

    @Test
    fun `signUp should update state correctly`() = runTest {
        val email = "test@example.com"
        val password = "password"

        viewModel.signUp(email, password)

        assertEquals(AuthState.Loading, viewModel.authState.value)
        advanceUntilIdle()

        assertTrue(viewModel.authState.value is AuthState.Error ||
                viewModel.authState.value is AuthState.Success)
    }
}