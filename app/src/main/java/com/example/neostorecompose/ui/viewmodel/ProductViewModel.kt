package com.example.neostorecompose.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neostorecompose.data.dto.ProductDetailsResponse
import com.example.neostorecompose.data.dto.ProductsListResponse
import com.example.neostorecompose.domain.usecase.ProductDetailsUseCase
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
    private val product: ProductListUseCase,
    private val productDetails: ProductDetailsUseCase
): ViewModel() {

    private val _productListState = MutableStateFlow<UiState<ProductsListResponse>>(UiState.Idle)
    val productList: StateFlow<UiState<ProductsListResponse>> = _productListState

    private val _productsDetailsData = MutableStateFlow<UiState<ProductDetailsResponse>>(UiState.Idle)
    val productDetailsData: StateFlow<UiState<ProductDetailsResponse>> = _productsDetailsData

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

    fun getProductsDetailsData(prouctId: Int){
        viewModelScope.launch {
            _productsDetailsData.value = UiState.Loading
            try {
                val response = productDetails(prouctId)
                if (response.isSuccessful){
                    _productsDetailsData.value = UiState.Success(
                        response.body()!!
                    )
                }else{
                    _productsDetailsData.value = UiState.Error("Response is Empty")
                }
            }catch (e: Exception){
                _productsDetailsData.value = UiState.Error(message = e.message!!)
            }
        }
    }

}