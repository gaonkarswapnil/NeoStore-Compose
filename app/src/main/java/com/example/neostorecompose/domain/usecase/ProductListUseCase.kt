package com.example.neostorecompose.domain.usecase

import com.example.neostorecompose.data.dto.ProductsListResponse
import com.example.neostorecompose.domain.repository.ProductRepository
import retrofit2.Response
import javax.inject.Inject

class ProductListUseCase @Inject constructor(
    private val productRepo: ProductRepository
) {

    suspend operator fun invoke(productCategoryId: Int): Response<ProductsListResponse> {
        return productRepo.getProductList(
            productCategoryId = productCategoryId
        )
    }

}