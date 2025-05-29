package com.example.neostorecompose.domain.model.request

data class UserRegistrationRequest(
    val confirmPassword: String,
    val email: String,
    val firstName: String,
    val gender: String,
    val lastName: String,
    val password: String,
    val phoneNo: Long
)