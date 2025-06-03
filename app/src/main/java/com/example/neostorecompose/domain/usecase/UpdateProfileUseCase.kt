package com.example.neostorecompose.domain.usecase

import com.example.neostorecompose.data.dto.UpdateProfileRequest
import com.example.neostorecompose.domain.repository.UserRepository
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(accessToken:String,updateProfileRequest: UpdateProfileRequest) =
        userRepository.updateUserProfileData(accessToken, updateProfileRequest)

}