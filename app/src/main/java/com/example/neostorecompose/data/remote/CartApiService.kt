package com.example.neostorecompose.data.remote

import com.example.neostorecompose.data.dto.CartListResponse
import com.example.neostorecompose.data.dto.CartOperationResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface CartApiService {

    @GET("api/cart")
    suspend fun getCartItems(
        @Header("access_token") accessToken: String
    ): Response<CartListResponse>

    @FormUrlEncoded
    @POST("api/editCart")
    suspend fun editCartItems(
        @Header("access_token") accessToken: String,
        @Field("product_id") productId: Int,
        @Field("quantity") quantity: Int
    ): Response<CartOperationResponse>

    @FormUrlEncoded
    @POST("api/deleteCart")
    suspend fun deleteCartItem(
        @Header("access_token") accessToken: String,
        @Field("product_id") productId: Int
    ): Response<CartOperationResponse>
}