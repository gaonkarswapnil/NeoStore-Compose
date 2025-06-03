package com.example.neostorecompose.data.dto

import com.google.gson.annotations.SerializedName

data class Product(
    val cost: Int,
    val id: Int,
    val name: String,
    @SerializedName("product_category") val productCategory: String,
    @SerializedName("product_images") val productImages: String,
    @SerializedName("sub_total") val subTotal: Int
)