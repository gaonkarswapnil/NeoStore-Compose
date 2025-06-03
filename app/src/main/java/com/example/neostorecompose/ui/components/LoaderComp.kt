package com.example.neostorecompose.ui.components

import androidx.compose.foundation.Image
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.ImageLoader
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.neostorecompose.R

@Composable
fun LoaderComp() {
    val context = LocalContext.current

    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (android.os.Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(context)
            .data("android.resource://${context.packageName}/${R.raw.customloader}")
            .crossfade(true)
            .build(),
        imageLoader = imageLoader
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)), // This is the overlay background
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter,
            contentDescription = "Loading",
            modifier = Modifier
                .size(150.dp) // Increased size
        )
    }
}

//
//@Composable
//fun LoaderComp() {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.Black.copy(alpha = 0.5f)),
//        contentAlignment = Alignment.Center
//    ) {
//        CircularProgressIndicator(color = Color.White)
//    }
//}
