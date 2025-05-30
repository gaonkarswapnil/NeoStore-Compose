package com.example.neostorecompose.domain.repository

import com.example.neostorecompose.data.dto.DashboardResponse
import com.example.neostorecompose.data.dto.UserLoginResponse
import com.example.neostorecompose.domain.model.UserLoginRequest
import com.example.neostorecompose.domain.model.request.UserRegistrationRequest
import com.example.neostorecompose.domain.model.response.UserRegistrationResponse
import retrofit2.Response

interface UserRepository {
    suspend fun register(register: UserRegistrationRequest): Response<UserRegistrationResponse>
    suspend fun login(login: UserLoginRequest): Response<UserLoginResponse>

    suspend fun getDashboard(accessToken:String):Response<DashboardResponse>

}