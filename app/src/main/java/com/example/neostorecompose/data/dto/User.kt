package com.example.neostorecompose.domain.model.response

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("country_id") val countryId: Any,
    val created: String,
    val dob: Any,
    val email: String,
    @SerializedName("first_name") val firstName: String,
    val gender: String,
    val id: Int,
    @SerializedName("is_active") val isActive: Boolean,
    @SerializedName("last_name") val lastName: String,
    val modified: String,
    @SerializedName("phone_no") val phoneNo: String,
    @SerializedName("profile_pic") val profilePic: Any,
    @SerializedName("role_id") val roleId: Int,
    val username: String
)