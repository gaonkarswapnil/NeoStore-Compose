package com.example.neostorecompose.data.dto

import com.google.gson.annotations.SerializedName

data class ProductsListResponse(
    @SerializedName("data") val productItem: List<ProductItem>,
    val status: Int
)