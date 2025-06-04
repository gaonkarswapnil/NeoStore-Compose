package com.example.neostorecompose.ui.components

import QuantityTextField
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDialog(
    title: String,
    imageUrl: String,
    quantity: Int,
    onDismiss: (Int) -> Unit
) {
    var quantityText by remember { mutableStateOf(quantity.toString()) }

    AlertDialog(
        onDismissRequest = {
            val parsedQty = quantityText.toIntOrNull() ?: 1
            onDismiss(parsedQty)
        },
        title = { Text(text = title) },
        text = {
            Column {
                Image(
                    painter = rememberAsyncImagePainter(imageUrl),
                    contentDescription = title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )

                Box(
                    modifier = Modifier.padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Enter Qty")
                        TextField(
                            value = quantityText,
                            onValueChange = { new ->
                                // Only allow digits or empty input
                                if (new.all { it.isDigit() } || new.isEmpty()) {
                                    quantityText = new
                                }
                            }
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val parsedQty = quantityText.toIntOrNull() ?: 1
                    onDismiss(parsedQty)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add To Cart")
            }
        }
    )
}
