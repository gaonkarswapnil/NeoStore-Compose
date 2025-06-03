package com.example.neostorecompose.domain.usecase

import com.example.neostorecompose.data.dto.CartOperationResponse
import com.example.neostorecompose.domain.model.CartRequest
import com.example.neostorecompose.domain.repository.CartRepository
import retrofit2.Response
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
){
    suspend operator fun invoke(accessToken: String, request: CartRequest): Response<CartOperationResponse>{
        return cartRepository.addToCart(accessToken, request)
    }
}