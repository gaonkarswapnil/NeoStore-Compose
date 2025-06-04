package com.example.neostorecompose.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.neostorecompose.data.dto.OrderDetails
import com.example.neostorecompose.ui.theme.OrangePrimary


@Composable
fun OrderCard(order: OrderDetails, onClick:(Int)->Unit) {

    val orderId = remember { mutableStateOf(order.id) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                onClick(orderId.value)
            },
        shape = RoundedCornerShape(10.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Order ID
            Text(
                text = "Order Id : ${order.id}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )

            // Order Date
            Text(
                text = "Ordered Date : ${order.created}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            // Order Amount
            Text(
                text = "Amount: $${order.cost}",
                style = MaterialTheme.typography.bodyMedium,
                color = OrangePrimary
            )
        }
    }
}
