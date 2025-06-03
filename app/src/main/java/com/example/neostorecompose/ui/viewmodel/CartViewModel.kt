package com.example.neostorecompose.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neostorecompose.data.dto.CartListResponse
import com.example.neostorecompose.data.dto.ProductsListResponse
import com.example.neostorecompose.domain.usecase.CartListUseCase
import com.example.neostorecompose.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartList: CartListUseCase
): ViewModel(){

    private val _cartListState = MutableStateFlow<UiState<CartListResponse>>(UiState.Idle)
    val productList: StateFlow<UiState<CartListResponse>> = _cartListState

    fun getProductList(accessToken: String){
        viewModelScope.launch {
            _cartListState.value = UiState.Loading
            try{
                val response = cartList(accessToken)

                if(response.isSuccessful){
                    _cartListState.value = UiState.Success(
                        response.body()!!
                    )
                }else{
                    _cartListState.value = UiState.Error("Response is Empty")
                }
            }catch (e: Exception){
                _cartListState.value = UiState.Error(
                    message = e.message!!
                )
            }
        }

    }

}