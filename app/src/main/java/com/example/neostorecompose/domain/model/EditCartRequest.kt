package com.example.neostorecompose.domain.model

data class EditCartRequest(
    val productId: Int,
    val quantity: Int
)