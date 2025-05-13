package com.chrisroid.ecommerce.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CategoryDto(
    @SerializedName("name") val name: String
)