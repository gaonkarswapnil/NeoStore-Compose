package com.example.neostorecompose.data.dto

import com.google.gson.annotations.SerializedName

data class ProductDetailsResponse(
    @SerializedName("data") val productDetailsData: Data,
    val status: Int
)