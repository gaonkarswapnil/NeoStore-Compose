package com.example.neostorecompose.data.remote

import com.example.neostorecompose.data.dto.CartListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface CartApiService {

    @GET("api/cart")
    suspend fun getCartItems(
        @Header("access_token") accessToken: String
    ): Response<CartListResponse>

}