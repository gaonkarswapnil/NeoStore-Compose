package com.example.neostorecompose.domain.usecase

import com.example.neostorecompose.domain.repository.UserRepository
import javax.inject.Inject

class DashboardUsecase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(accessToken:String) =userRepository.getDashboard(accessToken)
}