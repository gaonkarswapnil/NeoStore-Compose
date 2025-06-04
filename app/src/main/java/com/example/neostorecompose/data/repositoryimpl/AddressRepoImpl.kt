package com.example.neostorecompose.data.repositoryimpl

import com.example.neostorecompose.data.MapperFunctions.toDomainModel
import com.example.neostorecompose.data.MapperFunctions.toEntity
import com.example.neostorecompose.data.local.dao.AddressDao
import com.example.neostorecompose.domain.model.Address
import com.example.neostorecompose.domain.repository.AddressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AddressRepoImpl @Inject constructor(
    private val addressDao: AddressDao
): AddressRepository {

    override fun getAddresses(): Flow<List<Address>> {
        return addressDao.getAllAddresses().map { list -> list.map { it.toDomainModel() } }
    }

    override suspend fun addAddress(address: Address) {
        addressDao.insertAddress(address.toEntity())
    }

    override suspend fun removeAddress(address: Address) {
        addressDao.deleteAddress(address.toEntity())
    }
}