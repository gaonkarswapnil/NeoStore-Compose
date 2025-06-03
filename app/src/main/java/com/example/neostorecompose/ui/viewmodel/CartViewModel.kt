package com.example.neostorecompose.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neostorecompose.data.dto.CartListResponse
import com.example.neostorecompose.data.dto.CartOperationResponse
import com.example.neostorecompose.data.dto.ProductsListResponse
import com.example.neostorecompose.domain.model.EditCartRequest
import com.example.neostorecompose.domain.usecase.CartListUseCase
import com.example.neostorecompose.domain.usecase.DeleteItemUseCase
import com.example.neostorecompose.domain.usecase.EditCartUseCase
import com.example.neostorecompose.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartList: CartListUseCase,
    private val editCart: EditCartUseCase,
    private val delete: DeleteItemUseCase
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


    private val _editCartState = MutableStateFlow<UiState<CartOperationResponse>>(UiState.Idle)
    val editCartState: StateFlow<UiState<CartOperationResponse>> = _editCartState

    fun editCartItems(accessToken: String, request: EditCartRequest){
        viewModelScope.launch {
            _editCartState.value = UiState.Loading

            try {
                val response = editCart(accessToken, request)

                if (response.isSuccessful){
                    _editCartState.value = UiState.Success(
                        response.body()!!
                    )
                }else{
                    _editCartState.value = UiState.Error("Response is Empty")
                }
            }catch (e: Exception){
                _editCartState.value = UiState.Error(
                    message = e.message!!
                )
            }
        }
    }



    private val _deleteItemState = MutableStateFlow<UiState<CartOperationResponse>>(UiState.Idle)

    fun deleteCartItem(accessToken: String, productId: Int){
        viewModelScope.launch {
            _deleteItemState.value = UiState.Loading

            try {
                val response = delete(accessToken, productId)
                getProductList(accessToken)
                if (response.isSuccessful){
                    _deleteItemState.value = UiState.Success(
                        response.body()!!
                    )
                }else{
                    _deleteItemState.value = UiState.Error("Response ie Empty")
                }

            }catch (e: Exception){
                _deleteItemState.value = UiState.Error(
                    message = e.message!!
                )
            }
        }
    }

}