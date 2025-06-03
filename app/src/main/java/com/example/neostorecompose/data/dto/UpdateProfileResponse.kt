package com.example.neostorecompose.data.dto

import com.example.neostorecompose.domain.model.response.User

data class UpdateProfileResponse(
    val `data`: User,
    val message: String,
    val status: Int,
    val user_msg: String
)