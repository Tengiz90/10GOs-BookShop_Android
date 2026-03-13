package com.tengiz.itstepacademy_finalproject_android.di

import com.tengiz.itstepacademy_finalproject_android.data.repository.BookShopRepositoryImpl
import com.tengiz.itstepacademy_finalproject_android.domain.repository.BookShopRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMyBookShopRepository(
        myRepositoryImpl: BookShopRepositoryImpl
    ): BookShopRepository


}

