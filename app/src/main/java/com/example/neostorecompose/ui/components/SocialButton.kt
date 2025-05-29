package com.example.neostorecompose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun SocialButton(icons: Painter, label: String) {
    IconButton(
        onClick = {
            // TODO: Social login action
        },
        modifier = Modifier
            .height(40.dp),
    ) {
        Image(
            painter = icons,
            contentDescription = label
        )
    }
}