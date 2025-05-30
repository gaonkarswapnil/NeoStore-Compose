package com.example.neostorecompose.domain.usecase

import com.example.neostorecompose.data.dto.UserLoginResponse
import com.example.neostorecompose.domain.model.UserLoginRequest
import com.example.neostorecompose.domain.repository.UserRepository
import retrofit2.Response
import javax.inject.Inject

class UserLoginUseCase @Inject constructor(
    private val repository: UserRepository
){
    suspend operator fun invoke(request: UserLoginRequest): Response<UserLoginResponse>{
        return repository.login(request)
    }
}