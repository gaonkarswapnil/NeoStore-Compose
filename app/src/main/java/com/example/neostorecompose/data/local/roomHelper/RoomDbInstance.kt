package com.example.neostorecompose.data.local.roomHelper

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.neostorecompose.data.local.dao.AddressDao
import com.example.neostorecompose.data.local.model.AddressDb

@Database(entities = [AddressDb::class], version = 1, exportSchema = false)
abstract class RoomDbInstance : RoomDatabase() {
    abstract fun addressDao() : AddressDao
}