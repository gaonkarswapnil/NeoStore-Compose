package com.example.neostorecompose.domain.repository

import com.example.neostorecompose.data.dto.CartListResponse
import retrofit2.Response

interface CartRepository {
    suspend fun getCartItems(accessToken: String): Response<CartListResponse>
}