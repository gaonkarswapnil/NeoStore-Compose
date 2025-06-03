package com.example.neostorecompose.domain.repository

import com.example.neostorecompose.data.dto.CartListResponse
import com.example.neostorecompose.data.dto.CartOperationResponse
import com.example.neostorecompose.domain.model.EditCartRequest
import retrofit2.Response

interface CartRepository {
    suspend fun getCartItems(accessToken: String): Response<CartListResponse>

    suspend fun editCartItems(accessToken: String, request: EditCartRequest): Response<CartOperationResponse>
}