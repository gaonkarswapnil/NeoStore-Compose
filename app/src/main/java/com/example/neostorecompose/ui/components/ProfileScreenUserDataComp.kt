package com.example.neostorecompose.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreenUserDataComp(
    icon: ImageVector,
    text: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Icon",
            tint = Color.Black,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black
        )
    }
}
