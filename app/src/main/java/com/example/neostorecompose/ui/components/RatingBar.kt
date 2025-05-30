package com.example.neostorecompose.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RatingBar(
    rating: Int,
    modifier: Modifier = Modifier,
    totalStars: Int = 5,
    onRatingChanged: ((Int) -> Unit)? = null // Nullable callback
) {
    Row(modifier = modifier) {
        for (i in 1..totalStars) {
            val isSelected = i <= rating
            Icon(
                imageVector = if (isSelected) Icons.Filled.Star else Icons.Outlined.Star,
                contentDescription = "$i Star",
                tint = if (isSelected) Color(0xFFFFC107) else Color.Gray,
                modifier = Modifier
                    .size(24.dp)
                    .then(
                        if (onRatingChanged != null) {
                            Modifier.clickable { onRatingChanged(i) }
                        } else Modifier
                    )
            )
        }
    }
}
