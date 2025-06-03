package com.example.neostorecompose.domain.usecase

import com.example.neostorecompose.data.dto.CartOperationResponse
import com.example.neostorecompose.domain.repository.CartRepository
import retrofit2.Response
import javax.inject.Inject

class DeleteItemUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {

    suspend operator fun invoke(accessToken: String, productId: Int): Response<CartOperationResponse> {
        return cartRepository.deleteCartItem(accessToken, productId)
    }

}