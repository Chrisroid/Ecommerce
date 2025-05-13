package com.chrisroid.ecommerce.data.remote.api

import com.chrisroid.ecommerce.data.remote.dto.ProductDto
import retrofit2.http.GET

interface ProductApi {
    @GET("products")
    suspend fun getProducts(): List<ProductDto>
}