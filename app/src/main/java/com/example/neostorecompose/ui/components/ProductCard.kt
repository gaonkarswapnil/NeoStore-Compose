package com.example.neostorecompose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.neostorecompose.data.dto.ProductImage

@Composable
fun ProductCard(data: ProductImage, onClick: () -> Unit){
    Image(
        painter = rememberAsyncImagePainter(data.image),
        contentDescription = data.created,
        contentScale = ContentScale.Crop,
        modifier = Modifier.width(100.dp).height(70.dp).clickable { onClick() }
    )
}