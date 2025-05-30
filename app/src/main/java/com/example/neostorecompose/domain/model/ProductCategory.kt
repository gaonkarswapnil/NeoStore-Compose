package com.example.neostorecompose.domain.model

import java.io.Serializable

data class ProductCategory(
    val created: String,
    val icon_image: String,
    val id: Int,
    val modified: String,
    val name: String
) : Serializable