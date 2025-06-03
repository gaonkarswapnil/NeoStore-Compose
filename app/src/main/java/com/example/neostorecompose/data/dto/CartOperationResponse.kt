package com.example.neostorecompose.data.dto

import com.google.gson.annotations.SerializedName

data class CartOperationResponse(
    @SerializedName("data") val `data`: Boolean,
    val message: String,
    val status: Int,
    @SerializedName("total_carts") val totalCarts: Int,
    @SerializedName("user_msg") val userMsg: String
)