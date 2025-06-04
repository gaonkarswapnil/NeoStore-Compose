package com.example.neostorecompose.data.remote

import com.example.neostorecompose.data.dto.OrderResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface OrderApiService {

    @FormUrlEncoded
    @POST("api/order")
    suspend fun orderProduct(
        @Header("access_token") accessToken: String,
        @Field("address") address: String,
    ) : Response<OrderResponse>

}