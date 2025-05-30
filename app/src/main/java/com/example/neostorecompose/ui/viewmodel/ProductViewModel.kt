package com.example.neostorecompose.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neostorecompose.data.dto.ProductsListResponse
import com.example.neostorecompose.domain.usecase.ProductListUseCase
import com.example.neostorecompose.ui.navigation.Screens
import com.example.neostorecompose.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val product: ProductListUseCase
): ViewModel() {

    private val _productListState = MutableStateFlow<UiState<ProductsListResponse>>(UiState.Idle)
    val productList: StateFlow<UiState<ProductsListResponse>> = _productListState

    fun getProductList(categoryId: Int){
        viewModelScope.launch {
            _productListState.value = UiState.Loading
            try {
                val response = product(categoryId)

                if(response.isSuccessful){
                    _productListState.value = UiState.Success(
                        response.body()!!
                    )
                }else{
                    _productListState.value = UiState.Error("Response is Empty")
                }

            }catch (e: Exception){
                _productListState.value = UiState.Error(
                    message = e.message!!
                )
            }
        }
    }

}