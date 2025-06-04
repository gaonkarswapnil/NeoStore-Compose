package com.example.neostorecompose.domain.repository

import com.example.neostorecompose.data.dto.ProductDetailsResponse
import com.example.neostorecompose.data.dto.ProductsListResponse
import com.example.neostorecompose.data.dto.SetProductRating
import retrofit2.Response

interface ProductRepository {

    suspend fun getProductList(productCategoryId: Int): Response<ProductsListResponse>

    suspend fun getProductDetails(productId: Int): Response<ProductDetailsResponse>

    suspend fun setProductRating(productId: Int, rating: Int): Response<SetProductRating>

}