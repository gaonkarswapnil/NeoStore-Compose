package com.example.neostorecompose.data.dto

import com.google.gson.annotations.SerializedName

data class CartListResponse(
    val count: Int,
    @SerializedName("data") val productItem: List<ProductItemData>,
    val status: Int,
    val total: Int
)