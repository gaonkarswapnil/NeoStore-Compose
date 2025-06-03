package com.example.neostorecompose.ui.components

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

@Composable
fun EditableDatePickerField(
    icon: ImageVector,
    value: String,
    onDateSelected: (String) -> Unit
) {
    val context = LocalContext.current

    val calendar = Calendar.getInstance()

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                val selectedDate = "${dayOfMonth}-${month + 1}-$year"
                onDateSelected(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

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
            .clickable { datePickerDialog.show() }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Date Icon",
            tint = Color.Black,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = if (value.isNotEmpty()) value else "Select Date",
            style = TextStyle(
                color = if (value.isNotEmpty()) Color.Black else Color.Gray,
                fontSize = 16.sp
            )
        )
    }
}
