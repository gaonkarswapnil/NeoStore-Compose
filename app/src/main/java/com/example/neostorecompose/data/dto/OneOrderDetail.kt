package com.example.neostorecompose.data.dto

data class OneOrderDetail(
    val id: Int,
    val order_id: Int,
    val prod_cat_name: String,
    val prod_image: String,
    val prod_name: String,
    val product_id: Int,
    val quantity: Int,
    val total: Int
)