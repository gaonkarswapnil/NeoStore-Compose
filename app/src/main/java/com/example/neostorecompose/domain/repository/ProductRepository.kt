package com.example.neostorecompose.domain.repository

import com.example.neostorecompose.data.dto.ProductsListResponse
import retrofit2.Response

interface ProductRepository {

    suspend fun getProductList(productCategoryId: Int): Response<ProductsListResponse>

}