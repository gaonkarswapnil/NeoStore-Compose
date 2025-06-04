package com.example.neostorecompose.domain.repository

import com.example.neostorecompose.domain.model.Address
import kotlinx.coroutines.flow.Flow

interface AddressRepository {

    fun getAddresses(): Flow<List<Address>>
    suspend fun addAddress(address: Address)
    suspend fun removeAddress(address: Address)


}