package com.joni.assignment.di

import android.content.Context
import com.joni.assignment.data.network.ApiService
import com.joni.assignment.data.offline.ProductDao
import com.joni.assignment.data.repository.RepoImpl
import com.joni.assignment.domain.ProductRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Singleton
    @Provides
    fun providesProductRepo(apiService: ApiService, productDao: ProductDao, @ApplicationContext context : Context): ProductRepo {
        return RepoImpl(apiService, productDao, context)
    }
}