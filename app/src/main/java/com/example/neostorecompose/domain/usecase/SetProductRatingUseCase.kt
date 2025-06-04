package com.example.neostorecompose.domain.usecase

import com.example.neostorecompose.data.dto.SetProductRating
import com.example.neostorecompose.domain.repository.ProductRepository
import retrofit2.Response
import javax.inject.Inject

class SetProductRatingUseCase @Inject constructor(private val repository: ProductRepository) {
    suspend operator fun invoke(productId: Int, rating: Int): Response<SetProductRating>{
        return repository.setProductRating(productId = productId, rating = rating)
    }
}