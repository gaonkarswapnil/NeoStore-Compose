package com.example.neostorecompose.data.dto

import com.google.gson.annotations.SerializedName

data class ProductItemData(
    val id: Int,
    val product: Product,
    @SerializedName("product_id") val productId: Int,
    val quantity: Int
)