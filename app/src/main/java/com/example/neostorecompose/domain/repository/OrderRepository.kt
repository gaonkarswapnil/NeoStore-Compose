package com.example.neostorecompose.domain.repository

import com.example.neostorecompose.data.dto.FetchOrderDetailsResponse
import com.example.neostorecompose.data.dto.GetAllOrderResponse
import com.example.neostorecompose.data.dto.OrderResponse
import com.example.neostorecompose.domain.model.Address
import retrofit2.Response

interface OrderRepository {
    suspend fun orderProduct(accessToken:String,address: String):Response<OrderResponse>
    suspend fun getAllOrder(accessToken:String):Response<GetAllOrderResponse>
    suspend fun fetchOrderDetailById(accessToken: String, orderId:Int):Response<FetchOrderDetailsResponse>
}