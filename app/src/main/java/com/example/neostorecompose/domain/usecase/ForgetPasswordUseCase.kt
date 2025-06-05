package com.example.neostorecompose.domain.usecase

import com.example.neostorecompose.data.dto.ForgetPasswordResponse
import com.example.neostorecompose.domain.repository.UserRepository
import retrofit2.Response
import javax.inject.Inject

class ForgetPasswordUseCase @Inject constructor(
    private val repository: UserRepository
) {

    suspend operator fun invoke(email: String): Response<ForgetPasswordResponse> {
        return repository.forgetPassword(email)
    }

}