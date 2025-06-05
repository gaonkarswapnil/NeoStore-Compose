package com.example.neostorecompose.domain.usecase

import com.example.neostorecompose.domain.repository.UserRepository
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(
        accessToken:String,
        oldpassword:String,
        newpassword:String,
        confirmpassword:String
    )= userRepository.changePassword(accessToken,oldpassword,newpassword,confirmpassword)

}