package com.example.neostorecompose.data

import com.example.neostorecompose.data.local.model.AddressDb
import com.example.neostorecompose.domain.model.Address

object MapperFunctions {

    fun AddressDb.toDomainModel(): Address {
        return Address(addressId, fullAddress, landMark, city, state, zipcode, country)
    }
    fun Address.toEntity(): AddressDb {
        return AddressDb(addressId, fullAddress, landMark, city, state, zipcode, country)
    }


}