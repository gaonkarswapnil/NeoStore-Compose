package com.example.neostorecompose.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "address")
data class AddressDb(
    @PrimaryKey(autoGenerate = true) val addressId: Int = 0,
    val fullAddress: String,
    val landMark: String,
    val city: String,
    val state: String,
    val zipcode: Long,
    val country: String
)