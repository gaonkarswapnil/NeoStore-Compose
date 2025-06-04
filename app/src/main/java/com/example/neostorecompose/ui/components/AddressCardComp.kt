package com.example.neostorecompose.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.neostorecompose.domain.model.Address


@Composable
fun AddressCardComp(address: Address, isSelected: Boolean, onSelected: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onSelected() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = isSelected,
                    onClick = onSelected,
                    modifier = Modifier.padding(end = 16.dp)
                )
                Column {
                    Text(text = address.fullAddress, style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Landmark: ${address.landMark}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "${address.city}, ${address.state} - ${address.zipcode}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = address.country, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}
