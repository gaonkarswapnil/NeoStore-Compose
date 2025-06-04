package com.example.neostorecompose.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(16.dp),
        colors = colors
    ) {
        Text(text = text)
    }
}