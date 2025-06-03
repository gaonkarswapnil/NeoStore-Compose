package com.example.neostorecompose.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun CustomText(
    text: String,
    style: TextStyle,
    color: Color = Color.Black,
    modifier: Modifier = Modifier
){
    Text(text = text, style = style, color = color)

}