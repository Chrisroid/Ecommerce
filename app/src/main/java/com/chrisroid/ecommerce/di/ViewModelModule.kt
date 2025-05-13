package com.chrisroid.ecommerce.di

import com.chrisroid.ecommerce.data.local.dao.ProductDao
import com.chrisroid.ecommerce.data.remote.api.ProductApi
import com.chrisroid.ecommerce.data.repository.ProductRepository
import com.chrisroid.ecommerce.data.repository.ProductRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {
    @Provides
    @ViewModelScoped
    fun provideProductRepository(
        productApi: ProductApi,
        productDao: ProductDao
    ): ProductRepository {
        return ProductRepositoryImpl(productApi, productDao)
    }
}