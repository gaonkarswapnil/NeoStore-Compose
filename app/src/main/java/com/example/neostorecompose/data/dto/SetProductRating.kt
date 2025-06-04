package com.example.neostorecompose.data.dto

import com.google.gson.annotations.SerializedName

data class SetProductRating(
    @SerializedName("data") val data: Data,
    val message: String,
    val status: Int,
    val user_msg: String
)