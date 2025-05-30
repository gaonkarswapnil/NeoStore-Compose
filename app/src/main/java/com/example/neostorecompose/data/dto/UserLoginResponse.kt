package com.example.neostorecompose.data.dto

import com.example.neostorecompose.domain.model.response.User
import com.google.gson.annotations.SerializedName

data class UserLoginResponse(
    @SerializedName("data") val user: User,
    val message: String,
    val status: Int,
    @SerializedName("user_msg") val userMsg: String
)
