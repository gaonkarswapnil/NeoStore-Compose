package com.example.neostorecompose.domain.usecase

import com.example.neostorecompose.domain.model.request.UserRegistrationRequest
import com.example.neostorecompose.domain.model.response.UserRegistrationResponse
import com.example.neostorecompose.domain.repository.UserRepository
import retrofit2.Response
import javax.inject.Inject

class UserRegisterUseCase @Inject constructor(
    private val repo: UserRepository
) {

    suspend operator fun invoke(request: UserRegistrationRequest): Response<UserRegistrationResponse>{
        return repo.register(request)
    }

}