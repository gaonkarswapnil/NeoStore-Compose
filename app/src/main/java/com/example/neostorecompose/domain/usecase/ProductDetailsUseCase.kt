package com.example.neostorecompose.domain.usecase

import com.example.neostorecompose.data.dto.ProductDetailsResponse
import com.example.neostorecompose.domain.repository.ProductRepository
import retrofit2.Response
import javax.inject.Inject

class ProductDetailsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(productId: Int): Response<ProductDetailsResponse> {
        return productRepository.getProductDetails(productId = productId)
    }
}
