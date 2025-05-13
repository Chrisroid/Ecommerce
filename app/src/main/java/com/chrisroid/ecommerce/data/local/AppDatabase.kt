package com.chrisroid.ecommerce.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chrisroid.ecommerce.data.local.dao.ProductDao
import com.chrisroid.ecommerce.data.local.entities.Product

@Database(entities = [Product::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}