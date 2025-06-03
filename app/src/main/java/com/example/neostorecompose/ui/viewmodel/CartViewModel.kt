package com.example.neostorecompose.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neostorecompose.data.dto.CartListResponse
import com.example.neostorecompose.data.dto.CartOperationResponse
import com.example.neostorecompose.domain.model.CartRequest
import com.example.neostorecompose.domain.usecase.AddToCartUseCase
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
    private val delete: DeleteItemUseCase,
    private val addProduct: AddToCartUseCase
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

    fun editCartItems(accessToken: String, request: CartRequest){
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



    private val _addToCartState = MutableStateFlow<UiState<CartOperationResponse>>(UiState.Idle)
    fun addToCart(accessToken: String, request: CartRequest){
        viewModelScope.launch {
            _addToCartState.value = UiState.Loading

            try {
                val response = addProduct(accessToken, request)

                if (response.isSuccessful){
                    _addToCartState.value = UiState.Success(
                        response.body()!!
                    )
                }else{
                    _addToCartState.value = UiState.Error("Response is Empty")
                }
            }catch (e: Exception){
                _addToCartState.value = UiState.Error(
                    message = e.message!!
                )
            }
        }
    }
}