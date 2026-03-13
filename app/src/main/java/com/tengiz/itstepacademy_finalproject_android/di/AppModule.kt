package com.tengiz.itstepacademy_finalproject_android.di

import android.content.Context
import android.util.Log.d
import com.tengiz.itstepacademy_finalproject_android.data.local.SecureStorage
import com.tengiz.itstepacademy_finalproject_android.data.remote.BookShopApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request()
            val url = request.url.toString()
//            d("Request URL", url)
            chain.proceed(request)
        }
        .readTimeout(10, TimeUnit.SECONDS).build()

    @Provides
    @Singleton
    fun provideRetrofit(@ApplicationContext context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://10gosbooksapp-cge6dhaxgnepdahd.westeurope-01.azurewebsites.net/api/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMyApi(retrofit: Retrofit): BookShopApi {
        return retrofit.create(BookShopApi::class.java)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object AppModule {

        @Provides
        @Singleton
        fun provideSecureStorage(@ApplicationContext context: Context): SecureStorage {
            return SecureStorage(context)
        }

        // ... other providers
    }
}