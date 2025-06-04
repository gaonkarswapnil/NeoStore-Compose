package com.example.neostorecompose.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun RatingDialog(
    title: String,
    imageUrl: String,
    rating: Int,
    onDismiss: (Int) -> Unit
) {
    var selectedRating by remember { mutableIntStateOf(rating) }

    AlertDialog(
        onDismissRequest = { onDismiss(selectedRating) },
        title = { Text(text = title) },
        text = {
            Column {
                Image(
                    painter = rememberAsyncImagePainter(imageUrl),
                    contentDescription = title,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )

                androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(16.dp))

                RatingBar(
                    rating = selectedRating,
                    size = 40,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(horizontal = 8.dp),
                    onRatingChanged = { newRating ->
                        selectedRating = newRating
                    }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onDismiss(selectedRating) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("RATE NOW")
            }
        }
    )
}
