package com.example.neostorecompose.data.dto

import com.google.gson.annotations.SerializedName

data class ForgetPasswordResponse(
    val message: String,
    val status: Int,
    @SerializedName("user_msg") val userMsg: String
)