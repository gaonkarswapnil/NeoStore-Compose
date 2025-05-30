package com.example.neostorecompose.data.remote

import com.example.neostorecompose.data.dto.DashboardResponse
import com.example.neostorecompose.data.dto.UserLoginResponse
import com.example.neostorecompose.domain.model.response.UserRegistrationResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserApiService {

    @FormUrlEncoded
    @POST("api/users/register")
    suspend fun registerUser(
        @Field("first_name") firstName: String,
        @Field("last_name") lastName: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("confirm_password") confirmPassword: String,
        @Field("gender") gender: String,
        @Field("phone_no") phoneNumber: Number
    ): Response<UserRegistrationResponse>

    @FormUrlEncoded
    @POST("api/users/login")
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String): Response<UserLoginResponse>


    @GET("api/users/getUserData")
    suspend fun fetchUserAccountDetails(
        @Header("access_token") accessToken: String
    ) : Response<DashboardResponse>

}