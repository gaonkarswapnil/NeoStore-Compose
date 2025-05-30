package com.example.neostorecompose.data.dto

data class ProductData(
    val product_categories: List<ProductCategory>,
    val total_carts: Int,
    val total_orders: Int,
    val user_data: UserData
)