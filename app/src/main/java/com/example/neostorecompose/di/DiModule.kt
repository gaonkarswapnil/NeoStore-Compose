package com.example.neostorecompose.di

import android.content.Context
import com.example.neostorecompose.data.repositoryimpl.UserRepositoryImpl
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

}