package com.example.neostorecompose.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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




}