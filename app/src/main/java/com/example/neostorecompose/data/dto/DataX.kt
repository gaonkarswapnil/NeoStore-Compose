package com.example.neostorecompose.data.dto

data class DataX(
    val cost: Double,
    val address: String,
    val id: Int,
    val order_details: List<OneOrderDetail>
)