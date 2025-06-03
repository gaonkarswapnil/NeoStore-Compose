package com.example.neostorecompose.domain.usecase

import com.example.neostorecompose.data.dto.CartListResponse
import com.example.neostorecompose.data.dto.CartOperationResponse
import com.example.neostorecompose.domain.model.EditCartRequest
import com.example.neostorecompose.domain.repository.CartRepository
import retrofit2.Response
import javax.inject.Inject

class EditCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {

    suspend operator fun invoke(accessToken: String, request: EditCartRequest): Response<CartOperationResponse>{
        return cartRepository.editCartItems(accessToken, request)
    }

}