package com.example.neostorecompose.domain.model

import java.io.Serializable

data class Address(
    val addressId: Int = 0,
    val fullAddress: String,
    val landMark: String,
    val city: String,
    val state: String,
    val zipcode: Long,
    val country: String
) : Serializable