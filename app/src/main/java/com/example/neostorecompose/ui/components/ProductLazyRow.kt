package com.example.neostorecompose.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.neostorecompose.data.dto.ProductImage

@Composable
fun ProductLazyRow(products: List<ProductImage>, onImageClick: (String) -> Unit){
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ){
        items(products) { product ->
            ProductCard(product){
                onImageClick(product.image)
            }
        }
    }
}