package com.example.neostorecompose.data.dto

data class DashboardResponse(
    val `data`: ProductData,
    val message: String,
    val status: Int,
    val user_msg: String
)