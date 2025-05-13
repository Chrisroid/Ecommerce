package com.chrisroid.ecommerce.features.products

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chrisroid.ecommerce.data.local.entities.Product
import com.chrisroid.ecommerce.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    val repository: ProductRepository
) : ViewModel() {
    private val _products = mutableStateOf<List<Product>>(emptyList())
    val products: State<List<Product>> = _products

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _filterCategory = mutableStateOf<String?>(null)
    val filterCategory: State<String?> = _filterCategory

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                _products.value = repository.getProducts()
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load products"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refreshProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                repository.refreshProducts()
                _products.value = repository.getProducts()
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to refresh products"
            } finally {
                _isLoading.value = false
            }
        }
    }

    val filteredProducts: List<Product>
        get() = products.value.filter { product ->
            (searchQuery.value.isEmpty() ||
                    product.name.contains(searchQuery.value, true)) &&
                    (filterCategory.value == null ||
                            product.category == filterCategory.value)
        }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setFilterCategory(category: String?) {
        _filterCategory.value = category
    }
}