package com.example.neostorecompose.data.repositoryimpl

import com.example.neostorecompose.data.dto.FetchOrderDetailsResponse
import com.example.neostorecompose.data.dto.GetAllOrderResponse
import com.example.neostorecompose.data.dto.OrderResponse
import com.example.neostorecompose.data.remote.OrderApiService
import com.example.neostorecompose.domain.repository.OrderRepository
import retrofit2.Response
import javax.inject.Inject

class OrderRepoImpl @Inject constructor(
    private val apiService: OrderApiService
):OrderRepository {

    override suspend fun orderProduct(accessToken:String, address: String): Response<OrderResponse> {
        val response = apiService.orderProduct(accessToken, address)
        return if(response.isSuccessful && response.body()!=null){
            Response.success(response.body())
        }else{
            Response.error(response.code(), response.errorBody()!!)
        }
    }


    override suspend fun getAllOrder(accessToken: String): Response<GetAllOrderResponse> {
        val response = apiService.getallOrders(accessToken)
        return if(response.isSuccessful && response.body()!=null){
            Response.success(response.body())
        }else{
            Response.error(response.code(), response.errorBody()!!)
        }
    }

    override suspend fun fetchOrderDetailById(
        accessToken: String,
        orderId: Int
    ): Response<FetchOrderDetailsResponse> {
        val response = apiService.fetchOrderDetail(accessToken, orderId)
        return if(response.isSuccessful && response.body()!=null){
            Response.success(response.body())
        }else{
            Response.error(response.code(), response.errorBody()!!)
        }
    }
}