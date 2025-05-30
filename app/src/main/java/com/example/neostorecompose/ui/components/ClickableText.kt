package com.example.neostorecompose.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import com.example.neostorecompose.ui.theme.OrangePrimary

@Composable
fun ClickText(onClick :() -> Unit){
    Text(
        text = "SignUp",
        color = OrangePrimary,
        style = MaterialTheme.typography.bodyMedium,
        textDecoration = TextDecoration.Underline,
        modifier = Modifier.clickable {
            onClick()
        }
    )
}