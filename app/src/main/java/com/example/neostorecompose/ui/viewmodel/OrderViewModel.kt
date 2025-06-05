package com.example.neostorecompose.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neostorecompose.data.dto.FetchOrderDetailsResponse
import com.example.neostorecompose.data.dto.GetAllOrderResponse
import com.example.neostorecompose.data.dto.OrderResponse
import com.example.neostorecompose.domain.repository.OrderRepository
import com.example.neostorecompose.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val orderRepository: OrderRepository
) :ViewModel(){

    private val _orderRes = MutableStateFlow<UiState<OrderResponse>>(UiState.Idle)
    val orderRes :StateFlow<UiState<OrderResponse>> get() = _orderRes

    private val _getAllOrderRes = MutableStateFlow<UiState<GetAllOrderResponse>>(UiState.Idle)
    val getAllOrderRes :StateFlow<UiState<GetAllOrderResponse>> get() = _getAllOrderRes

    private val _fetchOrderRes = MutableStateFlow<UiState<FetchOrderDetailsResponse>>(UiState.Idle)
    val fetchOrderRes :StateFlow<UiState<FetchOrderDetailsResponse>> get() = _fetchOrderRes


    fun orderProduct(accessToken:String, address:String){
        viewModelScope.launch {
            _orderRes.value = UiState.Loading

            try {
                val res = orderRepository.orderProduct(accessToken, address)
                if(res.isSuccessful){
                    _orderRes.value = UiState.Success(res.body()!!)
                }else{
                    _orderRes.value= UiState.Error(res.errorBody().toString())
                }
            }catch (e:Exception){
                _orderRes.value = UiState.Error(e.message.toString())
            }
        }
    }


    fun getAllOrders(accessToken:String){
        viewModelScope.launch {
            _getAllOrderRes.value = UiState.Loading

            try {
                val res = orderRepository.getAllOrder(accessToken)
                if(res.isSuccessful){
                    _getAllOrderRes.value = UiState.Success(res.body()!!)
                }else{
                    _getAllOrderRes.value= UiState.Error(res.errorBody().toString())
                }
            }catch (e:Exception){
                _getAllOrderRes.value = UiState.Error(e.message.toString())
            }
        }
    }


    fun fetchOneOrderById(accessToken:String, orderId:Int){
        viewModelScope.launch {
            _fetchOrderRes.value = UiState.Loading
            try {
                val res = orderRepository.fetchOrderDetailById(accessToken, orderId)
                if(res.isSuccessful){
                    _fetchOrderRes.value = UiState.Success(res.body()!!)
                }else{
                    _fetchOrderRes.value= UiState.Error(res.errorBody().toString())
                }
            }catch (e:Exception){
                _fetchOrderRes.value = UiState.Error(e.message.toString())
            }
        }
    }








}