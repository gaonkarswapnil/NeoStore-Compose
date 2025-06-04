package com.example.neostorecompose.di

import android.content.Context
import com.example.neostorecompose.data.local.dao.AddressDao
import com.example.neostorecompose.data.remote.OrderApiService
import com.example.neostorecompose.data.repositoryimpl.AddressRepoImpl
import com.example.neostorecompose.data.repositoryimpl.CartRepositoryImpl
import com.example.neostorecompose.data.repositoryimpl.OrderRepoImpl
import com.example.neostorecompose.data.repositoryimpl.ProductRepositoryImpl
import com.example.neostorecompose.data.repositoryimpl.UserRepositoryImpl
import com.example.neostorecompose.domain.repository.AddressRepository
import com.example.neostorecompose.domain.repository.CartRepository
import com.example.neostorecompose.domain.repository.OrderRepository
import com.example.neostorecompose.domain.repository.ProductRepository
import com.example.neostorecompose.domain.repository.UserRepository
import com.example.neostorecompose.utils.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DiModule {

    @Provides
    @Singleton
    fun provideUserModule(repo: UserRepositoryImpl): UserRepository{
        return repo
    }

    @Provides
    @Singleton
    fun providesTokenManager(@ApplicationContext context: Context): TokenManager{
        return TokenManager(context)
    }

    @Provides
    @Singleton
    fun providesProductModule(productRepo: ProductRepositoryImpl): ProductRepository{
        return productRepo
    }

    @Provides
    @Singleton
    fun providesCartModule(cartRepo: CartRepositoryImpl): CartRepository{
        return cartRepo
    }

    @Provides
    @Singleton
    fun providesAddressRepository(addressDao: AddressDao):AddressRepository{
        return AddressRepoImpl(addressDao)
    }


    @Provides
    @Singleton
    fun providesOrderRepository(orderapiServce:OrderApiService):OrderRepository{
        return OrderRepoImpl(orderapiServce)
    }


}