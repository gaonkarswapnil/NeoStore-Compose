package com.example.neostorecompose.data.repositoryimpl

import com.example.neostorecompose.core.toSafeResponse
import com.example.neostorecompose.data.dto.DashboardResponse
import com.example.neostorecompose.data.dto.ResetPasswordResponse
import com.example.neostorecompose.data.dto.UpdateProfileRequest
import com.example.neostorecompose.data.dto.UpdateProfileResponse
import com.example.neostorecompose.data.dto.UserLoginResponse
import com.example.neostorecompose.data.remote.UserApiService
import com.example.neostorecompose.domain.model.UserLoginRequest
import com.example.neostorecompose.domain.model.request.UserRegistrationRequest
import com.example.neostorecompose.domain.model.response.UserRegistrationResponse
import com.example.neostorecompose.domain.repository.UserRepository
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val service: UserApiService
): UserRepository{
    override suspend fun register(register: UserRegistrationRequest): Response<UserRegistrationResponse> {

         val res = service.registerUser(
            firstName = register.firstName,
            lastName = register.lastName,
            email = register.email,
            password = register.password,
            confirmPassword = register.confirmPassword,
            gender = register.gender,
            phoneNumber = register.phoneNo
        )

        return res.toSafeResponse()

    }

    override suspend fun login(login: UserLoginRequest): Response<UserLoginResponse> {
        val res = service.loginUser(
            email = login.email,
            password = login.password
        )

        return res.toSafeResponse()
    }

    override suspend fun getDashboard(accessToken: String): Response<DashboardResponse> {
        val response = service.fetchUserAccountDetails(accessToken)
        return response.toSafeResponse()
    }


    override suspend fun updateUserProfileData(
        accessToken: String,
        updateProfileRequest: UpdateProfileRequest
    ): Response<UpdateProfileResponse> {
        val response = service.updateUserProfile(
            accessToken,
            firstName = updateProfileRequest.first_name,
            lastName = updateProfileRequest.last_name,
            email = updateProfileRequest.email,
            phoneNo = updateProfileRequest.phone_no,
            dob = updateProfileRequest.dob,
            profilePic = updateProfileRequest.profile_pic,
        )
        return response.toSafeResponse()
    }

    override suspend fun changePassword(
        accessToken: String,
        old_password: String,
        password: String,
        confirm_password: String
    ): Response<ResetPasswordResponse> {
        val response = service.changePassword(accessToken,old_password, password, confirm_password)
        return response.toSafeResponse()

    }
}