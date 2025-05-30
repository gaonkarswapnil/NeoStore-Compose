package com.example.neostorecompose.data.repositoryimpl

import com.example.neostorecompose.data.dto.ProductsListResponse
import com.example.neostorecompose.data.remote.ProductApiService
import com.example.neostorecompose.domain.repository.ProductRepository
import retrofit2.Response
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val service: ProductApiService
): ProductRepository {

    override suspend fun getProductList(productCategoryId: Int): Response<ProductsListResponse> {
        val res = service.getProductList(
            productCategoryId = productCategoryId
        )

        return if(res.isSuccessful && res.body()!=null){
            Response.success(res.body())
        }else{
            Response.error(res.code(), res.errorBody()!!)
        }
    }

}