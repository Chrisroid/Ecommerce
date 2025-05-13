package com.chrisroid.ecommerce.data.repository

import com.chrisroid.ecommerce.data.local.dao.ProductDao
import com.chrisroid.ecommerce.data.local.entities.Product
import com.chrisroid.ecommerce.data.remote.api.ProductApi
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProductApi,
    private val localDataSource: ProductDao
) : ProductRepository {
    override suspend fun getProducts(): List<Product> {
        return try {
            val localProducts = localDataSource.getAll()
            localProducts.ifEmpty {
                refreshProducts()
                localDataSource.getAll()
            }
        } catch (e: Exception) {
            localDataSource.getAll()
        }
    }

    override suspend fun getProductById(id: String): Product? {
        return localDataSource.getById(id) ?: run {
            refreshProducts()
            localDataSource.getById(id)
        }
    }

    override suspend fun refreshProducts() {
        try {
            val remoteProducts = remoteDataSource.getProducts()
            localDataSource.clearAll()
            localDataSource.insertAll(remoteProducts.map { it.toProduct() })
        } catch (e: Exception) {
            throw e
        }
    }
}