package com.example.neostorecompose.data.remote

import com.example.neostorecompose.data.dto.ProductDetailsResponse
import com.example.neostorecompose.data.dto.ProductsListResponse
import com.example.neostorecompose.data.dto.SetProductRating
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProductApiService {

    @GET("api/products/getList")
    suspend fun getProductList(
        @Query("product_category_id") productCategoryId: Int
    ): Response<ProductsListResponse>

    @GET("api/products/getDetail")
    suspend fun getProductDetails(
        @Query("product_id") productId: Int
    ): Response<ProductDetailsResponse>

    @FormUrlEncoded
    @POST("api/products/setRating")
    suspend fun setProductRating(
        @Field("product_id") productId: Int,
        @Field("rating") rating: Int): Response<SetProductRating>
}