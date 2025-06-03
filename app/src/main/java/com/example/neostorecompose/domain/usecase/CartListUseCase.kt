package com.example.neostorecompose.domain.usecase

import com.example.neostorecompose.data.dto.CartListResponse
import com.example.neostorecompose.domain.repository.CartRepository
import retrofit2.Response
import javax.inject.Inject

class CartListUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {

    suspend operator fun invoke(accessToken: String): Response<CartListResponse>{
        return cartRepository.getCartItems(accessToken)
    }

}