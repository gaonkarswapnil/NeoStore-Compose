package com.example.neostorecompose.domain.model.response

import com.google.gson.annotations.SerializedName

data class UserRegistrationResponse(
    @SerializedName("data") val user: User,
    val message: String,
    val status: Int,
    @SerializedName("user_msg") val userMsg: String
)