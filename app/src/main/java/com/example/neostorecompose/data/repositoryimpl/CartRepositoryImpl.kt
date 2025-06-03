package com.example.neostorecompose.data.repositoryimpl

import com.example.neostorecompose.data.dto.CartListResponse
import com.example.neostorecompose.data.dto.CartOperationResponse
import com.example.neostorecompose.data.remote.CartApiService
import com.example.neostorecompose.domain.model.EditCartRequest
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

    override suspend fun editCartItems(
        accessToken: String,
        request: EditCartRequest,
    ): Response<CartOperationResponse> {
        val response = cartService.editCartItems(
            accessToken = accessToken,
            productId = request.productId,
            quantity = request.quantity
        )

        return if(response.isSuccessful && response.body()!=null){
            Response.success(response.body())
        }else{
            Response.error(response.code(), response.errorBody()!!)
        }
    }


    override suspend fun deleteCartItem(
        accessToken: String,
        productId: Int,
    ): Response<CartOperationResponse> {
        val response = cartService.deleteCartItem(
            accessToken = accessToken,
            productId = productId
        )

        return if(response.isSuccessful && response.body()!=null){
            Response.success(response.body())
        }else{
            Response.error(response.code(), response.errorBody()!!)
        }
    }

}