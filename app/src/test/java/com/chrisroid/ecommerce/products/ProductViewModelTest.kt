package com.chrisroid.ecommerce.products

import com.chrisroid.ecommerce.data.local.dao.ProductDao
import com.chrisroid.ecommerce.data.remote.api.ProductApi
import com.chrisroid.ecommerce.data.remote.dto.CategoryDto
import com.chrisroid.ecommerce.data.remote.dto.ProductDto
import com.chrisroid.ecommerce.data.repository.ProductRepositoryImpl
import com.chrisroid.ecommerce.features.products.ProductViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.whenever
import javax.inject.Inject

//@HiltAndroidTest
@ExperimentalCoroutinesApi
class ProductViewModelTest {
    @get:Rule
//    var hiltRule = HiltAndroidRule(this)

//    @get:Rule
//    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var productApi: ProductApi

    @Inject
    lateinit var productDao: ProductDao

    private lateinit var viewModel: ProductViewModel

    @Before
    fun setup() {
//        hiltRule.inject()
        viewModel = ProductViewModel(ProductRepositoryImpl(productApi, productDao))
    }

    @Test
    fun `loadProducts should update state correctly`() = runTest {
        val mockProducts = listOf(
            ProductDto(1, "Product 1", 10.0, "Desc 1", listOf("url1"), CategoryDto("category1")),
            ProductDto(2, "Product 2", 20.0, "Desc 2", listOf("url2"), CategoryDto("category2"))
        )

        whenever(productApi.getProducts()).thenReturn(mockProducts)

        viewModel.loadProducts()

        assertTrue(viewModel.isLoading.value)
        advanceUntilIdle()

        assertFalse(viewModel.isLoading.value)
        assertEquals(2, viewModel.products.value.size)
        assertNull(viewModel.error.value)
    }

    @Test
    fun `loadProducts should handle errors`() = runTest {
        val errorMessage = "Network error"
        whenever(productApi.getProducts()).thenThrow(RuntimeException(errorMessage))

        viewModel.loadProducts()

        assertTrue(viewModel.isLoading.value)
        advanceUntilIdle()

        assertFalse(viewModel.isLoading.value)
        assertEquals(errorMessage, viewModel.error.value)
        assertTrue(viewModel.products.value.isEmpty())
    }
}