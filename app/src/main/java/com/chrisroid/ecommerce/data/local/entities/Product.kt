package com.chrisroid.ecommerce.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val category: String,
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis()
)
