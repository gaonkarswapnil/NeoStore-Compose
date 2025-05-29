package com.example.neostorecompose.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.example.neostorecompose.ui.theme.OrangeVariant

@Composable
fun GenderRadioButton(
    gender: String,
    selectedGender: String,
    onSelected: (String) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            selected = gender == selectedGender,
            onClick = { onSelected(gender) },
            colors = RadioButtonDefaults.colors(selectedColor = OrangeVariant)
        )
        Text(text = gender)
    }
}