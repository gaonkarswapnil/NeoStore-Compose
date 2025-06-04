package com.example.neostorecompose.data.remote

import com.example.neostorecompose.data.dto.FetchOrderDetailsResponse
import com.example.neostorecompose.data.dto.GetAllOrderResponse
import com.example.neostorecompose.data.dto.OrderResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface OrderApiService {

    @FormUrlEncoded
    @POST("api/order")
    suspend fun orderProduct(
        @Header("access_token") accessToken: String,
        @Field("address") address: String,
    ) : Response<OrderResponse>


    @GET("api/orderList")
    suspend fun getallOrders(
        @Header("access_token") accessToken: String,
    ): Response<GetAllOrderResponse>


    @GET("api/orderDetail")
    suspend fun fetchOrderDetail(
        @Header("access_token") accessToken: String,
        @Query("order_id") orderId: Int,
    ) : Response<FetchOrderDetailsResponse>


}