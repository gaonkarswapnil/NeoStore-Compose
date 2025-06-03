package com.example.neostorecompose.data.dto

data class UpdateProfileRequest(
    val first_name: String,
    val last_name: String,
    val email: String,
    val dob: String,
    val profile_pic: String,
    val phone_no: String
)