package com.example.neostorecompose.di

import android.os.Build
import com.example.neostorecompose.BuildConfig
import com.example.neostorecompose.data.remote.CartApiService
import com.example.neostorecompose.data.remote.OrderApiService
import com.example.neostorecompose.data.remote.ProductApiService
import com.example.neostorecompose.data.remote.UserApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitHelper {

    @Provides
    @Singleton
    fun provideRetrofitService(): Retrofit{
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserApiService{
        return retrofit.create(UserApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideProductService(retrofit: Retrofit): ProductApiService{
        return retrofit.create(ProductApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCartService(retrofit: Retrofit): CartApiService{
        return retrofit.create(CartApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideOrderService(retrofit: Retrofit): OrderApiService{
        return retrofit.create(OrderApiService::class.java)
    }




}