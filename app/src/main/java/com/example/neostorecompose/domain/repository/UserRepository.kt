package com.example.neostorecompose.domain.repository

import com.example.neostorecompose.domain.model.request.UserRegistrationRequest
import com.example.neostorecompose.domain.model.response.UserRegistrationResponse
import retrofit2.Response

interface UserRepository {
    suspend fun register(register: UserRegistrationRequest): Response<UserRegistrationResponse>
}