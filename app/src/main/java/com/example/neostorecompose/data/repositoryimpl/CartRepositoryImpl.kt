package com.example.neostorecompose.data.repositoryimpl

import com.example.neostorecompose.data.dto.CartListResponse
import com.example.neostorecompose.data.remote.CartApiService
import com.example.neostorecompose.domain.repository.CartRepository
import retrofit2.Response
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartService: CartApiService
): CartRepository {

    override suspend fun getCartItems(accessToken: String): Response<CartListResponse> {
        val response = cartService.getCartItems(accessToken);

        return if (response.isSuccessful && response.body()!=null){
            Response.success(response.body())
        }else{
            Response.error(response.code(), response.errorBody()!!)
        }

    }

}