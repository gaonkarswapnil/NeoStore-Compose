package com.example.neostorecompose.data.dto

import com.google.gson.annotations.SerializedName

data class ProductItem(
    val cost: Int,
    val created: String,
    val description: String,
    val id: Int,
    val modified: String,
    val name: String,
    val producer: String,
    @SerializedName("product_category_id") val productCategoryId: Int,
    @SerializedName("product_images") val productImages: String,
    val rating: Int,
    val viewCount: Int
)