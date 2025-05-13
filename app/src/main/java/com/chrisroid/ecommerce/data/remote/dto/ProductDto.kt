package com.chrisroid.ecommerce.data.remote.dto

import com.chrisroid.ecommerce.data.local.entities.Product
import com.google.gson.annotations.SerializedName

data class ProductDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val name: String,
    @SerializedName("price") val price: Double,
    @SerializedName("description") val description: String,
    @SerializedName("images") val images: List<String>,
    @SerializedName("category") val category: CategoryDto
) {
    fun toProduct(): Product {
        return Product(
            id = id.toString(),
            name = name,
            price = price,
            description = description,
            imageUrl = images.firstOrNull() ?: "",
            category = category.name
        )
    }
}