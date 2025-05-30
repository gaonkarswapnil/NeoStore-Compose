package com.example.neostorecompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.neostorecompose.ui.theme.OrangePrimary


fun Modifier.BackgroundForScreens() : Modifier{
    return this.fillMaxSize()
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    OrangePrimary,
                    Color.White
                ),
                startY = 0f,
                endY = 600f
            )
        )
}