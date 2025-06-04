package com.example.neostorecompose.di

import android.app.Application
import androidx.room.Room
import com.example.neostorecompose.data.local.dao.AddressDao
import com.example.neostorecompose.data.local.roomHelper.RoomDbInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): RoomDbInstance {
        return Room.databaseBuilder(
            application,
            RoomDbInstance::class.java,
            "neostore_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideAddressDao(database: RoomDbInstance): AddressDao {
        return database.addressDao()
    }

}
