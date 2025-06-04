package com.example.neostorecompose.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.neostorecompose.data.local.model.AddressDb
import kotlinx.coroutines.flow.Flow

@Dao
interface AddressDao {
    @Query("SELECT * FROM address")
    fun getAllAddresses(): Flow<List<AddressDb>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAddress(address: AddressDb)

    @Delete
    suspend fun deleteAddress(address: AddressDb)

}